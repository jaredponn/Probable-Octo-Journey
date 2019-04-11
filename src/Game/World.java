package Game;
/**
 * World. Abstract way to create different worlds of the game.
 * Date: February 10, 2019
 * @author Jared and the game loop code came from:
 * https://gafferongames.com/post/fix_your_timestep/
 * @version 1.0
 */
import poj.EngineState;
import poj.Time.*;
import poj.Component.*;
import poj.Render.Renderer;
import poj.GameWindow.InputPoller;
import java.util.ArrayList;
import java.util.Collections;

public abstract class World
{

	// 124 fps  --> to go from fps to ms: 1/x fps = 1 / (x/1000)
	private static double DEFAULT_DELTA_TIME = 1 / (124d / 1000d);
	// max time in ms for the frame
	private static double MAX_ACC_TIME = 33d;

	// pure game stuff
	protected EngineState engineState;

	// time
	protected double dt;   // deltatime
	protected double acct; // acculmated time

	protected boolean quit = false;

	// window
	protected int windowWidth;
	protected int windowHeight;

	// unpure references to objects
	protected Renderer renderer;
	protected InputPoller inputPoller;

	protected static ArrayList<Double> coolDownMax = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));
	protected ArrayList<Double> lastCoolDown = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));

	public World(int width, int height, Renderer renderer,
		     InputPoller inputPoller)
	{
		setWindowWidth(width);
		setWindowHeight(height);
		loadRenderer(renderer);
		loadInputPoller(inputPoller);
		this.engineState = new EngineState();
	}

	public void loadRenderer(Renderer r)
	{
		this.renderer = r;
	}

	public void loadInputPoller(InputPoller i)
	{
		this.inputPoller = i;
	}

	public void setWindowWidth(int w)
	{
		windowWidth = w;
	}

	public void setWindowHeight(int h)
	{
		windowHeight = h;
	}

	public int getWindowWidth()
	{
		return windowWidth;
	}

	public int getWindowHeight()
	{
		return windowHeight;
	}

	protected void clearTime()
	{
		this.dt = DEFAULT_DELTA_TIME;
		this.acct = 0d;
	}

	// heavily influenced by:
	// https://gafferongames.com/post/fix_your_timestep/
	public final void runGameLoop()
	{
		this.clearTime();

		double ti = Timer.getTimeInMilliSeconds();

		while (!this.quit) {
			// Timer.START_BENCH();

			double tf = Timer.getTimeInMilliSeconds();

			double dft = tf - ti;
			dft = Math.min(MAX_ACC_TIME, dft);

			this.acct += dft;
			do {
				runGame();

				dft -= this.dt;

			} while (dft >= this.dt);

			this.render();

			ti = tf;

			// Timer.END_BENCH();
			// Timer.LOG_BENCH_DELTA();
		}
	}

	// init function
	public void registerComponents()
	{
	}
	public void registerEntitySets()
	{
	}

	// higher game logic functions
	public void spawnWorld()
	{
	}
	public void clearWorld()
	{
	}

	protected abstract void processInputs();
	protected abstract void render();

	// use super.acct for the accumlated time, use this.dt for the time step
	protected abstract void runGame();


	public EngineState getEngineState()
	{
		return this.engineState;
	}

	public void quit()
	{
		quit = true;
	}

	public InputPoller getInputPoller()
	{
		return this.inputPoller;
	}

	public double getDt()
	{
		return this.dt;
	}


	public double getAcct()
	{
		return this.acct;
	}
}
