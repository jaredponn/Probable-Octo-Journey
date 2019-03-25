package App;

/**
 * App. Application running loop
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.GameWindow.*;
import poj.Render.*;
import Game.PlayGame;
import Game.Menu;
import Resources.GameConfig;

import java.awt.*;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class App
{

	// window / graphics
	private GameWindow gwindow;
	private InputPoller inputPoller;
	private GameCanvas gcanvas;
	private Renderer renderer;


	// boolean to keep track if the game is running
	boolean isRunning;
	public static boolean runMenu = true;
	private int width;
	private int height;

	/**
	 * Default constructor that acquires the rendering resources, and input
	 * system for the map
	 *
	 * @param text  the string to display.  If the text is null,
	 *              the tool tip is turned off for this component.
	 */
	public App()
	{
		this.gwindow = new GameWindow("Probably Octo Journey");
		this.inputPoller = new InputPoller();

		/* -- this makes it full screen
		GraphicsDevice gd =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		this.gcanvas = new GameCanvas(gd.getDisplayMode().getWidth(),
					      gd.getDisplayMode().getHeight(),
					      inputPoller);*/

		this.width = 1024;
		this.height = 768;

		// windowed
		this.gcanvas = new GameCanvas(width, height, inputPoller);

		this.gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);

		this.renderer = new Renderer(gcanvas);

		// default clear color
		this.renderer.setClearColor(GameConfig.APP_CLEAR_COLOR);

		// is running
		this.isRunning = true;
	}


	/**
	 * run the main app loop. Will load and intialize all the discrete game
	 * states that will be run during the duration of the application.
	 * TODO in the future add different game states like menu, and start
	 */
	public void runAppLoop() throws UnsupportedAudioFileException,
					IOException, LineUnavailableException
	{

		// playgame
		PlayGame playGame = new PlayGame();

		playGame.setWindowWidth(width);
		playGame.setWindowHeight(height);
		playGame.loadRenderer(this.renderer);

		playGame.loadInputPoller(this.inputPoller);
		playGame.registerComponents();
		playGame.registerEntitySets();
		playGame.spawnWorld();

		Menu menu = new Menu(width, height, this.renderer,
				     this.inputPoller);

		while (runMenu) {
			menu.runGame();
		}

		while (isRunning) {
			playGame.runGameLoop();
		}

		playGame.clearWorld();
	}

	/**
	 * disposes the window acquired
	 */
	public void disposeWindow()
	{
		this.gwindow.disposeWindow();
	}
}
