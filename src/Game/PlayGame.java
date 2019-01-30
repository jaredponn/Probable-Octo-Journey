package Game;

import Resources.GameResources;
import TileMap.Map;
import java.io.FileNotFoundException;

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


	public void runGameLoop() throws FileNotFoundException
	{

		while (true) {
			super.setInitialTime();

			// SYSTEMS Go here
			Map map = new Map(3);
			map.addMapConfig(GameResources.mapConfig);
			map.addTileSet(GameResources.tileSet);
			map.addMapLayer(GameResources.mapLayer0);
			map.printMapLayer(0);
			map.printRenderLayer(0, this.renderer);

			// this.render();

			super.setFinalTime();
			Timer.dynamicSleepToFrameRate(64, super.getDeltaTime());
		}
	}


	protected void processInputs()
	{
	}

	protected void render()
	{
		// RENDERING HAPPENS HERE
		this.renderer.render();
	}
}
