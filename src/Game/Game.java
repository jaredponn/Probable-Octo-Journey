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

public class Game
{

	// window / graphics
	private GameWindow gwindow;
	private InputPoller inputpol;
	private GameCanvas gcanvas;
	private Renderer renderer;

	// is running
	boolean isRunning;

	public Game()
	{
		this.gwindow = new GameWindow("Something just like this");
		this.inputpol = new InputPoller();
		this.gcanvas = new GameCanvas(800, 800, inputpol);

		this.gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);

		this.renderer = new Renderer(gcanvas);
		this.renderer.setClearColor(Color.black);
		this.isRunning = true;
	}

	public void runGameLoop()
	{
		while (isRunning) {
		}
	}

	public void disposeWindow()
	{
		this.gwindow.disposeWindow();
	}
}
