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
		this.map.addMapConfig(GameResources.pathFindTest1Config);
		this.map.addMapLayer(GameResources.pathFindTest1Layer);

		// this.map.addMapConfig(GameResources.pathFindTest1Config);
		// this.map.addMapLayer(GameResources.pathFindTest1Layer);

		// this.map.addMapConfig(GameResources.renderPerformanceConf);
		// this.map.addMapLayer(GameResources.renderPerformanceLayer);

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
		super.engineState.registerComponent(HasAnimation.class);
		super.engineState.registerComponent(Render.class);
		super.engineState.registerComponent(WorldAttributes.class);
		super.engineState.registerComponent(MovementDirection.class);
		super.engineState.registerComponent(FacingDirection.class);
		super.engineState.registerComponent(Movement.class);
		super.engineState.registerComponent(CollisionBoxBody.class);
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
			this.engineState, this.map, this.player);
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
		// this.setMovementVelocityFromMovementDirection();
		// this.updateWorldAttribPositionFromMovement(this.dt);

		// will set the enemy direction and speed, then will render them
		// next frame


		EngineTransforms.setMovementVelocityFromMovementDirection(
			this.engineState);
		EngineTransforms
			.updateCollisionBoxBodyTopLeftFromWorldAttributes(
				engineState);
		EngineTransforms.debugCollisionRenderPush(
			this.engineState, this.renderer, this.cam);

		EngineTransforms.checkCollisionsBetween(
			engineState, PlayerSet.class, MobSet.class);
		// EngineTransforms.resolveCollisionsOfEntitySets(
		//	engineState, PlayerSet.class, MobSet.class, this.dt);

		EngineTransforms.updateWorldAttribPositionFromMovement(
			this.engineState, this.dt);

		// this.generateDiffusionMap(0, 1f / 8f);
		// this.updateEnemyPositionFromPlayer();

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

	// IMPORTANT: in world attributes  and PathFindCord, X is RowNum, and Y
	// is ColNum!!!!!!
	// Width is rows, height is cols
	private void generateDiffusionMap(int layerNumber, float difCoefficient)
	{
		// TODO: HAIYANG will only do one layer!!!!!
		// will get the 8 neighbours aroud it

		MapLayer mapLayer = this.map.getLayerEngineState(layerNumber);
		// temporary buffer used to store the modified diffusion values
		int playerECSindex = this.map.getEcsIndexFromWorldVector2f(
			super.getComponentAt(WorldAttributes.class, this.player)
				.getCenteredBottomQuarter());
		ArrayList<Float> tempDiffusionBuffer = new ArrayList<Float>();
		ArrayList<Float> prevDiffusionBuffer = new ArrayList<Float>();
		// will not loop to the empty tiles inside the map, hopefull !!
		for (int i = mapLayer.getInitialComponentIndex(
			     WorldAttributes.class);
		     Components.isValidEntity(i);
		     i = mapLayer.getNextComponentIndex(WorldAttributes.class,
							i)) {

			// Vector2f testVector=
			//

			if (mapLayer.hasComponent(PathFindCord.class, i)) {
				float sum = 0f;
				PathFindCord center = mapLayer.getComponentAt(
					PathFindCord.class, i);
				prevDiffusionBuffer.add(
					this.map.getLayerEngineState(0)
						.getComponentAt(
							PathFindCord.class, i)
						.getDiffusionValue());
				/*
				System.out.println(
					"center's diffusion value: = "
					+ center.getDiffusionValue());
					*/
				if (!center.getIsWall()) {
					ArrayList<PathFindCord> tempNeighbours =
						getEightNeighbourVector(i, 0);
					// System.out.println("size of
					// tempNeighbours ="
					//+ tempNeighbours.size());
					/*
					for (PathFindCord a : tempNeighbours) {
						a.printCord();
					}
					*/
					for (PathFindCord a : tempNeighbours) {
						// if not a wall
						if (!a.getIsWall()) {
							sum += a.getDiffusionValue()
							       - center.getDiffusionValue();
						} else {
							System.out.println(
								"I did not pass!!, the Vector x  index is ="
								+ a.getCord()
									  .x);
							System.out.println(
								"I did not pass!!, the Vector y  index is ="
								+ a.getCord()
									  .y);
						}
					}
					/*
					System.out.println(
						"sum before adding center
					diffusion value = "
						+ sum);
					*/
					sum = center.getDiffusionValue()
					      + sum * difCoefficient;
					System.out.println("playerECSindex = "
							   + playerECSindex);
					sum = sum * 1 / 2;
					/*
					if (i != playerECSindex) {
						sum = sum * 1 / 2;
					}
					*/


					tempDiffusionBuffer.add(sum);
					/*
					System.out.println(
						"sum after adding center
					diffusion value = "
						+ sum);
					*/
				} else {
					sum = 0f;
					tempDiffusionBuffer.add(sum);
				}
			}
		}

		int counter = 0;
		for (int i = 0; i < tempDiffusionBuffer.size(); ++i) {
			if (counter == this.map.mapWidth) {
				counter = 0;
				System.out.println();
			}
			System.out.print(tempDiffusionBuffer.get(i) + " , ");
			counter++;
		}
		System.out.println();
		System.out.println("the diffusion value map it should be..");
		counter = 0;
		for (int i = 0; i < prevDiffusionBuffer.size(); ++i) {
			if (counter == this.map.mapWidth) {
				counter = 0;
				System.out.println();
			}
			System.out.print(prevDiffusionBuffer.get(i) + " , ");
			counter++;
		}
		System.out.println();
		if (tempDiffusionBuffer.size() > 0) {
			for (int i = mapLayer.getInitialComponentIndex(
				     WorldAttributes.class);
			     Components.isValidEntity(i);
			     i = mapLayer.getNextComponentIndex(
				     WorldAttributes.class, i)) {
				mapLayer.getComponentAt(PathFindCord.class, i)
					.setDiffusionValue(
						tempDiffusionBuffer.get(0));
				tempDiffusionBuffer.remove(0);
			}
		}
	}
	private ArrayList<PathFindCord> getEightNeighbourVector(int indexOfEcs,
								int layerNumber)
	{
		MapLayer mapLayer = this.map.getLayerEngineState(layerNumber);
		ArrayList<Vector2f> neighbours = new ArrayList<Vector2f>();
		ArrayList<PathFindCord> tmp = new ArrayList<PathFindCord>();
		Vector2f centerVector =
			mapLayer.getComponentAt(PathFindCord.class, indexOfEcs)
				.getCord();
		// add the 8 neighbours
		neighbours.add(centerVector.addAndReturnVector(0, 1));
		neighbours.add(centerVector.addAndReturnVector(0, -1));
		neighbours.add(centerVector.addAndReturnVector(1, 0));
		neighbours.add(centerVector.addAndReturnVector(-1, 0));
		neighbours.add(centerVector.addAndReturnVector(1, 1));
		neighbours.add(centerVector.addAndReturnVector(-1, 1));
		neighbours.add(centerVector.addAndReturnVector(1, -1));
		neighbours.add(centerVector.addAndReturnVector(-1, -1));
		for (Vector2f neib : neighbours) {
			// if the tile is valid
			if (this.map.isValidCord(neib)) {
				tmp.add(mapLayer.getComponentAt(
					PathFindCord.class,
					this.map.getEcsIndexFromWorldVector2f(
						neib)));
			}
		}
		return tmp;
	}

	// TODO: HAIYANG
	// 1. Please make a no-direction movement..
	private void updateEnemyPositionFromPlayer()
	{
		EngineTransforms.addPlayerDiffusionValAtPlayerPos(
			this.engineState, this.map, this.player);
		System.out.println("mob position in world attribues: ");
		System.out.println(
			"mob ecs cord= "
			+ this.map.getEcsCordFromWorldAttributes(
				  super.getComponentAt(WorldAttributes.class,
						       this.mob1)));
		System.out.println(
			"mob x dir="
			+ super.getComponentAt(WorldAttributes.class, this.mob1)
				  .getCenteredBottomQuarter()
				  .x);

		System.out.println(
			"mob y dir="
			+ super.getComponentAt(WorldAttributes.class, this.mob1)
				  .getCenteredBottomQuarter()
				  .y);
		System.out.println(this.map.isValidCord(
			super.getComponentAt(WorldAttributes.class, this.mob1)
				.getCenteredBottomQuarter()));
		ArrayList<PathFindCord> mobNeighb = getEightNeighbourVector(
			this.map.getEcsIndexFromWorldVector2f(
				super.getComponentAt(WorldAttributes.class,
						     this.mob1)
					.getCenteredBottomQuarter()),
			0);
		float maxValue = 0;
		Vector2f maxPosition = new Vector2f();
		Vector2f mobPosition =
			super.getComponentAt(WorldAttributes.class, this.mob1)
				.getCenteredBottomQuarter();

		Vector2f playerPosition =
			super.getComponentAt(WorldAttributes.class, this.player)
				.getCenteredBottomQuarter();
		/*
		System.out.println("mob x position: " + mobPosition.x);
		System.out.println("mob y position: " + mobPosition.y);
		System.out.println("mob x floored position: "
				   + (int)mobPosition.x);
		System.out.println("mob y floored position: "
				   + (int)mobPosition.y);
		System.out.println("player x position: " + playerPosition.x);
		System.out.println("player y position: " + playerPosition.y);
		System.out.println("player x floored position: "
				   + (int)playerPosition.x);
		System.out.println("player y floored position: "
				   + (int)playerPosition.y);
				   */
		System.out.println("player x position  floor ="
				   + playerPosition.x);
		System.out.println("player y position  floor ="
				   + playerPosition.y);
		System.out.println("mob x position before floor ="
				   + mobPosition.x);
		System.out.println("mob y position before floor ="
				   + mobPosition.y);
		/*
		System.out.println("mob x position after  floor ="
				   + mobPosition.x);
		System.out.println("mob y position after  floor ="
				   + mobPosition.y);
				   */

		// else {
		for (PathFindCord neib : mobNeighb) {
			if (neib.getDiffusionValue() > maxValue) {
				maxValue = neib.getDiffusionValue();
				maxPosition = neib.getCord();
			}
		}
		System.out.println(" the max value calculated for the enemy: "
				   + maxValue);
		System.out.println("maxPosition x =" + maxPosition.x);
		System.out.println("maxPosition y =" + maxPosition.y);
		System.out.println("cardinal inside enemy :");
		CardinalDirections.print(
			CardinalDirections
				.getClosestDirectionFromDirectionVector(
					maxPosition.subtractAndReturnVector(
						mobPosition)));
		System.out.println(
			" the diffusion value at mob is ="
			+ this.map.getLayerEngineState(0)
				  .getComponentAt(
					  PathFindCord.class,
					  this.map.getEcsIndexFromWorldVector2f(
						  super.getComponentAt(
							       WorldAttributes
								       .class,
							       this.mob1)
							  .getCenteredBottomQuarter()))
				  .getDiffusionValue());
		if (maxValue
		    < this.map.getLayerEngineState(0)
			      .getComponentAt(
				      PathFindCord.class,
				      this.map.getEcsIndexFromWorldVector2f(
					      super.getComponentAt(
							   WorldAttributes
								   .class,
							   this.mob1)
						      .getCenteredBottomQuarter()))
			      .getDiffusionValue()

		) {
			System.out.println(
				" went inside this cord is bigger than all neightbours!!");
			System.out.println("player x position  floor ="
					   + playerPosition.x);
			System.out.println("player y position  floor ="
					   + playerPosition.y);
			System.out.println("mob x position before floor ="
					   + mobPosition.x);
			System.out.println("mob y position before floor ="
					   + mobPosition.y);
			if (Math.abs(mobPosition.x - playerPosition.x) != 0f
			    && Math.abs(mobPosition.y - playerPosition.y)
				       != 0f) {
				super.getComponentAt(MovementDirection.class,
						     this.mob1)
					.setDirection(CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition)));
				super.getComponentAt(Movement.class, this.mob1)
					.setSpeed(GameConfig.MOB_SPEED);
			} else {
				System.out.println(
					"set the mob speed equal to 0!!!!!!!");
				super.getComponentAt(Movement.class, this.mob1)
					.setSpeed(0f);
			}
		} else {
			mobPosition.floor();
			super.getComponentAt(MovementDirection.class, this.mob1)
				.setDirection(
					CardinalDirections.getClosestDirectionFromDirectionVector(
						maxPosition
							.subtractAndReturnVector(
								mobPosition)));
			super.getComponentAt(Movement.class, this.mob1)
				.setSpeed(GameConfig.MOB_SPEED);
		}
		//}
	}
}
