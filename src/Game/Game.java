package Game;

import poj.EngineState;
import poj.GameWindow.*;
import poj.Render.*;
import poj.Time.*;
import poj.Animation;
import Components.*;
import Systems.*;
import EntitySets.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import Resources.GameResources;

public class Game
{

	// window / graphics
	private GameWindow gwindow;
	private InputPoller inputpol;
	private GameCanvas gcanvas;
	private Renderer renderer;

	// pure game stuff
	private EngineState engineState;
	private long ti;
	private long tf;
	private long dt;

	public Game()
	{
		this.gwindow = null;
		this.inputpol = null;
		this.gcanvas = null;
		this.renderer = null;
		this.engineState = null;

		this.ti = 0;
		this.tf = 0;
		this.dt = 0;
	}

	public void initGame()
	{
		this.gwindow = new GameWindow("Something just like this");
		this.inputpol = new InputPoller();
		this.gcanvas = new GameCanvas(600, 800, inputpol);

		this.gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);

		this.renderer = new Renderer(gcanvas);
		this.renderer.setClearColor(Color.black);

		this.engineState = new EngineState();
	}

	public void processInput()
	{
	}

	public void runGameLoop()
	{

		while (true) {
			ti = Timer.getTimeInMilliSeconds();

			if (inputpol.isKeyDown(KeyEvent.VK_H)) {
				System.out.println("yo");
				break;
			}

			if (inputpol.isLeftMouseButtonDown()) {
				System.out.println("left is down");
			}
			if (inputpol.isRightMouseButtonDown()) {
				System.out.println("right is down");
			}

			System.out.println("X, y position of mouse");
			System.out.println(inputpol.getMouseX());
			System.out.println(inputpol.getMouseY());
			System.out.println("----");

			System.out.println("mouse wheel");
			System.out.println(inputpol.getMouseWheelNotches());

			// pushingdifferent render objects to the screen
			renderer.pushRenderObject(
				new RenderRect(100, 100, 100, 100, Color.BLUE));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 100, 100, Color.GREEN));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 30, 40, Color.RED));
			renderer.pushRenderObject(new StringRenderObject(
				"aaa", 300, 300, Color.GREEN));

			// simple exmaple of pushing the entire image to the
			// screen
			renderer.pushRenderObject(new ImageRenderObject(
				500, 500, GameResources.testImage));

			// pushing an image to the a portion of the image to the
			// screen
			renderer.pushRenderObject(new ImageRenderObject(
				100, 100, GameResources.testImage,
				new ImageWindow(120, 60, 60, 30)));

			// pushing an image to the screen with an animation
			renderer.pushRenderObject(new ImageRenderObject(
				300, 300, GameResources.testImage,
				GameResources.testImageAnimation
					.getImageWindow()));

			// pushing another image to the screen
			renderer.pushRenderObject(
				new ImageRenderObject(
					100, 100, GameResources.testImage,
					new ImageWindow(120, 60, 60, 30))
					.setImageShade(0.3f));

			// another exampl eof pushing something simple to the
			// screen
			renderer.pushRenderObject(new ImageRenderObject(
				0, 500, GameResources.testImage));


			renderer.render();

			this.tf = Timer.getTimeInMilliSeconds();

			this.dt = this.tf - this.ti;

			// updating the animation
			GameResources.testImageAnimation.updateAnimationWindow(
				(long)dt);

			Timer.dynamicSleepToFrameRate(
				64, Timer.convertNanoSecondsToMilliseconds(dt));
		}
	}


	public void disposeWindow()
	{
		this.gwindow.disposeWindow();
	}
}
