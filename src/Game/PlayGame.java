package Game;

import Resources.GameResources;
import TileMap.Map;
import java.io.FileNotFoundException;

import poj.Time.Timer;

public class PlayGame extends World
{
	private Map map = new Map(3);
	public void registerComponents()
	{
		// remember to register compoennts
	}
	public void registerEntitySets()
	{
		// remember to register entity sets
	}

	// higher game logic functions
	public void spawnWorld() throws FileNotFoundException

	{

		map.addMapConfig(GameResources.mapConfig);
		map.addTileSet(GameResources.tileSet);
		map.addMapLayer(GameResources.mapLayer0);
		map.addMapLayer(GameResources.mapLayer1);
		map.addMapLayer(GameResources.mapLayer2);
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
			// map.printMapLayer(0);

			this.render();

			super.setFinalTime();
			Timer.dynamicSleepToFrameRate(64, super.getDeltaTime());
		}
	}


	protected void processInputs()
	{
	}

	protected void render()
	{
		map.printRenderLayer(this.renderer);
		// map.printRenderLayer(1, this.renderer);
		// RENDERING HAPPENS HERE
		this.renderer.render();
	}
}
