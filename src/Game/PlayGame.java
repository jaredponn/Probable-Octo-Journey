package Game;

import poj.Time.Timer;

public class PlayGame extends World
{

	public void registerComponents()
	{
		// remember to register compoennts
	}
	public void registerEntitySets()
	{
		// remember to register entity sets
	}

	// higher game logic functions
	public void spawnWorld()
	{

		// World is spawned here
	}
	public void clearWorld()
	{
	}


	public void runGameLoop()
	{

		while (true) {
			super.setInitialTime();

			// SYSTEMS Go here
			this.render();

			super.setFinalTime();
			Timer.dynamicSleepToFrameRate(64, super.getDeltaTime());
		}
	}

	protected void render()
	{
		// RENDERING HAPPENS HERE
	}
}
