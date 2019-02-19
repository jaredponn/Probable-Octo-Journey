package Game;

import Resources.*;
import Components.*;

import poj.Component.Components;
import poj.Time.Timer;
import poj.linear.Vector2f;
import poj.EngineState;

import Game.Camera;
import EntitySets.*;
import TileMap.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;

public class PlayGame extends World
{
	// Render
	private Map map;
	private HashSet<Integer>
		tileMapRenderHelperSet; // used to help render the tiles in O(1)
					// time

	// Camera
	private Camera cam;    // camera
	private Camera invCam; // inverse camera

	// Higher level game logic
	private int player;
	private int mob1;
	private Vector2f unitVecPlayerPosToMouseDelta;
	private CardinalDirections prevDirection = CardinalDirections.N;

	public PlayGame()
	{
		super();

		// World loading
		this.map = new Map(3);
		this.map.addTileSet(GameResources.tileSet);
		this.map.addMapConfig(GameResources.renderPerformanceConf);
		this.map.addMapLayer(GameResources.renderPerformanceLayer);

		/*this.map.addMapConfig(GameResources.pathFindTest1Config);
		this.map.addMapLayer(GameResources.pathFindTest1Layer);
		*/

		// this.map.addMapConfig(GameResources.mapConfig);
		// this.map.addMapLayer(GameResources.mapLayer0);

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
		super.engineState.registerComponent(CollisionBody.class);
		super.engineState.registerComponent(HasAnimation.class);
		super.engineState.registerComponent(Render.class);
		super.engineState.registerComponent(WorldAttributes.class);
		super.engineState.registerComponent(MovementDirection.class);
		super.engineState.registerComponent(FacingDirection.class);
		super.engineState.registerComponent(Movement.class);
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
		this.mob1 = super.engineState.spawnEntitySet(new MobSet());

		// TODO: HAIYANG get the layer number for the path finding!
		// right now for testing it only have 1 layer
		//
		MapLayer mapLayer = this.map.getLayerEngineState(0);

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
		EngineTransforms.setMovementVelocityFromMovementDirection(
			this.engineState);
		EngineTransforms.updateWorldAttribPositionFromMovement(
			this.engineState, this.dt);
		// updating the camera
		centerCamerasPositionToPlayer();
		updateInverseCamera();

		EngineTransforms.updateAnimationWindows(this.engineState,
							this.dt);
		EngineTransforms.cropSpriteSheetsFromAnimationWindows(
			this.engineState);

		EngineTransforms
			.updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera(
				this.engineState, this.cam);

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
				.setDirection(CardinalDirections.NE);
			super.getComponentAt(Movement.class, this.player)
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
			super.getComponentAt(Movement.class, this.player)
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
			prevDirection = CardinalDirections.SW;
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
				.setDirection(CardinalDirections.SE);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.SE;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_W)) {
			System.out.println("w key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.N);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.N;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerNMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
			System.out.println("d key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.E);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.E;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerEMoveAnimation);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("a key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.W);
			super.getComponentAt(Movement.class, this.player)
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
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.S;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					GameResources.playerSMoveAnimation);
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
		if (super.inputPoller.isKeyDown(GameConfig.ATTACK_KEY)
		    || super.inputPoller.isLeftMouseButtonDown()) {
			System.out.print(
				"space key is down. Player character should be attacking\n");
			this.playerShootBullet();
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
				.getOriginCoord();

		Vector2f mousePosition = super.inputPoller.getMousePosition();
		mousePosition.matrixMultiply(this.invCam);

		mousePosition.log("Mouse position in world coordinates");

		Vector2f tmp = playerPosition.pureSubtract(mousePosition);
		tmp.negate();
		CardinalDirections facingDirection =
			CardinalDirections
				.getClosestDirectionFromDirectionVector(tmp);

		super.getComponentAt(FacingDirection.class, player)
			.setDirection(facingDirection);

		this.unitVecPlayerPosToMouseDelta = tmp.pureNormalize();

		super.getComponentAt(FacingDirection.class, player).print();
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
				    || tileMapRenderHelperSet.contains(e))
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

	private void updateWorldAttribPositionFromMovement(double dt)
	{
		for (int i = super.getInitialComponentIndex(Movement.class);
		     Components.isValidEntity(i);
		     i = super.getNextComponentIndex(Movement.class, i)) {
			Systems.updateWorldAttribPositionFromMovement(
				super.getComponentAt(WorldAttributes.class, i),
				super.getComponentAt(Movement.class, i),
				this.dt);
		}
		System.out.println("start dif");
		addPlayerDiffusionValAtPlayerPos();
		System.out.println("end dif");
	}

	private void setMovementVelocityFromMovementDirection()
	{
		for (int i = super.getInitialSetIndex(MovementDirection.class);
		     Components.isValidEntity(i);
		     i = super.getNextSetIndex(MovementDirection.class, i)) {
			Systems.setMovementVelocityFromMovementDirection(
				super.getComponentAt(Movement.class, i),
				super.getComponentAt(MovementDirection.class,
						     i));
		}
	}

	private void centerCamerasPositionToPlayer()
	{
		this.centerCamerasPositionsToWorldAttribute(
			engineState.getComponentAt(WorldAttributes.class,
						   this.player));
	}

	// IMPORTANT: in world attributes  and PathFindCord, X is RowNum, and Y
	// is ColNum!!!!!!
	// Width is rows, height is cols
	private void generateDiffusionMap(int layerNumber, int difCoefficient)
	{
		// TODO: HAIYANG will only do one layer!!!!!
		// will get the 8 neighbours aroud it
		//
		// ArrayList<PathFindCord> tempNeighbours =
		// new ArrayList<PathFindCord>();
		MapLayer mapLayer = this.map.getLayerEngineState(layerNumber);
		int sum = 0;
		for (int i = mapLayer.getInitialComponentIndex(
			     WorldAttributes.class);
		     Components.isValidEntity(i);
		     i = mapLayer.getNextComponentIndex(WorldAttributes.class,
							i)) {
			// player initial val?
			// will first diffuse again,
			// then addPlayerDifVal
			// then diffuse again

			ArrayList<PathFindCord> tempNeighbours =
				new ArrayList<PathFindCord>();
			// Vector2f testVector=
			//

			for (int j = 0; j < 8; ++j) {
				// tempNeighbours.add
			}
			if (mapLayer.hasComponent(PathFindCord.class, i)) {
			}
			/*
			if (!(center.getIsWall() == true
			      || isValidCord(adfdsa, mapWidth, mapHeight))) {
				// sum +=
			}
			*/

			// if it is a wall or out of bounds, dont add it
			/*
			if (!(center.getIsWall() == true
			      || isValidCord(adfdsa, mapWidth, mapHeight))) {
				// sum +=
			}
			*/
		}
	}
	private void addPlayerDiffusionValAtPlayerPos()
	{


		Vector2f playerPosition =
			super.getComponentAt(WorldAttributes.class, this.player)
				.getOriginCoord();
		/*
		System.out.println("player X=" + playerPosition.x);
		System.out.println("player Y=" + playerPosition.y);
		System.out.println(
			"this.map.getEcsIndexFromWorldVector2f(playerPosition)"
			+ this.map.getEcsIndexFromWorldVector2f(
				  playerPosition));
		*/
		if (this.map.getEcsIndexFromWorldVector2f(playerPosition)
		    != -1) {

			MapLayer mapLayer = this.map.getLayerEngineState(0);
			this.map.printPathfindCord(0);
			mapLayer.getComponentAt(
					PathFindCord.class,
					this.map.getEcsIndexFromWorldVector2f(
						playerPosition))
				.setDiffusionValue(
					GameConfig.PLAYER_DIFFUSION_VALUE);
		}
	}
}
