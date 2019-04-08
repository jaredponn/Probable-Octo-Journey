package Game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Components.PCollisionBody;
import Components.Render;
import EntitySets.MenuButton;
import Resources.GameResources;

import poj.Collisions.GJK;
import poj.Collisions.Polygon;
import poj.GameWindow.InputPoller;
import poj.Render.ImageRenderObject;
import poj.Render.RenderObject;
import poj.Render.RenderRect;
import poj.Render.Renderer;
import poj.Time.Timer;
import poj.linear.Vector2f;
public class MenuNew extends World
{

	private Queue<RenderObject> titleBuffer =
		new LinkedList<RenderObject>();
	private Queue<RenderObject> buttonsBuffer =
		new LinkedList<RenderObject>();
	private Queue<RenderObject> collisioBoxBuffer =
		new LinkedList<RenderObject>();
	private Queue<PCollisionBody> buttonHitBoxBuffer =
		new LinkedList<PCollisionBody>();

	private GJK gjk = new GJK();
	private ArrayList<RenderObject> menuImageROBuffer =
		new ArrayList<RenderObject>();
	private ArrayList<PCollisionBody> buttonHitBoxROBuffer =
		new ArrayList<PCollisionBody>();

	private MenuState curMenuState = MenuState.mainMenu;

	private ArrayList<BufferedImage> menuButton =
		new ArrayList<BufferedImage>();

	private ArrayList<Render> buttonRenderLayer;

	public MenuNew(int width, int height, Renderer renderer,
		       InputPoller inputPoller)
	{
		super(width, height, renderer, inputPoller);
		registerComponents();
		registerEntitySets();
		/*
		 * Note: the order of index for the menuImageROBuffer is inside
		 * gameresources
		 */
		// if the resolution is 1920x1080
		if (this.windowWidth == 1920 && this.windowHeight == 1080) {
			// create the imageRenderObject from the bufferedImages
			for (int i = 0; i < GameResources.menuImage90.size();
			     ++i) {
				menuImageROBuffer.add(new ImageRenderObject(
					0, 0,
					GameResources.menuImage90.get(i)));
			}
			// make the menuButton pointer point to the 1920x1080
			// res vector
			menuButton = GameResources.menuButtonImage90;

		}
		// any other resolution will be using the default resolution
		// images: 1366x768 (an OK assumption since modern computers
		// have common resolution ratio of  1366x768)
		else {
			// create the imageRenderObject from the bufferedImages
			for (int i = 0; i < GameResources.menuImage38.size();
			     ++i) {
				menuImageROBuffer.add(new ImageRenderObject(
					0, 0,
					GameResources.menuImage38.get(i)));
			}
			// make the menuButton pointer point to the 1920x1080
			// res vector
			menuButton = GameResources.menuButtonImage38;
		}
		// add the menu buttons
		addMenuButtons();
	}

	public void registerComponents()
	{
		super.engineState.registerComponent(PCollisionBody.class);
		super.engineState.registerComponent(Render.class);
	}

	public void registerEntitySets()
	{
		super.engineState.registerSet(MenuButton.class);
	}

	public void addMenuButtons()
	{
		// clang-format off
		//registers all of the button entity sets with its render and collision box 
		for (int i=0;i<GameResources.menuButtonValues.length;++i){
			super.engineState.spawnEntitySet(new MenuButton(this.windowWidth, this.windowHeight, GameResources.menuButtonValues[i][0], GameResources.menuButtonValues[i][1], GameResources.menuButtonValues[i][2], GameResources.menuButtonValues[i][3],this.menuButton.get(i)));
		}
		// clang-format on
		// store the render components for the buttons
		buttonRenderLayer = super.engineState.getComponents()
					    .getRawComponentArrayListPackedData(
						    Render.class);
		buttonHitBoxROBuffer =
			super.engineState.getComponents()
				.getRawComponentArrayListPackedData(
					PCollisionBody.class);
	}

	public void processInputs()
	{
		System.out.println("asdfasdf");
		// press P to play
		if (inputPoller.isKeyDown(KeyEvent.VK_P)) {
			super.quit();
		}

		// if the mouse is clicked
		if (inputPoller.isLeftMouseButtonDown()) {
			Vector2f mousePosition = inputPoller.getMousePosition();
			PCollisionBody mouseHitBox = new PCollisionBody(
				new Vector2f(0f, 0f), new Vector2f(0f, 0f),
				mousePosition);
			switch (curMenuState) {
				// if the menu is at the main menu page
			case mainMenu:
				// store te array index of which elements are
				// colliding
				int colliding = -1;
				for (int i = 0; i < 3; ++i) {
					// if this button hitbosx is colliding
					// with the mouse
					if (Systems.arePCollisionBodiesColliding(
						    gjk, mouseHitBox,
						    this.buttonHitBoxROBuffer
							    .get(i))) {
						// set the colliding int as
						// index, and then break
						colliding = i;
						break;
					}
				}

				// if a collision occurs
				if (colliding != -1) {
					// clicks the play button
					if (colliding == 0) {
						super.quit();
					}
					// clicks how to play button
					else if (colliding == 1) {
						// switch the enum state to
						// instructions
						curMenuState =
							MenuState.instructions;
					}
					// clicks the exit button
					else {
						System.exit(0);
					}
				}
				// breaks from the switch
				break;
			// if the menu is at the instructions page
			case instructions:
				// if the back button is hit
				if (Systems.arePCollisionBodiesColliding(
					    gjk, mouseHitBox,
					    this.buttonHitBoxROBuffer.get(4))) {
					// switch the enum state to main menu
					curMenuState = MenuState.mainMenu;
				}
				// break from the switch statement
				break;
			}
		}
	}

	public void render()
	{
		if (curMenuState == MenuState.mainMenu) {
			addMainMenuRenderBuffer();
		} else {
			addInstructionsRenderBuffer();
		}
		this.renderer.renderBuffers(titleBuffer, buttonsBuffer,
					    collisioBoxBuffer);
	}

	// add the buttons render objects and collision boxes for the main menu
	public void addMainMenuRenderBuffer()
	{
		titleBuffer.add(menuImageROBuffer.get(1)); // add main bg
		titleBuffer.add(menuImageROBuffer.get(0)); // add title
		titleBuffer.add(menuImageROBuffer.get(2)); // add main tombstone
		// only render the play, how to play, and exit button
		for (int i = 0; i < 3; ++i) {
			buttonsBuffer.add(
				this.buttonRenderLayer.get(i).getGraphic());
			buttonHitBoxBuffer.add(
				this.buttonHitBoxROBuffer.get(i));
			pCollisionBodyDebugRenderer(
				this.buttonHitBoxROBuffer.get(i),
				collisioBoxBuffer, Color.BLUE);
		}
	}

	// add the buttons render objects and collision boxes for the main menu
	public void addInstructionsRenderBuffer()
	{
		titleBuffer.add(menuImageROBuffer.get(3)); // add help bg
		titleBuffer.add(
			menuImageROBuffer.get(4)); // add help instructiosn

		// only render the how to play and back button
		for (int i = 3; i <= 4; ++i) {
			buttonsBuffer.add(
				this.buttonRenderLayer.get(i).getGraphic());
			buttonHitBoxBuffer.add(
				this.buttonHitBoxROBuffer.get(i));
			pCollisionBodyDebugRenderer(
				this.buttonHitBoxROBuffer.get(i),
				collisioBoxBuffer, Color.BLUE);
		}
		// show collision box for debugging
	}

	public void runGame()
	{
		Timer.sleepNMilliseconds(10);
		System.out.println("Yo");
		processInputs();
		// render();
	}

	public void pCollisionBodyDebugRenderer(final PCollisionBody pc,
						Queue<RenderObject> q, Color r)
	{
		Polygon p = pc.getPolygon();
		Vector2f[] pts = p.pts();

		for (int i = 0; i < p.getSize(); ++i) {
			q.add(new RenderRect((int)pts[i].x, (int)pts[i].y, 5, 5,
					     r));
		}
	}
}

enum MenuState {
	mainMenu,
	instructions;
}
