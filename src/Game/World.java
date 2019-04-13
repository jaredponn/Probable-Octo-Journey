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

	/**
	 * constructor
	 * @param width : width
	 * @param height : height
	 * @param renderer : renderer -- should be a refernce
	 * @param inputPoller : inputPoller -- should be a refernce
	 */
	public World(int width, int height, Renderer renderer,
		     InputPoller inputPoller)
	{
		setWindowWidth(width);
		setWindowHeight(height);
		loadRenderer(renderer);
		loadInputPoller(inputPoller);
		this.engineState = new EngineState();
	}

	/**
	 * loads renderer
	 * @param r : renderer
	 */
	public void loadRenderer(Renderer r)
	{
		this.renderer = r;
	}

	/**
	 * loads inputPoller
	 * @param inputPoller : inputPoller -- should be a refernce
	 */
	public void loadInputPoller(InputPoller i)
	{
		this.inputPoller = i;
	}

	/**
	 * sets window width
	 * @param w :width
	 */
	public void setWindowWidth(int w)
	{
		windowWidth = w;
	}


	/**
	 * sets window height
	 * @param h :height
	 */
	public void setWindowHeight(int h)
	{
		windowHeight = h;
	}

	/**
	 * gets window width
	 * @return width
	 */
	public int getWindowWidth()
	{
		return windowWidth;
	}

	/**
	 * gets window height
	 * @return height
	 */
	public int getWindowHeight()
	{
		return windowHeight;
	}

	/**
	 * clears time
	 */
	protected void clearTime()
	{
		this.dt = DEFAULT_DELTA_TIME;
		this.acct = 0d;
	}

	/**
	 * runs game loop
	 * heavily influenced by:
	 * https://gafferongames.com/post/fix_your_timestep/
	 */
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
			startOfFrame();
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

	/**
	 * register components -- should override
	 */
	public void registerComponents()
	{
	}
	/**
	 * register entity sets -- should override
	 */
	public void registerEntitySets()
	{
	}

	/**
	 * spawn world -- should override
	 */
	public void spawnWorld()
	{
	}
	/**
	 * clear world -- should override
	 */
	public void clearWorld()
	{
	}

	/**
	 * start of frame-- should override
	 */
	protected void startOfFrame()
	{
	}

	/**
	 * process inputs-- should override
	 */
	protected abstract void processInputs();

	/**
	 * render-- should override
	 */
	protected abstract void render();

	/**
	 * run game -- should override.
	 * use super.acct for the accumlated time, use this.dt for the time step
	 */
	protected abstract void runGame();


	/**
	 * gets engine state
	 * @return engineState
	 */
	public EngineState getEngineState()
	{
		return this.engineState;
	}


	/**
	 * quits
	 */
	public void quit()
	{
		quit = true;
	}


	/**
	 * gets the input poller
	 * @return inputPoller
	 */
	public InputPoller getInputPoller()
	{
		return this.inputPoller;
	}

	/**
	 * gets the dt
	 * @return dt
	 */
	public double getDt()
	{
		return this.dt;
	}


	/**
	 * gets the acct
	 * @return acct
	 */
	public double getAcct()
	{
		return this.acct;
	}

	/**
	 * gets last cooldown
	 * @return lasCoolDown
	 */
	public ArrayList<Double> getLastCoolDown()
	{
		return this.lastCoolDown;
	}

	/**
	 * gets renderer
	 * @return renderer
	 */
	public Renderer getRenderer()
	{
		return this.renderer;
	}
}
