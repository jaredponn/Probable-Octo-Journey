package Game;

import poj.EngineState;
import poj.Time.*;

public abstract class World
{
	// pure game stuff
	protected EngineState engineState;

	// time
	protected long ti;
	protected long tf;
	protected long dt;


	public World()
	{
		this.engineState = new EngineState();
		ti = 0;
		tf = 0;
		dt = 0;
	}

	protected void setInitialTime()
	{
		this.ti = Timer.getTimeInMilliSeconds();
	}

	protected void setFinalTime()
	{
		this.tf = Timer.getTimeInMilliSeconds();
	}

	protected long getDeltaTime()
	{
		return tf - ti;
	}

	// init function
	public abstract void registerComponents();
	public abstract void registerEntitySets();

	// higher game logic functions
	public abstract void spawnWorld();
	public abstract void clearWorld();

	public abstract void runGameLoop();
	protected abstract void render();
}
