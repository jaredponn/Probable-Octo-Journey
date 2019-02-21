package Game;

import Resources.*;
import Components.*;

import poj.Component.Components;
import poj.linear.Vector2f;
import poj.Render.*;

import Game.Camera;
import EntitySets.*;
import TileMap.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class PlayGame extends World
{
	// Render
	private Map map;
	private HashSet<Integer>
		tileMapRenderHelperSet; // used to help render the tiles in O(1)
					// time
	private MapLayer mapLayer;

	// Camera
	private Camera cam;    // camera
	private Camera invCam; // inverse camera


	private static ArrayList<Double> coolDownMax = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));
	private ArrayList<Double> lastCoolDown = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));
	// Higher level game logic
	private int player;
	private int mob1;
	private float EPSILON = 0.01f;
	private Vector2f unitVecPlayerPosToMouseDelta;
	private CardinalDirections prevDirection = CardinalDirections.N;

	public PlayGame()
	{
		super();

		// World loading
		this.map = new Map(3);
		this.map.addTileSet(GameResources.tileSet);
		this.map.addMapConfig(GameResources.pathFindTest1Config);
		this.map.addMapLayer(GameResources.pathFindTest1Layer);
		// adding the maximum cooldown for turrent


		// setting the build turrent coolDown
		for (int i = 0; i < GameConfig.COOL_DOWN_KEYS.size(); ++i) {
			coolDownMax.set(GameConfig.COOL_DOWN_KEYS.get(i).fst,
					GameConfig.COOL_DOWN_KEYS.get(i).snd);
		}
		// lastCoolDown.set(GameConfig.BUILD_TOWER, 0d);
		// this.map.addMapConfig(GameResources.pathFindTest1Config);
		// this.map.addMapLayer(GameResources.pathFindTest1Layer);

		// this.map.addMapConfig(GameResources.renderPerformanceConf);
		// this.map.addMapLayer(GameResources.renderPerformanceLayer);

		mapLayer = this.map.getLayerEngineState(0);

		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer2);
		this.tileMapRenderHelperSet = new HashSet<Integer>(
			(int)(this.windowWidth * this.windowHeight)
			/ (int)(GameResources.TILE_SCREEN_WIDTH
				* GameResources.TILE_SCREEN_HEIGHT));

		this.cam = new Camera();
		this.unitVecPlayerPosToMouseDelta = new Vector2f();

		// camera intilization
		resetCamera();

		this.invCam = new Camera();
		this.updateInverseCamera();
	}

	public void registerComponents()
	{
		super.engineState.registerComponent(HasAnimation.class);
		super.engineState.registerComponent(Render.class);
		super.engineState.registerComponent(WorldAttributes.class);
		super.engineState.registerComponent(MovementDirection.class);
		super.engineState.registerComponent(FacingDirection.class);
		super.engineState.registerComponent(Movement.class);
		super.engineState.registerComponent(CollisionAabbBodies.class);
		super.engineState.registerComponent(AabbCollisionBody.class);
		super.engineState.registerComponent(CircleCollisionBody.class);
	}
	public void registerEntitySets()
	{
		super.engineState.registerSet(PlayerSet.class);
		super.engineState.registerSet(MobSet.class);
		super.engineState.registerSet(ConstructSet.class);
		super.engineState.registerSet(Bullet.class);
		super.engineState.registerSet(TurrentSet.class);
	}

	// higher game logic functions
	public void spawnWorld()

	{
		// Player
		this.player = super.engineState.spawnEntitySet(new PlayerSet());
		this.mob1 = super.engineState.spawnEntitySet(new MobSet());


		// ------
		/*
		super.engineState
			.getComponentAt(
				WorldAttributes.class,
				super.engineState.spawnEntitySet(new MobSet()))
			.setOriginCoord(new Vector2f(5, 6));
		super.engineState
			.getComponentAt(
				WorldAttributes.class,
				super.engineState.spawnEntitySet(new MobSet()))
			.setOriginCoord(new Vector2f(5, 7));

		super.engineState
			.getComponentAt(
				WorldAttributes.class,
				super.engineState.spawnEntitySet(new MobSet()))
			.setOriginCoord(new Vector2f(5, 8));

		super.engineState
			.getComponentAt(
				WorldAttributes.class,
				super.engineState.spawnEntitySet(new MobSet()))
			.setOriginCoord(new Vector2f(6, 8));
			*/
		// ------

		EngineTransforms.addPlayerDiffusionValAtPlayerPos(
			this.engineState, this.map, this.mapLayer, this.player);
		// TODO: HAIYANG get the layer number for the path finding!
		// right now for testing it only have 1 layer


		int tmp = super.engineState.spawnEntitySet(new Bullet());
		super.getComponentAt(WorldAttributes.class, tmp)
			.setOriginCoord(new Vector2f(0f, 0f));
		super.getComponentAt(Movement.class, tmp).setSpeed(0);

		clearTime();
	}
	public void clearWorld()
	{
	}

	// use super.acct for the accumlated time, use this.dt for the time
	// step. Time is all in milliseconds
	public void runGame()
	{
		this.processInputs();

		// SYSTEMS Go here
		// this.setMovementVelocityFromMovementDirection();
		// this.updateWorldAttribPositionFromMovement(this.dt);

		// will set the enemy direction and speed, then will render them
		// next frame


		EngineTransforms.setMovementVelocityFromMovementDirection(
			this.engineState);

		EngineTransforms.updateCircleCollisionFromWorldAttributes(
			engineState);
		EngineTransforms.debugCircleCollisionRender(
			engineState, super.renderer, this.cam);

		EngineTransforms.updateWorldAttribPositionFromMovement(
			this.engineState, this.dt);
		EngineTransforms.generateDiffusionMap(this.map, this.mapLayer,
						      0, 1f / 8f);
		EngineTransforms.updateEnemyPositionFromPlayer(
			this.engineState, this.map, this.mapLayer, this.player,
			this.mob1);
		// this.generateDiffusionMap(0, 1f / 8f);
		// this.updateEnemyPositionFromPlayer();

		// updating the camera
		centerCamerasPositionToPlayer();
		updateInverseCamera();
		updateCoolDownKeys();

		EngineTransforms.updateAnimationWindows(this.engineState,
							this.dt);
		EngineTransforms.cropSpriteSheetsFromAnimationWindows(
			this.engineState);

		EngineTransforms
			.updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera(
				this.engineState, this.cam);

		System.out.println("----------------------- end one loop");
		// rendering is run after this is run
	}


	protected void processInputs()
	{

		////// Movement Commands //////
		if (super.inputPoller.isKeyDown(KeyEvent.VK_W)
		    && super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
			System.out.println("wd key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.NW);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.NW;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerNMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_W)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("wa key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.SW);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.SW;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerNMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("sa key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.SE);
			prevDirection = CardinalDirections.SE;
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_D)) {

			System.out.println("sd key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.NE);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.NE;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);

		} else if (super.inputPoller.isKeyDown(
				   KeyEvent.VK_W)) { // single Key movements
			System.out.println("w key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.W);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.W;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerWMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
			System.out.println("d key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.N);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.N;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerNMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("a key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.S);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.S;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)) {
			System.out.println("s key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.E);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.E;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerEMoveAnimation);
		} else // no movement key is pressed
		{
			super.getComponentAt(Movement.class, this.player)
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
			if (Math.abs(lastCoolDown.get(GameConfig.BUILD_TOWER))
			    <= EPSILON) {
				Vector2f playerPosition =
					super.getComponentAt(
						     WorldAttributes.class,
						     this.player)
						.getOriginCoord();
				int tmp = super.engineState.spawnEntitySet(
					new TurrentSet());
				super.getComponentAt(WorldAttributes.class, tmp)
					.setOriginCoord(playerPosition);
				// reset the lastCooldown key to the max
				// cooldown of that key
				updateDtForKey(GameConfig.BUILD_TOWER,
					       -coolDownMax.get(
						       GameConfig.BUILD_TOWER));
				// lastCoolDown.set(GameConfig.BUILD_TOWER,
				//-coolDownMax.get(
				// GameConfig.BUILD_TOWER));
			}

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
		if (super.inputPoller.isKeyDown(GameConfig.ATTACK_KEY)
		    || super.inputPoller.isLeftMouseButtonDown()) {
			System.out.print(
				"space key is down. Player character should be attacking\n");

			System.out.println("the cd value of attack key = "
					   + Math.abs(lastCoolDown.get(
						     GameConfig.ATTACK_KEY)));
			if (Math.abs(lastCoolDown.get(GameConfig.ATTACK_KEY))
			    <= EPSILON) {
				updateDtForKey(GameConfig.ATTACK_KEY,
					       -coolDownMax.get(
						       GameConfig.ATTACK_KEY));
				this.playerShootBullet();
				// lastCoolDown.set(GameConfig.BUILD_TOWER,
				//-coolDownMax.get(
				// GameConfig.BUILD_TOWER));
			}

			// TODO: find adjacent tiles (and any enemies on
			// them)
			// TODO: apply damage to enemies
			// TODO: attack on mouse click instead?
		}
		if (super.inputPoller.isKeyDown(GameConfig.SWITCH_WEAPONS)) {
			System.out.print(
				"x key is down. Player character should be changing weapons\n");
			// TODO: implement different weapons
			// TODO: switch between weapons
			// player.switchWeapon();
		}

		////// Mouse handling  //////
		Vector2f playerPosition =
			super.getComponentAt(WorldAttributes.class, this.player)
				.getCenteredBottomQuarter();

		Vector2f mousePosition = super.inputPoller.getMousePosition();
		mousePosition.matrixMultiply(this.invCam);

		// mousePosition.log("Mouse position in world coordinates");

		Vector2f tmp = playerPosition.pureSubtract(mousePosition);
		tmp.negate();
		CardinalDirections facingDirection =
			CardinalDirections
				.getClosestDirectionFromDirectionVector(tmp);

		super.getComponentAt(FacingDirection.class, player)
			.setDirection(facingDirection);

		this.unitVecPlayerPosToMouseDelta = tmp.pureNormalize();

		// super.getComponentAt(FacingDirection.class, player).print();
		/*
		super.getComponentAt(WorldAttributes.class, this.player)
			.print();*/
	}

	// Render function
	protected void render()
	{


		this.pushTileMapLayerToRenderer(
			this.map.getLayerEngineState(0));

		for (Render r :
		     super.getRawComponentArrayListPackedData(Render.class)) {
			Systems.cullPushRenderComponentToRenderer(
				r, super.renderer, this.windowWidth,
				this.windowHeight);
		}

		super.renderer.render();
	}

	// Renders a set window of the tilemap
	private void pushTileMapLayerToRenderer(MapLayer tileLayer)
	{
		tileMapRenderHelperSet.clear();
		for (float i = -GameResources.TILE_SCREEN_WIDTH;
		     i <= this.windowWidth + GameResources.TILE_SCREEN_WIDTH;
		     i += GameResources.TILE_SCREEN_WIDTH / 2f) {
			for (float j = -GameResources.TILE_SCREEN_HEIGHT;
			     j
			     <= this.windowHeight
					+ 3 * GameResources.TILE_SCREEN_HEIGHT;
			     j += GameResources.TILE_SCREEN_HEIGHT / 2f) {
				Vector2f wc =
					new Vector2f(i, j).pureMatrixMultiply(
						this.invCam);

				int e = this.map.getEcsIndexFromWorldVector2f(
					wc);

				if (e == -1
				    || tileMapRenderHelperSet.contains(e)
				    || !tileLayer.hasComponent(Render.class, e))
					continue;

				Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
					tileLayer.getComponentAt(
						WorldAttributes.class, e),
					tileLayer.getComponentAt(Render.class,
								 e),
					this.cam);
				Systems.pushRenderComponentToRenderer(
					tileLayer.getComponentAt(Render.class,
								 e),
					super.renderer);
				tileMapRenderHelperSet.add(e);
			}
		}
	}


	private void updateInverseCamera()
	{
		if (this.cam.isInvertible()) {
			this.invCam =
				new Camera((this.cam.unsafePureInverse()));
		}
	}

	private void resetCamera()
	{
		this.cam.clearBackToIdentity();
		this.cam.setScalingForVector2(-GameResources.TILE_SCREEN_WIDTH,
					      GameResources.TILE_SCREEN_HEIGHT);
		this.cam.composeWithRotationForVector2XaxisCC(
			GameResources.TILE_SCREEN_ROTATION);
		this.cam.composeSetScalingForVector2(
			GameResources.MAGIC_CONSTANT,
			GameResources.MAGIC_CONSTANT);
		/*
		this.cam.setScalingForVector2(-GameResources.TILE_SCREEN_WIDTH,
					      GameResources.TILE_SCREEN_HEIGHT);
		this.cam.composeWithRotationForVector2XaxisCC(
			GameResources.TILE_SCREEN_ROTATION);*/
	}

	private void centerCamerasPositionsToWorldAttribute(WorldAttributes n)
	{
		this.resetCamera();
		Vector2f tmp = n.getOriginCoord();
		tmp.matrixMultiply(this.cam);

		this.cam.setTranslationForVector2(
			-tmp.x + super.windowWidth / 2f,
			-tmp.y + super.windowHeight / 2f);
	}

	private void playerShootBullet()
	{
		int e = super.engineState.spawnEntitySet(new Bullet());
		float bulletSpeed =
			super.getComponentAt(Movement.class, e).getSpeed();
		Vector2f tmp = new Vector2f(
			super.getComponentAt(WorldAttributes.class, this.player)
				.getOriginCoord());

		super.getComponentAt(WorldAttributes.class, e)
			.setOriginCoord(tmp);

		super.getComponentAt(Movement.class, e)
			.setVelocity(this.unitVecPlayerPosToMouseDelta.pureMul(
				bulletSpeed));
	}


	private void centerCamerasPositionToPlayer()
	{
		this.centerCamerasPositionsToWorldAttribute(
			engineState.getComponentAt(WorldAttributes.class,
						   this.player));
	}
	private void updateCoolDownKeys()
	{
		for (int i = 0; i < Resources.GameConfig.COOL_DOWN_KEYS.size();
		     ++i) {
			updateDtForKey(
				Resources.GameConfig.COOL_DOWN_KEYS.get(i).fst,
				this.dt / 1000);
		}
	}
	private void updateDtForKey(int keyIndex, double val)
	{
		// if the key cooldown is not 0.. i put a if statement here
		// becasuse i don't want to subtract it to neg infinity..
		if (lastCoolDown.get(keyIndex) - val >= 0d) {
			lastCoolDown.set(keyIndex,
					 lastCoolDown.get(keyIndex) - val);
		}
	}
}
