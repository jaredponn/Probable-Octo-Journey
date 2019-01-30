package Game;

import poj.EngineState;
import poj.Time.*;
import poj.Render.Renderer;
import poj.GameWindow.InputPoller;
import java.io.FileNotFoundException;

public abstract class World
{
	// pure game stuff
	protected EngineState engineState;

	// time
	protected long ti;
	protected long tf;
	protected long dt;

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

	public abstract void runGameLoop() throws FileNotFoundException;
	protected abstract void processInputs();
	protected abstract void render();
}
