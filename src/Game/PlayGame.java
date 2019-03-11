package Game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;

import Components.AttackCycle;
import Components.CardinalDirections;
import Components.FacingDirection;
import Components.HasAnimation;
import Components.HitPoints;
import Components.Lifespan;
import Components.Movement;
import Components.MovementDirection;
import Components.PCollisionBody;
import Components.Render;
import Components.WorldAttributes;
import EntitySets.Bullet;
import EntitySets.CollectibleSet;
import EntitySets.ConstructSet;
import EntitySets.MobSet;
import EntitySets.PlayerSet;
import EntitySets.TurretSet;
import PathFinding.MapGeneration;
import Resources.GameConfig;
import Resources.GameResources;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Pair;
import poj.Collisions.GJK;
import poj.Logger.Logger;
import poj.Render.MinYFirstSortedRenderObjectBuffer;
import poj.Render.RenderObject;
import poj.Render.StringRenderObject;
import poj.linear.Vector2f;

import poj.Component.Components;

public class PlayGame extends World
{
	// Render
	private Map map;

	// buffers for the renderer
	private Queue<RenderObject> groundBuffer;
	private MinYFirstSortedRenderObjectBuffer entityBuffer;
	private Queue<RenderObject> guiBuffer;
	private Queue<RenderObject> debugBuffer;

	// private MapLayer mapLayer;
	// Camera
	private Camera cam;    // camera
	private Camera invCam; // inverse camera


	private static ArrayList<Double> coolDownMax = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));
	private ArrayList<Double> lastCoolDown = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));

	// Higher level game logic
	private int player;
	public static double EPSILON = 0.0001d;
	private CardinalDirections prevDirection = CardinalDirections.N;
	private WeaponState curWeaponState = WeaponState.Gun;

	// ASE
	private double timeOfLastMobSpawn = 0.0;
	private double timeOfLastCashSpawn =
		0.0 - GameConfig.PICKUP_CASH_SPAWN_TIME;
	private int cash = 1000;

	private StringRenderObject gameTimer =
		new StringRenderObject("", 5, 10, Color.WHITE);
	private StringRenderObject cashDisplay = new StringRenderObject(
		"Your Cash: " + this.cash, 5, 20, Color.WHITE);


	// /ASE

	// Collision detection and resolution
	GJK gjk;

	private MapGeneration generateDiffusionMap;
	public PlayGame()
	{
		super();

		gjk = new GJK();
		gjk.clearVerticies();

		// World loading
		this.map = new Map(3);
		this.map.addTileSet(GameResources.tileSet);
		this.map.addMapConfig(GameResources.demo1Config);
		this.map.addMapLayer(GameResources.demo1LayerGround);
		this.map.addMapLayer(GameResources.demo1LayerWall);
		// this.map.addMapConfig(GameResources.pathFindTest3Config);
		// this.map.addMapLayer(GameResources.pathFindTest1Layer);


		// setting the build turret coolDown
		for (int i = 0; i < GameConfig.COOL_DOWN_KEYS.size(); ++i) {
			coolDownMax.set(GameConfig.COOL_DOWN_KEYS.get(i).fst,
					GameConfig.COOL_DOWN_KEYS.get(i).snd);
		}
		// this.map.addMapConfig(GameResources.pathFindTest1Config);
		// this.map.addMapLayer(GameResources.pathFindTest1Layer);

		// this.map.addMapConfig(GameResources.renderPerformanceConf);
		// this.map.addMapLayer(GameResources.renderPerformanceLayer);

		// mapLayer = this.map.getLayerEngineState(0);

		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer1);
		// this.map.addMapLayer(GameResources.mapLayer2);

		this.cam = new Camera();

		this.groundBuffer = new LinkedList<RenderObject>();
		this.entityBuffer = new MinYFirstSortedRenderObjectBuffer();
		this.guiBuffer = new LinkedList<RenderObject>();
		this.debugBuffer = new LinkedList<RenderObject>();

		// camera initialization
		resetCamera();

		this.invCam = new Camera();
		this.updateInverseCamera();

		// initialize the path finding thread
		this.generateDiffusionMap =
			new MapGeneration(this.map, 0, 1f / 8f);
	}

	public void registerComponents()
	{
		super.engineState.registerComponent(HasAnimation.class);
		super.engineState.registerComponent(Render.class);
		super.engineState.registerComponent(WorldAttributes.class);
		super.engineState.registerComponent(MovementDirection.class);
		super.engineState.registerComponent(FacingDirection.class);
		super.engineState.registerComponent(AttackCycle.class);
		super.engineState.registerComponent(Movement.class);
		super.engineState.registerComponent(PCollisionBody.class);
		super.engineState.registerComponent(Lifespan.class);
		super.engineState.registerComponent(HitPoints.class);
	}
	public void registerEntitySets()
	{
		super.engineState.registerSet(PlayerSet.class);
		super.engineState.registerSet(MobSet.class);
		super.engineState.registerSet(ConstructSet.class);
		super.engineState.registerSet(Bullet.class);
		super.engineState.registerSet(TurretSet.class);
		super.engineState.registerSet(CollectibleSet.class);
	}

	// higher game logic functions
	public void spawnWorld()

	{
		// Player
		this.player = super.engineState.spawnEntitySet(new PlayerSet());
		for (int i = 0; i < 1; ++i) {
			super.engineState.spawnEntitySet(new MobSet());
		}

		EngineTransforms.addPlayerDiffusionValAtPlayerPos(
			this.engineState, this.map, 0, this.player);
		// TODO: HAIYANG get the layer number for the path finding!
		// right now for testing it only have 1 layer

		clearTime();

		// start the path finding thread
		generateDiffusionMap.start();
	}
	public void clearWorld()
	{
	}

	// use super.acct for the accumulated time, use this.dt for the time
	// step. Time is all in milliseconds
	public void runGame()
	{
		try {
			generateDiffusionMap.setStart();
		} catch (Exception ex) {
			Logger.logMessage(
				"an exception has occured in path finding generation thread "
				+ ex);
		}
		this.processInputs();

		// ASE
		this.mobSpawner();

		// Timed despawners
		this.cashDropDespawner();
		this.bulletDespawner();

		// detect bullet hits
		// TODO: use actual collision detection for this
		// TODO: currently just despawns bullet, does not do damage
		for (int i = this.engineState.getInitialSetIndex(Bullet.class);
		     poj.EngineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(Bullet.class, i)) {

			this.findBulletHits(i);
		}

		// TODO: make mobs drop cash on death?
		this.cashSpawner(true, 13f, 7f);
		this.collectCash(GameConfig.PICKUP_CASH_AMOUNT);

		this.updateGameTimer();
		this.updateCashDisplay();

		// /ASE

		// updating positions
		EngineTransforms.setMovementVelocityFromMovementDirection(
			this.engineState);

		EngineTransforms.updatePCollisionFromWorldAttr(
			this.engineState);


		// debug renderers
		EngineTransforms.debugRenderPolygons(
			this.engineState, this.debugBuffer, this.cam);

		// testing collision with turrets
		for (int i = 0; i < this.map.getNumberOfLayers(); ++i) {
			EngineTransforms.debugRenderPolygons(
				this.map.getLayerEngineState(i), debugBuffer,
				this.cam);

			EngineTransforms.resolvePCollisionBodiesAgainstTileMap(
				this.engineState, this.gjk, PlayerSet.class,
				this.map.getLayerEngineState(i), this.dt);
		}

		//  attack cycles
		EngineTransforms.runAttackCycleHandlersAndFreezeMovement(
			this.engineState, this.player, this.curWeaponState,
			super.inputPoller, this.invCam, this.getPlayTime());

		// changing world attrib position
		EngineTransforms.updateWorldAttribPositionFromMovement(
			this.engineState, this.dt);

		// EngineTransforms.generateDiffusionMap(this.map, 0, 1f / 8f);
		for (int i = this.engineState.getInitialSetIndex(MobSet.class);
		     poj.EngineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(MobSet.class, i)) {

			EngineTransforms.updateEnemyPositionFromPlayer(
				this.engineState, this.map, 0, this.player, i,
				gjk);

			// TODO: detect mob with turret but MOB CANNOT BE MOVED
			// after collision
			/*
			for (int j = engineState.getInitialSetIndex(
				     TurretSet.class);
			     engineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(TurretSet.class,
							     j)) {
				EngineTransforms.checkTurretCollisionWithMob(
					this.engineState, j, i, this.gjk);
			}
			*/
		}

		// updating the camera
		centerCamerasPositionToPlayer();
		updateInverseCamera();
		updateCoolDownKeys();

		EngineTransforms.updateAnimationWindows(this.engineState,
							this.dt);
		EngineTransforms.updateTriggeredAttackCycles(this.engineState,
							     this.dt);
		EngineTransforms.cropSpriteSheetsFromAnimationWindows(
			this.engineState);

		EngineTransforms
			.updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera(
				this.engineState, this.cam);

		// System.out.println("----------------------- end one loop");
		// rendering is run after this is run
	}

	protected Vector2f getPositionToMouseDelta(Vector2f v)
	{
		Vector2f mousePosition = super.inputPoller.getMousePosition();
		mousePosition.matrixMultiply(this.invCam);
		Vector2f tmp = v.pureSubtract(mousePosition);

		tmp.negate();

		return tmp;
	}

	protected void processInputs()
	{

		////// Combat Commands //////
		if (super.inputPoller.isKeyDown(GameConfig.ATTACK_KEY)
		    || super.inputPoller.isLeftMouseButtonDown()) {
			System.out.print(
				"space key is down. Player character should be attacking\n");

			System.out.println("the cd value of attack key = "
					   + Math.abs(lastCoolDown.get(
						     GameConfig.ATTACK_KEY)));
			if (Math.abs(lastCoolDown.get(GameConfig.ATTACK_KEY))
			    == 0d) {
				updateDtForKey(GameConfig.ATTACK_KEY,
					       -coolDownMax.get(
						       GameConfig.ATTACK_KEY));

				engineState
					.getComponentAt(AttackCycle.class,
							this.player)
					.startAttackCycle();
				return;
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

			if (Math.abs(
				    lastCoolDown.get(GameConfig.SWITCH_WEAPONS))
			    == 0d) {
				updateDtForKey(
					GameConfig.SWITCH_WEAPONS,
					-coolDownMax.get(
						GameConfig.SWITCH_WEAPONS));
				System.out.print(
					"x key is down. Player character should be changing weapons\n");
				System.out.println(
					"old weapon state = "
					+ curWeaponState.currentWeaponState());
				curWeaponState = curWeaponState.next();
				System.out.println(
					"new weapon state = "
					+ curWeaponState.currentWeaponState());
				// lastCoolDown.set(GameConfig.BUILD_TOWER,
				//-coolDownMax.get(
				// GameConfig.BUILD_TOWER));
			}
		}

		boolean hasMovementKeyBeenPressed = true;

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
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_W)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("wa key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.SW);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.SW;
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("sa key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.SE);
			prevDirection = CardinalDirections.SE;
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)
			   && super.inputPoller.isKeyDown(KeyEvent.VK_D)) {

			System.out.println("sd key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.NE);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.NE;

		} else if (super.inputPoller.isKeyDown(
				   KeyEvent.VK_W)) { // single Key movements
			System.out.println("w key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.W);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.W;
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_D)) {
			System.out.println("d key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.N);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.N;
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_A)) {
			System.out.println("a key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.S);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.S;
		} else if (super.inputPoller.isKeyDown(KeyEvent.VK_S)) {
			System.out.println("s key is down");
			super.getComponentAt(MovementDirection.class,
					     this.player)
				.setDirection(CardinalDirections.E);
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(GameConfig.PLAYER_SPEED);
			prevDirection = CardinalDirections.E;
		} else // no movement key is pressed
		{
			hasMovementKeyBeenPressed = false;
			super.getComponentAt(Movement.class, this.player)
				.setSpeed(0);
			// TODO idle direction!!!!!
			super.getComponentAt(FacingDirection.class,
					     this.player);
		}

		if (!this.engineState.getComponentAt(AttackCycle.class, player)
			     .isAttacking()) {
			int flag = hasMovementKeyBeenPressed ? 1 : 0;
			super.getComponentAt(HasAnimation.class, this.player)
				.setAnimation(
					EngineTransforms.findPlayerFacingSprite(
						this.engineState
							.getComponentAt(
								MovementDirection
									.class,
								this.player)
							.getDirection(),
						flag));
		}

		////// Build Commands //////
		if (super.inputPoller.isKeyDown(GameConfig.BUILD_TOWER)) {

			System.out.println("will build turret!!!!");
			System.out.println(
				" last cooldown = "
				+ lastCoolDown.get(GameConfig.BUILD_TOWER));
			if (Math.abs(lastCoolDown.get(GameConfig.BUILD_TOWER))
				    == 0d
			    && this.cash >= GameConfig.TOWER_BUILD_COST) {
				// player position is also the top left of the
				// polygon !
				Vector2f playerPosition =
					super.getComponentAt(
						     WorldAttributes.class,
						     this.player)
						.getOriginCoord();

				int tmp = super.engineState.spawnEntitySet(
					new TurretSet());
				this.cash -= 250;
				super.getComponentAt(WorldAttributes.class, tmp)
					.setOriginCoord(playerPosition);

				System.out.println(
					"Built a tower. It cost $"
					+ GameConfig.TOWER_BUILD_COST);
				// reset the lastCooldown key to the max
				// cooldown of that key
				updateDtForKey(GameConfig.BUILD_TOWER,
					       -coolDownMax.get(
						       GameConfig.BUILD_TOWER));
				// lastCoolDown.set(GameConfig.BUILD_TOWER,
				//-coolDownMax.get(
				// GameConfig.BUILD_TOWER));
			} else if (this.cash < GameConfig.TOWER_BUILD_COST)
				System.out.print(
					"Not enough cash to build a turret\nYou need at least $"
					+ GameConfig.TOWER_BUILD_COST + "\n");
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
	}

	// Render function
	protected void render()
	{

		pushTileMapLayerToQueue(map.getLayerEngineState(0),
					groundBuffer);

		pushTileMapLayerToQueue(map.getLayerEngineState(1),
					entityBuffer);

		for (Render r :
		     super.getRawComponentArrayListPackedData(Render.class)) {
			Systems.cullPushRenderComponentToQueue(
				r, entityBuffer, this.windowWidth,
				this.windowHeight);
		}

		guiBuffer.add(this.gameTimer);
		guiBuffer.add(this.cashDisplay);

		super.renderer.renderBuffers(groundBuffer, entityBuffer,
					     debugBuffer, guiBuffer);
	}

	protected void pushTileMapLayerToQueue(MapLayer n,
					       Queue<RenderObject> q)
	{
		EngineTransforms.pushTileMapLayerToQueue(
			this.map, n, windowWidth, this.windowHeight,
			(int)GameResources.TILE_SCREEN_WIDTH,
			(int)GameResources.TILE_SCREEN_HEIGHT, this.cam,
			this.invCam, q);
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
		// because i don't want to subtract it to neg infinity..
		if (lastCoolDown.get(keyIndex) - val > EPSILON) {
			lastCoolDown.set(keyIndex,
					 lastCoolDown.get(keyIndex) - val);
		} else {
			lastCoolDown.set(keyIndex, 0d);
		}
	}

	// ASE
	/** @return: current time the game has been running in seconds */
	private double getPlayTime()
	{
		double playTime = Math.floor((super.acct / 1000) * 100) / 100;
		return playTime;
	}

	/** updates the gameTimer string with the current play time */
	private void updateGameTimer()
	{
		this.gameTimer.setStr("" + getPlayTime());
	}

	/**  updates the cashDisplay string with the players current cash */
	private void updateCashDisplay()
	{
		this.cashDisplay.setStr("Your Cash: $" + this.cash);
	}

	/**
	 * spawns a new mob entity if it has been at least
	 * MOB_SPAWN_TIMER seconds since the last spawn
	 */
	private void mobSpawner()
	{
		double currentPlayTime = this.getPlayTime();
		if (currentPlayTime - this.timeOfLastMobSpawn
		    > GameConfig.MOB_SPAWN_TIMER) {
			super.engineState.spawnEntitySet(new MobSet());
			super.engineState.spawnEntitySet(
				new MobSet(GameConfig.MOB_SPAWNER_1));
			this.timeOfLastMobSpawn = currentPlayTime;
			System.out.println("Spawning new mob at time: "
					   + this.timeOfLastMobSpawn);
		}
	}

	/**
	 * spawns a new cash pick-up at a location
	 * @param timed: only spawn if PICKUP_CASH_SPAWN_TIME seconds have
	 *         passed since last spawn
	 * @param x: x-coordinate to spawn the drop at
	 * @param y: y-coordinate to spawn the drop at
	 *   */
	private void cashSpawner(boolean timed, float x, float y)
	{
		double currentPlayTime = this.getPlayTime();
		if (timed
		    && currentPlayTime - this.timeOfLastCashSpawn
			       > GameConfig.PICKUP_CASH_SPAWN_TIME) {
			super.engineState.spawnEntitySet(
				new CollectibleSet(x, y, currentPlayTime));
			this.timeOfLastCashSpawn = currentPlayTime;
			System.out.println("Spawning new timed cash drop.");
		} else if (timed == false) {
			super.engineState.spawnEntitySet(
				new CollectibleSet(x, y, currentPlayTime));
			this.timeOfLastCashSpawn = currentPlayTime;
			System.out.println("Spawning new cash drop.");
		}
	}

	/**
	 * deletes cash drops older than the lifespan
	 * prevents drops that have not been collected from piling up
	 */
	private void cashDropDespawner()
	{
		for (int i = this.engineState.getInitialSetIndex(
			     CollectibleSet.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(CollectibleSet.class,
							  i)) {

			double spawnTime =
				engineState.getComponentAt(Lifespan.class, i)
					.getSpawnTime();
			double lifespan =
				engineState.getComponentAt(Lifespan.class, i)
					.getLifespan();

			if (this.getPlayTime() - spawnTime >= lifespan) {
				CombatFunctions.removePickUp(engineState, i);
			}
		}
	}

	/**
	 * Removes bullets that have been alive longer than their lifespan
	 * Makes bullets have limited range
	 */
	private void bulletDespawner()
	{
		for (int i = this.engineState.getInitialSetIndex(Bullet.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(Bullet.class, i)) {

			double spawnTime =
				engineState.getComponentAt(Lifespan.class, i)
					.getSpawnTime();
			double lifespan =
				engineState.getComponentAt(Lifespan.class, i)
					.getLifespan();

			if (this.getPlayTime() - spawnTime >= lifespan) {
				CombatFunctions.removeBullet(engineState, i);
			}
		}
	}

	/**
	 * Add the money in a cash pick-up to the player
	 * @param amount of money in the pick-up
	 */
	private void collectCash(int amount)
	{
		PCollisionBody playerPosition = engineState.getComponentAt(
			PCollisionBody.class, this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     CollectibleSet.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(CollectibleSet.class,
							  i)) {

			PCollisionBody collectiblePosition =
				engineState.getComponentAt(PCollisionBody.class,
							   i);

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition, collectiblePosition)) {
				this.cash += amount;
				System.out.println("Picked up $" + amount
						   + ". You now have $"
						   + this.cash);
				CombatFunctions.removePickUp(engineState, i);
			}
		}
	}
	/**
	 * checks a bullet to see if it is in the same place
	 * as a mob, applies damage to hit mob, despawns the
	 * mob if its health is at or below 0, then despawns
	 * the bullet
	 * @param bullet to check for hit
	 */
	private void findBulletHits(int bullet)
	{
		// TODO: delete bullets that collide with a wall

		final PCollisionBody bulletPosition =
			engineState.getComponentAt(PCollisionBody.class,
						   bullet);
		// check against all mobs
		for (int i = this.engineState.getInitialSetIndex(MobSet.class);
		     poj.EngineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(MobSet.class, i)) {

			final PCollisionBody anotherMob =
				engineState.getComponentAt(PCollisionBody.class,
							   i);
			// check if bullet and mob are at same position
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletPosition, anotherMob)) {
				System.out.println("A bullet hit a mob!");
				engineState.getComponentAt(HitPoints.class, i)
					.hurt(GameConfig.BULLET_DAMAGE);

				// kill mob if its health is at or below 0
				if (engineState
					    .getComponentAt(HitPoints.class, i)
					    .getHP()
				    <= 0) {
					CombatFunctions.removeMob(engineState,
								  i);
				}
				// remove bullet
				CombatFunctions.removeBullet(engineState,
							     bullet);

				break;
			}
		}
		for (PCollisionBody b :
		     map.getLayerEngineState(1)
			     .getRawComponentArrayListPackedData(
				     PCollisionBody.class)) {
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletPosition, b)) {

				bulletPosition.print();
				b.print();
				CombatFunctions.removeBullet(engineState,
							     bullet);
				break;
			}
		}
	}
	// /ASE
}
