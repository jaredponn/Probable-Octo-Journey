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

public abstract class World
{

	// 124 fps  --> to go from fps to ms: 1/x fps = 1 / (x/1000)
	private static double DEFAULT_DELTA_TIME = 1 / 0.124d;
	private static double MAX_ACC_TIME = 50d;

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

	public World()
	{
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

	/* convenience functions for accessing engine state
	 * -- simply just wrappers for the original function.
	 * */
	public <T extends Component> void addComponentAt(Class<?> ct, T c,
							 int i)
	{
		engineState.addComponentAt(ct, c, i);
	}
	public <T extends Component> void deleteComponentAt(Class<T> c, int i)
	{
		engineState.deleteComponentAt(c, i);
	}
	/* component getters / setters for the sparse vector*/
	public <T extends Component> T getComponentAt(Class<T> c, int i)
	{
		return (T)engineState.getComponentAt(c, i);
	}

	public <T extends Component> void setComponentAt(Class<T> c, int i,
							 T val)
	{
		engineState.setComponentAt(c, i, val);
	}
	public <T extends Component> ArrayList<T>
	getRawComponentArrayListPackedData(Class<T> c)
	{
		return engineState.getRawComponentArrayListPackedData(c);
	}
	public final <T extends Component> int
	getInitialSetIndex(Class<T> setType)
	{
		return engineState.getInitialSetIndex(setType);
	}

	// gets the next entity of a set
	final public <T extends Component> int getNextSetIndex(Class<T> setType,
							       int focus)
	{
		return engineState.getNextSetIndex(setType, focus);
	}

	//  gets the initial entity for a component
	public final <T extends Component> int
	getInitialComponentIndex(Class<T> setType)
	{
		return this.getInitialSetIndex(setType);
	}


	//  gets the next entity for a component
	final public <T extends Component> int
	getNextComponentIndex(Class<T> setType, int focus)
	{
		return this.getNextSetIndex(setType, focus);
	}

	public <T extends Component> boolean hasComponent(Class<T> c, int i)
	{
		return engineState.hasComponent(c, i);
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

		double ct = Timer.getTimeInMilliSeconds();
		double acc = 0.0d;

		while (!this.quit) {
			double nt = Timer.getTimeInMilliSeconds();
			double ft = nt - ct;
			ct = nt;
			acc += ft;

			acc = Math.min(MAX_ACC_TIME, acc);

			while (acc >= this.dt) {
				runGame();
				acc -= this.dt;
				this.acct += this.dt;
			}
			this.render();
		}
	}

	// init function
	public abstract void registerComponents();
	public abstract void registerEntitySets();

	// higher game logic functions
	public abstract void spawnWorld();
	public abstract void clearWorld();

	protected abstract void processInputs();
	protected abstract void render();

	// use super.acct for the accumlated time, use this.dt for the time step
	protected abstract void runGame();
}
