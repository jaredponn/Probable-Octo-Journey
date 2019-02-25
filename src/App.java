import poj.GameWindow.*;
import poj.Render.*;
import Game.PlayGame;

import java.awt.*;

public class App
{

	// window / graphics
	private GameWindow gwindow;
	private InputPoller inputPoller;
	private GameCanvas gcanvas;
	private Renderer renderer;


	// is running
	boolean isRunning;

	private int width = 1024;
	private int height = 768;

	public App()
	{
		this.gwindow = new GameWindow("Something just like this");
		this.inputPoller = new InputPoller();

		// makes it full screen
		/*
		GraphicsDevice gd =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		this.gcanvas = new GameCanvas(gd.getDisplayMode().getWidth(),
					      gd.getDisplayMode().getHeight(),
					      inputPoller);*/

		// windowed
		this.gcanvas = new GameCanvas(width, height, inputPoller);


		this.gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);

		this.renderer = new Renderer(gcanvas);

		this.renderer.setClearColor(Color.black);
		// this.renderer.setClearColor(Color.darkGray);

		this.isRunning = true;
	}

	public void runAppLoop()
	{

		PlayGame playGame = new PlayGame();

		playGame.setWindowWidth(width);
		playGame.setWindowHeight(height);
		playGame.loadRenderer(this.renderer);

		// changes the renderer to use the sorted buffer
		renderer.setRenderBuffer(
			new MinYFirstSortedRenderObjectBuffer());

		playGame.loadInputPoller(this.inputPoller);
		playGame.registerComponents();
		playGame.registerEntitySets();
		playGame.spawnWorld();

		while (isRunning) {

			playGame.runGameLoop();
		}

		playGame.clearWorld();
	}

	public void disposeWindow()
	{
		this.gwindow.disposeWindow();
	}
}
