package Game;

import Resources.*;
import Components.*;

import poj.Component.Components;
import poj.Time.Timer;
import poj.linear.Vector2f;
import poj.EngineState;

import Game.Camera;

import EntitySets.*;
import TileMap.Map;

import EntityTransforms.*;

import java.awt.event.KeyEvent;

public class PlayGame extends World
{
	private Map map;
	private Camera cam;    // camera
	private Camera invCam; // inverse camera

	private int player;
	private CardinalDirections prevDirection = CardinalDirections.N;
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

		// camera intilization
		this.cam.setScalingForVector2(GameResources.TILE_SCREEN_WIDTH,
					      GameResources.TILE_SCREEN_HEIGHT);
		this.cam.composeWithRotationForVector2XaxisCC(
			GameResources.TILE_SCREEN_ROTATION);

		this.invCam = new Camera();
		this.updateInverseCamera();
	}

	public void registerComponents()
	{
		super.engineState.registerComponent(CollisionBody.class);
		super.engineState.registerComponent(HasAnimation.class);
		super.engineState.registerComponent(Render.class);
		super.engineState.registerComponent(TileCord.class);
		super.engineState.registerComponent(WorldAttributes.class);
		super.engineState.registerComponent(MovementDirection.class);
		super.engineState.registerComponent(FacingDirection.class);
		super.engineState.registerComponent(Speed.class);
	}
	public void registerEntitySets()
	{
		super.engineState.registerSet(PlayerSet.class);
		super.engineState.registerSet(MobSet.class);
		super.engineState.registerSet(ConstructSet.class);
		super.engineState.registerSet(Bullet.class);
	}

	// higher game logic functions
	public void spawnWorld()

	{
		// Player
		this.player = super.engineState.spawnEntitySet(new PlayerSet());
		super.engineState.spawnEntitySet(new MobSet());
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
			this.updateWorldAttribPositionFromDirectionAndSpeed(
				this.dt);
			this.centerCamerasPositionToPlayer();
			this.updateAnimationWindows();
			this.cropSpriteSheetsFromAnimationWindows();
			this.updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera();

			this.render();

			// updating the camera
			this.centerCamerasPositionToPlayer();

			this.updateInverseCamera();

			super.setFinalTime();

			super.calculateDeltaTime();

			Timer.dynamicSleepToFrameRate(78, super.dt);
		}
	}


	protected void processInputs()
	{

		////// Movement Commands //////
		if (super.inputPoller.isKeyDown(KeyEvent.VK_W)
		    && super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
			System.out.println("wd key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.NE);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.NE;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerNMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_W)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("wa key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.NW);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.NW;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerNMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("sa key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.SW);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.SW;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
			System.out.println("sd key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.SW);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_W)) {
			System.out.println("w key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.N);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.N;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerNMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("a key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.E);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.E;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerEMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
			System.out.println("d key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.W);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.W;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerWMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)) {
			System.out.println("s key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.S);
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.S;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);
		} else // no movement key is pressed
		{
			super.getComponentAt(Speed.class, this.player)
				.setSpeed(0);
			// TODO idle direction!!!!!

			super.getComponentAt(FacingDirection.class,
					     this.player);


			switch (prevDirection) {
			case N:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerNIdleAnimation);
				break;
			case NE:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerNIdleAnimation);
				break;
			case NW:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerNIdleAnimation);

				break;
			case S:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerSIdleAnimation);

				break;
			case SE:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerSIdleAnimation);

				break;
			case SW:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerSIdleAnimation);

				break;
			case W:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerWIdleAnimation);

				break;
			case E:
				super.getComponentAt(HasAnimation.class,
						     this.player)
					.setAnimation(
						GameResources
							.playerEIdleAnimation);

				break;
			}
		}

		////// Build Commands //////
		if (super.inputPoller.isKeyDown(GameConfig.BUILD_TOWER)) {
			System.out.print(
				"q key is down. Should spawn tower at player location\n");
			// TODO: get tile player is stood on
			// TODO: highlight that tile?
			// TODO: spawn new tower entity on tile
			// TODO: make tower spawn on key up? (to prevent
			// constant spawning if key down for more than 1
			// frame)
		}
		if (super.inputPoller.isKeyDown(GameConfig.BUILD_TRAP)) {
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
		if (super.inputPoller.isKeyDown(GameConfig.ATTACK_KEY)) {
			System.out.print(
				"f key is down. Player character should be attacking\n");
			playerShootBullet();
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
		if (super.inputPoller.isKeyDown(GameConfig.SWITCH_WEAPONS)) {
			System.out.print(
				"x key is down. Player character should be changing weapons\n");
			// TODO: implement different weapons
			// TODO: switch between weapons
			// player.switchWeapon();
		}

		////// Mouse handling  //////
		Vector2f playerScreenPosition =
			new Vector2f(windowWidth / 2f, windowHeight / 2f);
		Vector2f mouseScreenPosition =
			super.inputPoller.getMousePosition();
		Vector2f tmp =
			playerScreenPosition.pureSubtract(mouseScreenPosition);
		CardinalDirections facingDirection =
			CardinalDirections
				.getClosestDirectionFromDirectionVector(tmp);
		super.getComponentAt(FacingDirection.class, player)
			.setDirection(facingDirection);

		super.getComponentAt(FacingDirection.class, player).print();

		super.getComponentAt(WorldAttributes.class, this.player)
			.print();
	}

	// Render function
	protected void render()
	{
		EngineState tileLayer = this.map.getLayerEngineState(0);
		for (int i = tileLayer.getInitialComponentIndex(Render.class);
		     Components.isValidEntity(i);
		     i = tileLayer.getNextComponentIndex(Render.class, i)) {

			Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
				tileLayer.getComponentAt(WorldAttributes.class,
							 i),
				tileLayer.getComponentAt(Render.class, i),
				this.cam);
			Systems.pushRenderComponentToRenderer(
				tileLayer.getComponentAt(Render.class, i),
				super.renderer);
		}

		for (Render r :
		     super.getRawComponentArrayListPackedData(Render.class)) {
			Systems.pushRenderComponentToRenderer(r,
							      super.renderer);
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

	private void centerCamerasPositionsToWorldAttribute(WorldAttributes n)
	{
		Vector2f tmp = n.getOriginCoord();

		tmp.matrixMultiply(this.cam);

		this.cam.setTranslationForVector2(
			-tmp.x + super.windowWidth / 2f,
			-tmp.y + super.windowHeight / 2f);
	}

	private void playerShootBullet()
	{
		int e = super.engineState.spawnEntitySet(new Bullet());
		super.getComponentAt(WorldAttributes.class, e)
			.setOriginCoord(
				super.getComponentAt(WorldAttributes.class,
						     this.player)
					.getOriginCoord());
		super.getComponentAt(MovementDirection.class, e)
			.setDirection(
				super.getComponentAt(FacingDirection.class,
						     this.player)
					.getDirection());
	}

	/// SYSTEMS ///
	private void updateAnimationWindows()
	{
		for (HasAnimation a : super.getRawComponentArrayListPackedData(
			     HasAnimation.class)) {
			Systems.updateHasAnimationComponent(a, this.dt);
		}
	}

	private void cropSpriteSheetsFromAnimationWindows()
	{

		for (int i = super.getInitialComponentIndex(HasAnimation.class);
		     Components.isValidEntity(i);
		     i = super.getNextComponentIndex(HasAnimation.class, i)) {
			Systems.updateRenderComponentWindowFromHasAnimation(
				super.getComponentAt(Render.class, i),
				super.getComponentAt(HasAnimation.class, i));
		}
	}


	private void
	updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera()
	{

		for (int i = super.getInitialComponentIndex(
			     WorldAttributes.class);
		     Components.isValidEntity(i);
		     i = super.getNextComponentIndex(WorldAttributes.class,
						     i)) {

			Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
				super.getComponentAt(WorldAttributes.class, i),
				super.getComponentAt(Render.class, i),
				this.cam);
		}
	}

	private void updateWorldAttribPositionFromDirectionAndSpeed(float dt)
	{
		for (int i = super.getInitialComponentIndex(
			     MovementDirection.class);
		     Components.isValidEntity(i);
		     i = super.getNextComponentIndex(MovementDirection.class,
						     i)) {
			Vector2f tmp = Systems.getVelocityFromDirectionAndSpeed(
				getComponentAt(MovementDirection.class, i),
				getComponentAt(Speed.class, i));
			tmp.mul(dt);
			getComponentAt(WorldAttributes.class, i).add(tmp);
		}
	}

	private void centerCamerasPositionToPlayer()
	{
		this.centerCamerasPositionsToWorldAttribute(
			engineState.getComponentAt(WorldAttributes.class,
						   this.player));
	}
}
