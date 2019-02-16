package Game;

import poj.EngineState;
import poj.Time.*;
import poj.Component.*;
import poj.Render.Renderer;
import poj.GameWindow.InputPoller;
import java.util.ArrayList;

public abstract class World
{
	// pure game stuff
	protected EngineState engineState;

	// time
	protected long ti;
	protected long tf;
	protected long dt;

	// window
	protected int windowWidth;
	protected int windowHeight;

	// unpure references to objects
	protected Renderer renderer;
	protected InputPoller inputPoller;

	public World()
	{
		this.engineState = new EngineState();
		ti = 0;
		tf = 0;
		dt = 0;
	}

	public void loadRenderer(Renderer r)
	{
		this.renderer = r;
	}

	public void loadInputPoller(InputPoller i)
	{
		this.inputPoller = i;
	}

	protected void setInitialTime()
	{
		this.ti = Timer.getTimeInMilliSeconds();
	}

	protected void setFinalTime()
	{
		this.tf = Timer.getTimeInMilliSeconds();
	}

	protected void calculateDeltaTime()
	{
		dt = tf - ti;
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

	// init function
	public abstract void registerComponents();
	public abstract void registerEntitySets();

	// higher game logic functions
	public abstract void spawnWorld();
	public abstract void clearWorld();

	public abstract void runGameLoop();
	protected abstract void processInputs();
	protected abstract void render();
}
