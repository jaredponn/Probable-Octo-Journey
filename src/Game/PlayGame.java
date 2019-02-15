package Game;

import Resources.*;
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
	private static final float TILE_WIDTH = 64f;
	private static final float TILE_HEIGHT = 32f;

	public PlayGame()
	{
		super();


		// World loading
		this.map = new Map(3);
		this.map.addMapConfig(GameResources.mapConfig);
		this.map.addTileSet(GameResources.tileSet);
		this.map.addMapLayer(GameResources.mapLayer0);
		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer2);

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
		for (int player = super.engineState.getComponents()
					  .getInitialSetIndex(PlayerSet.class);
		     Components.isValidEntity(player);
		     player = engineState.getComponents().getNextSetIndex(
			     PlayerSet.class, player)) {

			if (super.inputPoller.isKeyDown(KeyEvent.VK_W)) {
				System.out.println("w key is down");
				super.engineState
					.getComponentAt(WorldAttributes.class,
							player)
					.add(0, GameConfig.PLAYER_SPEED);
			}

			if (super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
				System.out.println("d key is down");
				super.engineState
					.getComponentAt(WorldAttributes.class,
							player)
					.add(GameConfig.PLAYER_SPEED, 0);
			}
			if (super.inputPoller.isKeyDown(KeyEvent.VK_S)) {
				System.out.println("s key is down");
				super.engineState
					.getComponentAt(WorldAttributes.class,
							player)
					.add(0, -GameConfig.PLAYER_SPEED);
			}
			if (super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
				System.out.println("a key is down");
				super.engineState
					.getComponentAt(WorldAttributes.class,
							player)
					.add(-GameConfig.PLAYER_SPEED, 0);
			}

			////// Build Commands //////
			if (super.inputPoller.isKeyDown(
				    GameConfig.BUILD_TOWER)) {
				System.out.print(
					"q key is down. Should spawn tower at player location\n");
				// TODO: get tile player is stood on
				// TODO: highlight that tile?
				// TODO: spawn new tower entity on tile
				// TODO: make tower spawn on key up? (to prevent
				// constant spawning if key down for more than 1
				// frame)
			}
			if (super.inputPoller.isKeyDown(
				    GameConfig.BUILD_TRAP)) {
				System.out.print(
					"e key is down. Should spawn trap at player location\n");
				// TODO: get tile player is stood on
				// TODO: highlight that tile?
				// TODO: spawn new trap entity on tile
				// TODO: make trap spawn on key up? (to prevent
				// constant spawning if key down for more than 1
				// frame)
			}

			////// Combat Commands //////
			if (super.inputPoller.isKeyDown(
				    GameConfig.ATTACK_KEY)) {
				System.out.print(
					"f key is down. Player character should be attacking\n");
				// TODO: find adjacent tiles (and any enemies on
				// them)
				// TODO: apply damage to enemies
				// TODO: attack on mouse click instead?
			}
			if (super.inputPoller.isLeftMouseButtonDown()) {
				System.out.println(
					"left mouse button is down. Player character should be attacking");
				// TODO: find adjacent tiles (and any enemies on
				// them)
				// TODO: apply damage to enemies
			}
			if (super.inputPoller.isKeyDown(
				    GameConfig.SWITCH_WEAPONS)) {
				System.out.print(
					"x key is down. Player character should be changing weapons\n");
				// TODO: implement different weapons
				// TODO: switch between weapons
				// player.switchWeapon();
			}

			super.engineState
				.getComponentAt(WorldAttributes.class, player)
				.print();
			System.out.println("x ="
					   + super.inputPoller.getMouseX());
			System.out.println("y ="
					   + super.inputPoller.getMouseY());
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
