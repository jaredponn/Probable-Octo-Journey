package Game;

import Resources.GameResources;
import Components.*;

import poj.EngineState;
import poj.Component.Components;
import poj.Time.Timer;

import Game.Camera;

import EntitySets.*;
import TileMap.Map;
import EntityTransforms.*;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayGame extends World
{
	private Map map;
	private Camera cam;    // camera
	private Camera invCam; // inverse camera

	// TODO -- this should be moved somewhere in the map class. @Haiyang,
	// can you please look into storing this state there? And also, the map
	// tile width and heighgs are wrong
	private static final float TILE_ROTATION = ((float)Math.PI / 4.f);
	private static final float TILE_WIDTH = 1f;
	private static final float TILE_HEIGHT = 1f;

	public PlayGame()
	{
		super();

		// other resource initialization here
		this.map = new Map(3);
		this.cam = new Camera();
		this.cam.setScalingForVector2(TILE_WIDTH, -TILE_HEIGHT);
		this.cam.composeWithRotationForVector2XaxisCC(
			PlayGame.TILE_ROTATION);
		this.setCameraPosition(0f, 0f);

		this.invCam = new Camera();
		this.updateInverseCamera();
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
		// Player
		super.engineState.spawnEntitySet(new PlayerSet());

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
			this.processInputs();

			// SYSTEMS Go here
			// enemyMovements / updates / path findings
			// updatePositionFromVelocity() .....
			// updateCameraPosition() .....

			// updating the animation windows
			for (HasAnimation a :
			     super.engineState
				     .getRawComponentArrayListPackedData(
					     HasAnimation.class)) {
				EntitySetTransforms.updateHasAnimationComponent(
					a, this.dt);
			}

			// updating the cropping the render componets' sprite
			// sheets
			for (int i = super.engineState.getInitialComponentIndex(
				     HasAnimation.class);
			     Components.isValidEntity(i);
			     i = super.engineState.getNextComponentIndex(
				     HasAnimation.class, i)) {
				EntitySetTransforms
					.updateRenderComponentWindowFromHasAnimation(
						super.engineState
							.getComponentAt(
								Render.class,
								i),
						super.engineState
							.getComponentAt(
								HasAnimation
									.class,
								i));
			}

			// updating the positions to screen coordinates
			for (int i = super.engineState.getInitialComponentIndex(
				     WorldAttributes.class);
			     Components.isValidEntity(i);
			     i = super.engineState.getNextComponentIndex(
				     WorldAttributes.class, i)) {

				EntitySetTransforms
					.updateRenderScreenCoordinatesFromWorldCoordinates(
						super.engineState
							.getComponentAt(
								WorldAttributes
									.class,
								i),
						super.engineState
							.getComponentAt(
								Render.class,
								i),
						this.cam);
			}

			this.render();
			this.updateInverseCamera();

			super.setFinalTime();
			super.calculateDeltaTime();

			Timer.dynamicSleepToFrameRate(124, super.dt);
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
				super.engineState
					.getComponentAt(WorldAttributes.class,
							i)
					.add(0, 10);
			}

			if (super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
				System.out.println("d key is down");
				super.engineState
					.getComponentAt(WorldAttributes.class,
							i)
					.add(10, 0);
			}
			if (super.inputPoller.isKeyDown(KeyEvent.VK_S)) {
				System.out.println("s key is down");
				super.engineState
					.getComponentAt(WorldAttributes.class,
							i)
					.add(0, -10);
			}
			if (super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
				System.out.println("a key is down");
				super.engineState
					.getComponentAt(WorldAttributes.class,
							i)
					.add(-10, 0);
			}
		}
	}

	protected void render()
	{
		map.renderTileMap(super.renderer);

		for (Render r : super.engineState.getComponents()
					.getRawComponentArrayListPackedData(
						Render.class)) {
			EntitySetTransforms.pushRenderComponentToRenderer(
				r, super.renderer);
		}

		super.renderer.render();
	}


	private void updateInverseCamera()
	{
		if (this.cam.isInvertible()) {
			this.invCam =
				new Camera((this.cam.unsafePureInverse()));
		}
	}

	private void setCameraPosition(float x, float y)
	{
		cam.setTranslationForVector2(x, y);
	}
}
