package Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Components.PCollisionBody;
import Components.PhysicsPCollisionBody;
import Resources.*;
import App.*;

import poj.Collisions.GJK;
import poj.Collisions.Polygon;
import poj.GameWindow.InputPoller;
import poj.Render.ImageRenderObject;
import poj.Render.RenderObject;
import poj.Render.RenderRect;
import poj.Render.Renderer;
import poj.linear.Vector2f;

public class Menu
{
	private Renderer renderer;
	private InputPoller inputPoller;
	private Queue<RenderObject> titleBuffer =
		new LinkedList<RenderObject>();
	private Queue<RenderObject> buttonsBuffer =
		new LinkedList<RenderObject>();

	private ArrayList<PCollisionBody> buttonHitBoxes =
		new ArrayList<PCollisionBody>();
	private GJK gjk = new GJK();
	private int width, height;
	private ImageRenderObject playButton, title, howToPlayButton;
	private MenuState curMenuState = MenuState.mainMenu;

	public Menu(int width, int height, Renderer renderer,
		    InputPoller inputPoller)
	{
		this.width = width;
		this.height = height;
		this.renderer = renderer;
		this.inputPoller = inputPoller;
		gjk.clearVerticies();

		title = new ImageRenderObject(0, 0, GameResources.octoTitle);
		playButton =
			new ImageRenderObject(width / 2 - 70, height / 2 + 150,
					      GameResources.playButton);
		howToPlayButton =
			new ImageRenderObject(width / 2 - 70, height / 2 - 50,
					      GameResources.howToPlayButton);

		buttonHitBoxes.add(GameConfig.PLAY_BUTTON_COLLISION_BODY);
		buttonHitBoxes.add(
			GameConfig.HOW_TO_PLAY_BUTTON_COLLISION_BODY);
	}

	// check for button hitboxes
	// 0th = play button, 1st= howToPlay
	public void processInputs()
	{
		if (inputPoller.isLeftMouseButtonDown()) {

			Vector2f mousePosition = inputPoller.getMousePosition();
			System.out.println("x value of mouse click is : "
					   + mousePosition.x
					   + ", y value of mouse click is : "
					   + mousePosition.y);
			PCollisionBody mouseHitBox = new PCollisionBody(
				new Vector2f(0f, 0f), new Vector2f(0f, 0f),
				mousePosition);
			for (int i = 0; i < buttonHitBoxes.size(); ++i) {
				if (Systems.arePCollisionBodiesColliding(
					    gjk, mouseHitBox,
					    buttonHitBoxes.get(i))) {
					if (i == 0) {
						System.out.println(
							"play button is hit!");
						// end the loop in App
						App.runMenu = false;
					} else if (i == 1) {
						System.out.println(
							"how to play button is hit!");
					}
				}
			}
		}
	}

	public void runGame()
	{
		switch (curMenuState) {
		case mainMenu:
			setMainMenu();
			break;
		case instructions:
			break;
		}
		processInputs();
		for (PCollisionBody i : buttonHitBoxes) {
			pCollisionBodyDebugRenderer(i, buttonsBuffer,
						    Color.BLUE);
		}
		render();
	}

	// render the buttons
	public void render()
	{
		renderer.renderBuffers(titleBuffer, buttonsBuffer);
	}

	public void setMainMenu()
	{
		titleBuffer.add(title);
		buttonsBuffer.add(playButton);
		buttonsBuffer.add(howToPlayButton);
	}


	public void pCollisionBodyDebugRenderer(final PCollisionBody pc,
						Queue<RenderObject> q, Color r)
	{
		Polygon p = pc.getPolygon();
		Vector2f[] pts = p.pts();

		for (int i = 0; i < p.getSize(); ++i) {
			// final Vector2f sc = pts[i].pureMatrixMultiply(cam);
			q.add(new RenderRect((int)pts[i].x, (int)pts[i].y, 1, 1,
					     r));
		}
		// Vector2f center = pc.pureGetCenter().pureMatrixMultiply(cam);


		// q.add(new RenderRect((int)center.x, (int)center.y, 1, 1,
		// CENTER_DEBUG_COLOR));
	}
}

enum MenuState {
	mainMenu,
	playing,
	instructions;
}
