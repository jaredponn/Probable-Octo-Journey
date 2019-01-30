package Game;

import poj.EngineState;
import poj.GameWindow.*;
import poj.Render.*;
import poj.Time.*;
import Components.*;
import EntitySets.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import Resources.GameResources;

public class App
{

	// window / graphics
	private GameWindow gwindow;
	private InputPoller inputPoller;
	private GameCanvas gcanvas;
	private Renderer renderer;

	// is running
	boolean isRunning;

	public App()
	{
		this.gwindow = new GameWindow("Something just like this");
		this.inputPoller = new InputPoller();

		// makes it full screen
		GraphicsDevice gd =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		this.gcanvas = new GameCanvas(gd.getDisplayMode().getWidth(),
					      gd.getDisplayMode().getHeight(),
					      inputPoller);

		this.gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);

		this.renderer = new Renderer(gcanvas);
		this.renderer.setClearColor(Color.black);
		this.isRunning = true;
	}

	public void runAppLoop()
	{

		PlayGame playGame = new PlayGame();

		playGame.loadRenderer(this.renderer);
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
