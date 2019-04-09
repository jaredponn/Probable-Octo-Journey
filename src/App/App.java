package App;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Game.GameOver;
import Game.MenuNew;
import Game.PlayGame;
import Resources.GameConfig;
import Resources.GameResources;
/**
 * App. Application running loop
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */
import poj.GameWindow.GameCanvas;
import poj.GameWindow.GameWindow;
import poj.GameWindow.InputPoller;
import poj.Render.Renderer;

public class App
{

	// window / graphics
	private GameWindow gwindow;
	private InputPoller inputPoller;
	private GameCanvas gcanvas;
	private Renderer renderer;


	// boolean to keep track if the game is running
	boolean isRunning;
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
		this.gwindow = new GameWindow("Probable Octo Journey");
		this.inputPoller = new InputPoller();

		GraphicsDevice gd =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		this.gcanvas = new GameCanvas(gd.getDisplayMode().getWidth(),
					      gd.getDisplayMode().getHeight(),
					      inputPoller);
		this.width = gd.getDisplayMode().getWidth();
		this.height = gd.getDisplayMode().getHeight();
		System.out.println("width = " + width);
		System.out.println("height = " + height);

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


		while (isRunning) {
			MenuNew menu = new MenuNew(width, height, this.renderer,
						   this.inputPoller);
			// start playing menu music
			GameResources.menuSound.playContinuously();

			menu.runGameLoop();
			// stop playing menu music
			GameResources.menuSound.end();

			// playgame
			GameResources.gameBgSound.playContinuously();
			PlayGame playGame = new PlayGame(
				width, height, this.renderer, this.inputPoller);

			playGame.registerComponents();
			playGame.registerEntitySets();
			playGame.spawnWorld();
			// start playing game background music
			GameResources.gameBgSound.playContinuously();
			playGame.runGameLoop();

			GameResources.gameBgSound.end();

			GameOver gameOver = new GameOver(
				width, height, this.renderer, this.inputPoller,
				playGame.getKillCount());

			gameOver.runGameLoop();
		}
	}

	/**
	 * disposes the window acquired
	 */
	public void disposeWindow()
	{
		this.gwindow.disposeWindow();
	}
}
