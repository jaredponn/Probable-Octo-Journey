package Game;

import Resources.GameResources;
import Components.*;
import poj.Component.Components;
import EntitySets.*;
import TileMap.Map;
import EntityTransforms.*;

import java.awt.event.KeyEvent;

import poj.Time.Timer;

public class PlayGame extends World
{
	private Map map;

	public PlayGame()
	{
		super();

		// other resource initialization here
		this.map = new Map(3);
	}

	public void registerComponents()
	{
		// remember to register components
		super.engineState.registerComponent(CollisionBody.class);
		super.engineState.registerComponent(HasAnimation.class);
		super.engineState.registerComponent(Render.class);
		super.engineState.registerComponent(TileCord.class);
		super.engineState.registerComponent(Speed.class);
		super.engineState.registerComponent(WorldAttributes.class);
		super.engineState.registerComponent(Direction.class);
	private Map map = new Map(3);
	public void registerComponents()
	{
		// remember to register compoennts
	}
	public void registerEntitySets()
	{
		// remember to register entity sets
		super.engineState.registerSet(PlayerSet.class);
		super.engineState.registerSet(MobSet.class);
		super.engineState.registerSet(ConstructSet.class);
	}

	// higher game logic functions
	public void spawnWorld()

	{
		// World is spawned here
		this.map.addMapConfig(GameResources.mapConfig);
		this.map.addTileSet(GameResources.tileSet);
		this.map.addMapLayer(GameResources.mapLayer0);
		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer2);
	}
	public void clearWorld()
	{
	}


	public void runGameLoop()
	{

		while (true) {
			super.setInitialTime();

			// SYSTEMS Go here

			// enemyMovements / updates / path findings
			// updatePositionFromVelocity() .....
			// updateCameraPosition() .....

			for (HasAnimation a :
			     super.engineState.getComponents()
				     .getRawComponentArrayListPackedData(
					     HasAnimation.class)) {
				EntitySetTransforms.updateHasAnimationComponent(
					a, this.dt);
			}

			this.render();
			super.setFinalTime();

			Timer.dynamicSleepToFrameRate(64, super.getDeltaTime());
		}
	}


	protected void processInputs()
	{


		// player manipulation
		for (int i = super.engineState.getComponents()
				     .getInitialSetIndex(PlayerSet.class);
		     Components.isValidEntity(i);
		     i = engineState.getComponents().getNextSetIndex(
			     PlayerSet.class, i)) {

			if (super.inputPoller.isKeyDown(KeyEvent.VK_W)) {
				System.out.println("w key is down");
			}
			if (super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
				System.out.println("w key is down");
			}
			if (super.inputPoller.isKeyDown(KeyEvent.VK_S)) {
				System.out.println("w key is down");
			}
			if (super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
				System.out.println("w key is down");
			}
		}
	}

	protected void render()
	{
		map.renderTileMap(super.renderer);
		// map.printRenderLayer(1, super.renderer);

		for (Render r : super.engineState.getComponents()
					.getRawComponentArrayListPackedData(
						Render.class)) {
			EntitySetTransforms.pushRenderComponentToRenderer(
				r, super.renderer);
		}

		super.renderer.render();
	}
}
