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
import poj.Time.*;

public class Menu
{
	private Renderer renderer;
	private InputPoller inputPoller;
	private Queue<RenderObject> titleBuffer =
		new LinkedList<RenderObject>();
	private Queue<RenderObject> buttonsBuffer =
		new LinkedList<RenderObject>();

	private Queue<PCollisionBody> buttonHitBoxes =
		new LinkedList<PCollisionBody>();
	private GJK gjk = new GJK();
	private int width, height;
	private ImageRenderObject playButton, title, howToPlayButton,
		exitButton, instructionTitle, backButton;
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
			new ImageRenderObject(width / 2 - 70, height / 2 + 90,
					      GameResources.playButton);
		howToPlayButton =
			new ImageRenderObject(width / 2 - 70, height / 2 - 90,
					      GameResources.howToPlayButton);
		exitButton =
			new ImageRenderObject(width / 2 - 70, height / 2 + 290,
					      GameResources.exitButton);
		backButton =
			new ImageRenderObject(width / 2 - 70, height / 2 + 190,
					      GameResources.backButton);
		instructionTitle = new ImageRenderObject(
			0, 290, GameResources.instructionTitle);
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

			System.out.println("button hitbox sizze before = "
					   + buttonHitBoxes.size());
			/*
			int i = 0;
			while (!buttonHitBoxes.isEmpty()) {
				PCollisionBody buttonBody =
					buttonHitBoxes.poll();
				if (Systems.arePCollisionBodiesColliding(
					    gjk, mouseHitBox, buttonBody)) {
					if (i == 0) {
						System.out.println("0 is hit");
					} else if (i == 1) {
						System.out.println("1 is hit");
					} else if (i == 2) {
						System.out.println("2 is hit");
					}
				}
				i++;
			}
				*/


			int i = 0;
			while (!buttonHitBoxes.isEmpty()) {
				PCollisionBody buttonBody =
					buttonHitBoxes.poll();
				System.out.println("currr i  " + i);
				System.out.println("cur enum = "
						   + curMenuState);

				if (Systems.arePCollisionBodiesColliding(
					    gjk, buttonBody, mouseHitBox)) {
					// exit button is always
					// added if in main menu
					// 1st=playButton, 2nd=
					// howToPlay
					if (curMenuState
					    == MenuState.mainMenu) {
						System.out.println(" i = " + i);

						if (i == 0) {
							System.out.println(
								"exit button is hit!");
							System.exit(0);
						} else if (i == 1) {
							System.out.println(
								"play button is hit!");
							// end the
							// loop in
							// App
							App.runMenu = false;
							break;
						} else if (i == 2) {
							// go to
							// instructions
							System.out.println(
								"how to play button is hit!");
							setInstructionMenu();
							break;
						}
					} else if (curMenuState
						   == MenuState.instructions) {
						// 1st=back button
						if (i == 0) {
							System.out.println(
								"exit button is hit!");
							System.exit(0);
						} else if (i == 1) {
							System.out.println(
								"back button is hit!");

							setMainMenu();
							break;
						}
					}
				}
				++i;
			}
			buttonHitBoxes.clear();
		}
	}

	public void runGame()
	{
		Timer.sleepNMilliseconds(10);
		titleBuffer.add(title);
		buttonsBuffer.add(exitButton);

		switch (curMenuState) {
		case mainMenu:
			setMainMenu();
			break;
		case instructions:
			setInstructionMenu();
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
		buttonsBuffer.add(playButton);
		buttonsBuffer.add(howToPlayButton);
		curMenuState = MenuState.mainMenu;
		buttonHitBoxes.add(GameConfig.EXIT_BUTTON_COLLISION_BODY);
		buttonHitBoxes.add(GameConfig.PLAY_BUTTON_COLLISION_BODY);
		buttonHitBoxes.add(
			GameConfig.HOW_TO_PLAY_BUTTON_COLLISION_BODY);
	}
	public void setInstructionMenu()
	{
		curMenuState = MenuState.instructions;
		buttonsBuffer.add(instructionTitle);
		buttonsBuffer.add(backButton);
		buttonHitBoxes.add(GameConfig.EXIT_BUTTON_COLLISION_BODY);
		buttonHitBoxes.add(GameConfig.BACK_BUTTON_COLLISION_BODY);
	}


	public void pCollisionBodyDebugRenderer(final PCollisionBody pc,
						Queue<RenderObject> q, Color r)
	{
		Polygon p = pc.getPolygon();
		Vector2f[] pts = p.pts();

		for (int i = 0; i < p.getSize(); ++i) {
			// final Vector2f sc =
			// pts[i].pureMatrixMultiply(cam);
			q.add(new RenderRect((int)pts[i].x, (int)pts[i].y, 1, 1,
					     r));
		}
		// Vector2f center =
		// pc.pureGetCenter().pureMatrixMultiply(cam);


		// q.add(new RenderRect((int)center.x, (int)center.y, 1,
		// 1, CENTER_DEBUG_COLOR));
	}
}

enum MenuState {
	mainMenu,
	playing,
	instructions;
}
