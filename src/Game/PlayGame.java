package Game;

/**
 * PlayGame -- main class that plays the game (input, render, engine transforms,
 * etc) - giant conglomeration of all the state and the seat and tears and blood
 * of our team put togtether to put together this project.
 *
 * Date: March 12, 2019
 * 2019
 * @author Jared Pon, Haiyang He, Romirio Piqer, Alex Stark
 * @version 1.0
 */

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;

import Components.*;
import EntitySets.AmmoPack;
import EntitySets.Bullet;
import EntitySets.CannonShell;
import EntitySets.CollectibleSet;
import EntitySets.PowerUp;
import EntitySets.ConstructSet;
import EntitySets.HealthPack;
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
import poj.EngineState;

public class PlayGame extends World
{
	// Render
	protected Map map;
	// buffers for the renderer
	protected Queue<RenderObject> groundBuffer;
	protected MinYFirstSortedRenderObjectBuffer entityBuffer;
	protected Queue<RenderObject> guiBuffer;
	public static Queue<RenderObject> debugBuffer =
		new LinkedList<RenderObject>(); // all debugging should be
						// global (fight me) if java had
						// Monads I would be happy


	// Camera
	protected Camera cam;    // camera
	protected Camera invCam; // inverse camera

	// game event stack
	PlayGameEventStack gameEventStack;

	// Cooldown for keys
	protected static ArrayList<Double> coolDownMax = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));
	protected ArrayList<Double> lastCoolDown = new ArrayList<Double>(
		Collections.nCopies(poj.GameWindow.InputPoller.MAX_KEY, 0d));

	// Higher level game logic
	protected int player;
	protected static double EPSILON = 0.0001d;
	protected WeaponState curWeaponState = WeaponState.Gun;

	protected double playerDamageBonus = 1d;
	protected int playerAmmo = GameConfig.PLAYER_STARTING_AMMO;
	protected int cash = GameConfig.PLAYER_STARTING_CASH;
	
	protected double timeOfLastMobSpawn = 0.0 - GameConfig.MOB_SPAWN_TIMER;
	protected double timeOfLastCashSpawn =
		0.0 - GameConfig.PICKUP_CASH_SPAWN_TIME;
	protected double timeOfLastPowerUpSpawn =
			0.0 - GameConfig.PICKUP_POWERUP_SPAWN_TIME;

	protected StringRenderObject gameTimer =
		new StringRenderObject("", 5, 10, Color.WHITE);
	protected StringRenderObject cashDisplay = new StringRenderObject(
		"Your Cash: " + this.cash, 5, 20, Color.WHITE);
	protected StringRenderObject healthDisplay =
		new StringRenderObject("", 5, 30, Color.WHITE);
	protected StringRenderObject ammoDisplay =
			new StringRenderObject("", 5, 40, Color.WHITE);


	// Collision detection and resolution
	protected GJK gjk;

	protected MapGeneration generateDiffusionMap;
	public PlayGame()
	{
		super();

		gjk = new GJK();
		gjk.clearVerticies();

		this.gameEventStack = new PlayGameEventStack();

		// World loading
		this.map = new Map(6);
		this.map.addTileSet(GameResources.officialTileSetConfig);
		this.map.addMapConfig(GameResources.officialMapConfig);
		this.map.addMapLayer(GameResources.officialMapGround1);
		this.map.addMapLayer(GameResources.officialMapMisc2);
		this.map.addMapLayer(GameResources.officialMapEverythingElse3);
		this.map.addMapLayer(GameResources.officialMapCars4);
		this.map.addMapLayer(GameResources.officialMapRocks5);
		this.map.addMapLayer(GameResources.officialMapTrees6);
		// this.map.addMapLayer(GameResources.demo1LayerWall);
		/*
		// World loading
		this.map = new Map(3);
		this.map.addTileSet(GameResources.tileSet);
		this.map.addMapConfig(GameResources.demo1Config);
		this.map.addMapLayer(GameResources.demo1LayerGround);
		this.map.addMapLayer(GameResources.demo1LayerWall);
		// this.map.addMapConfig(GameResources.pathFindTest3Config);
		// this.map.addMapLayer(GameResources.pathFindTest1Layer);
		// */


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
		super.engineState.registerComponent(DespawnTimer.class);
		super.engineState.registerComponent(FacingDirection.class);
		super.engineState.registerComponent(AttackCycle.class);
		super.engineState.registerComponent(Movement.class);
		super.engineState.registerComponent(
			PhysicsPCollisionBody.class);
		super.engineState.registerComponent(PHitBox.class);
		super.engineState.registerComponent(Lifespan.class);
		super.engineState.registerComponent(HitPoints.class);
		super.engineState.registerComponent(Damage.class);
		super.engineState.registerComponent(AggroRange.class);
	}
	public void registerEntitySets()
	{
		super.engineState.registerSet(PlayerSet.class);
		super.engineState.registerSet(MobSet.class);
		super.engineState.registerSet(ConstructSet.class);
		super.engineState.registerSet(Bullet.class);
		super.engineState.registerSet(CannonShell.class);
		super.engineState.registerSet(TurretSet.class);
		super.engineState.registerSet(CollectibleSet.class);
		super.engineState.registerSet(PowerUp.class);
		super.engineState.registerSet(HealthPack.class);
		super.engineState.registerSet(AmmoPack.class);
	}

	// higher game logic functions
	public void spawnWorld()

	{
		// Player
		this.player = super.engineState.spawnEntitySet(new PlayerSet());
		mobSpawner();

		EngineTransforms.addPlayerDiffusionValAtPlayerPos(
			this.engineState, this.map, 0, this.player);
		// TODO: HAIYANG get the layer number for the path finding!
		// right now for testing it only have 1 layer

		clearTime();

		// start the path finding thread
		generateDiffusionMap.start();

		EngineTransforms.updatePCollisionBodiesFromWorldAttr(
			this.engineState);
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
		EngineTransforms.updatePCollisionBodiesFromWorldAttr(
				this.engineState);
		this.handleTurrets();

		// de-spawn entities with lifespans
		this.timedDespawner();

		// Handle bullets hitting things
		// player bullets:
		for (int i = this.engineState.getInitialSetIndex(Bullet.class);
		     poj.EngineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(Bullet.class, i)) {

			this.findBulletHits(i);
		}
		// turret bullets:
		for (int i = this.engineState.getInitialSetIndex(
			     CannonShell.class);
		     poj.EngineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(CannonShell.class,
							  i)) {

			this.findBulletHits(i);
		}

		// mobs touching players
		CombatFunctions
			.startAttackCycleOfSetAIfPhysicsCollisionBodiesAreCollidingWithSetB(
				engineState, gjk, MobSet.class,
				PlayerSet.class);
		CombatFunctions
			.startAttackCycleOfSetAIfPhysicsCollisionBodiesAreCollidingWithSetB(
				engineState, gjk, MobSet.class,
				TurretSet.class);

		this.cashSpawner(true, 13f, 7f);
		this.powerUpSpawner(true, 13f, 8f);
		this.collectCash(GameConfig.PICKUP_CASH_AMOUNT);
		this.collectPowerUp();

		this.updateGameTimer();
		this.updateCashDisplay();
		this.updateHealthDisplay();
		this.updateAmmoDisplay();


		// updating positions
		EngineTransforms.setMovementVelocityFromMovementDirectionForSet(
			this.engineState, PlayerSet.class);
		EngineTransforms
			.steerMovementVelocityFromMovementDirectionForSet(
				this.engineState, MobSet.class, 1 / 16f);
		EngineTransforms.updatePCollisionBodiesFromWorldAttr(
			this.engineState);


		// debug renderers
		EngineTransforms.debugRenderPhysicsPCollisionBodies(
			this.engineState, this.debugBuffer, this.cam,
			Color.red);

		EngineTransforms.debugRenderPHitBox(this.engineState,
						    this.debugBuffer, this.cam);

		EngineTransforms.debugRenderAggro(this.engineState,
						  this.debugBuffer, this.cam);

		// Resolving  collisions against tilemap

		for (int i = 0; i < this.map.getNumberOfLayers(); ++i) {
			EngineTransforms
				.nudgePhysicsPCollisionBodiesOutsideTileMap(
					this.engineState, this.gjk,
					PlayerSet.class,
					this.map.getLayerEngineState(i),
					this.dt);

			EngineTransforms
				.nudgePhysicsPCollisionBodiesOutsideTileMap(
					this.engineState, this.gjk,
					MobSet.class,
					this.map.getLayerEngineState(i),
					this.dt);

			EngineTransforms.debugRenderPhysicsPCollisionBodies(
				this.map.getLayerEngineState(i), debugBuffer,
				this.cam, Color.RED);
		}

		//  attack cycles
		AttackCycleHandlers.runAttackCycleHandlersAndFreezeMovement(
			this);

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
		PlayGameProcessInputs.updateCoolDownKeys(this);

		EngineTransforms.updateAnimationWindows(this.engineState,
							this.dt);
		EngineTransforms.updateTriggeredAttackCycles(this.engineState,
							     this.dt);
		EngineTransforms.cropSpriteSheetsFromAnimationWindows(
			this.engineState);

		EngineTransforms
			.deleteAllComponentsAtIfDespawnTimerIsFinishedAndUpdateDespawnTimerTime(
				this.engineState, this.dt);

		EngineTransforms
			.updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera(
				this.engineState, this.cam);

		gameEventStack.runGameEventStack();
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
		PlayGameProcessInputs.playGameProcessInputs(this);
	}

	// Render function
	protected void render()
	{

		// TODO: refactor this later..
		pushTileMapLayerToQueue(map.getLayerEngineState(0),
					groundBuffer);

		pushTileMapLayerToQueue(map.getLayerEngineState(1),
					entityBuffer);
		pushTileMapLayerToQueue(map.getLayerEngineState(2), guiBuffer);
		pushTileMapLayerToQueue(map.getLayerEngineState(3), guiBuffer);
		pushTileMapLayerToQueue(map.getLayerEngineState(4), guiBuffer);
		pushTileMapLayerToQueue(map.getLayerEngineState(5), guiBuffer);

		for (Render r :
		     super.getRawComponentArrayListPackedData(Render.class)) {
			Systems.cullPushRenderComponentToQueue(
				r, entityBuffer, this.windowWidth,
				this.windowHeight);
		}

		guiBuffer.add(this.gameTimer);
		guiBuffer.add(this.cashDisplay);
		guiBuffer.add(this.healthDisplay);
		guiBuffer.add(this.ammoDisplay);

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


	protected void updateInverseCamera()
	{
		if (this.cam.isInvertible()) {
			this.invCam =
				new Camera((this.cam.unsafePureInverse()));
		}
	}

	protected void resetCamera()
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

	protected void centerCamerasPositionsToWorldAttribute(WorldAttributes n)
	{
		this.resetCamera();
		Vector2f tmp = n.getOriginCoord();
		tmp.matrixMultiply(this.cam);

		this.cam.setTranslationForVector2(
			-tmp.x + super.windowWidth / 2f,
			-tmp.y + super.windowHeight / 2f);
	}

	protected void centerCamerasPositionToPlayer()
	{
		this.centerCamerasPositionsToWorldAttribute(
			engineState.getComponentAt(WorldAttributes.class,
						   this.player));
	}

	// ASE
	/** @return: current time the game has been running in seconds */
	protected double getPlayTime()
	{
		double playTime = Math.floor((super.acct / 1000) * 100) / 100;
		return playTime;
	}

	/** updates the gameTimer string with the current play time */
	protected void updateGameTimer()
	{
		this.gameTimer.setStr("" + getPlayTime());
	}

	/**  updates the cashDisplay string with the players current cash */
	protected void updateCashDisplay()
	{
		this.cashDisplay.setStr("Your Cash: $" + this.cash);
	}

	/** update healthDisplay */
	protected void updateHealthDisplay()
	{
		this.healthDisplay.setStr(
			"Your HP: "
			+ engineState
				  .getComponentAt(HitPoints.class, this.player)
				  .getHP());
	}
	
	/** update ammoDisplay */
	protected void updateAmmoDisplay()
	{
		this.ammoDisplay.setStr(
			"Your Ammo: "+this.playerAmmo);
	}

	/**
	 * spawns a new mob entity if it has been at least
	 * MOB_SPAWN_TIMER seconds since the last spawn
	 */
	protected void mobSpawner()
	{
		double currentPlayTime = this.getPlayTime();
		if (currentPlayTime - this.timeOfLastMobSpawn
		    >= GameConfig.MOB_SPAWN_TIMER) {
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
	protected void cashSpawner(boolean timed, float x, float y)
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
			System.out.println("Spawning new cash drop.");
		}
	}
	
	// TODO: powerup spawn times
	protected void powerUpSpawner(boolean timed, float x, float y)
	{
		double currentPlayTime = this.getPlayTime();
		if (timed
		    && currentPlayTime - this.timeOfLastPowerUpSpawn
			       > GameConfig.PICKUP_POWERUP_SPAWN_TIME) {
			super.engineState.spawnEntitySet(
				new PowerUp(x, y, currentPlayTime));
			this.timeOfLastPowerUpSpawn = currentPlayTime;
			System.out.println("Spawning new timed power-up drop.");
		} else if (timed == false) {
			super.engineState.spawnEntitySet(
				new PowerUp(x, y, currentPlayTime));
			System.out.println("Spawning new power-up drop.");
		}
	}

	/**
	 * deletes cash drops older than the lifespan
	 * prevents drops that have not been collected from piling up
	 */
	protected void timedDespawner()
	{
		for (int i = this.engineState.getInitialSetIndex(
			     CollectibleSet.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(CollectibleSet.class,
							  i)) {

			CombatFunctions.removeEntityWithLifeSpan(this, i);
		}
		
		for (int i = this.engineState.getInitialSetIndex(
			     PowerUp.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(PowerUp.class,
							  i)) {

			CombatFunctions.removeEntityWithLifeSpan(this, i);
		}
		
		for (int i = this.engineState.getInitialSetIndex(
			     HealthPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(HealthPack.class,
							  i)) {

			CombatFunctions.removeEntityWithLifeSpan(this, i);
		}
		
		for (int i = this.engineState.getInitialSetIndex(
			     AmmoPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(AmmoPack.class,
							  i)) {

			CombatFunctions.removeEntityWithLifeSpan(this, i);
		}
	}

	/**
	 * Removes bullets that have been alive longer than their lifespan
	 * Makes bullets have limited range
	 * Depreciated. Bullets now just removed on collision
	 */
	/*
	protected void bulletDespawner()
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
	*/

	/**
	 * Add the money in a cash pick-up to the player
	 * @param amount of money in the pick-up
	 */
	protected void collectCash(int amount)
	{
		PhysicsPCollisionBody playerPosition =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     CollectibleSet.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(CollectibleSet.class,
							  i)) {

			PhysicsPCollisionBody collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

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
	 * Increase player bullet damage
	 */
	protected void collectPowerUp()
	{
		PhysicsPCollisionBody playerPosition =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     PowerUp.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(PowerUp.class,
							  i)) {

			PhysicsPCollisionBody collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition, collectiblePosition)) {
				this.playerDamageBonus += GameConfig.PICKUP_POWERUP_AMOUNT;
				System.out.println("The player now has an attack bonus of "+this.playerDamageBonus);
				CombatFunctions.removePickUp(engineState, i);
			}
		}
	}
	
	/**
	 * Increase player hit points
	 */
	protected void collectHealthPack()
	{
		PhysicsPCollisionBody playerPosition =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     HealthPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(HealthPack.class,
							  i)) {

			PhysicsPCollisionBody collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition, collectiblePosition)) {
				engineState.getComponentAt(HitPoints.class, player)
					.heal(GameConfig.PICKUP_HEALTHPACK_AMOUNT);
				CombatFunctions.removePickUp(engineState, i);
			}
		}
	}
	
	/**
	 * Increase player ammo
	 */
	protected void collectAmmoPack()
	{
		PhysicsPCollisionBody playerPosition =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     AmmoPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(AmmoPack.class,
							  i)) {

			PhysicsPCollisionBody collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition, collectiblePosition)) {
				this.playerAmmo += GameConfig.PICKUP_AMMOPACK_AMOUNT;
				CombatFunctions.removePickUp(engineState, i);
			}
		}
	}
	
	/**
	 * checks a bullet to see if it has collided with
	 * a mob, applies damage to hit mob, despawns the
	 * mob if its health is at or below 0, and despawns
	 * the bullet
	 * @param bullet to check for hit
	 */
	protected void findBulletHits(int bullet)
	{
		CombatFunctions.bulletHitHandler(this, bullet);
	}

	/**
	 * handles turrets shooting at mobs and mobs attacking turrets
	 * @param mobIndex: the mob attacking a turret
	 */
	protected void handleTurrets()
	{
		for (int i = engineState.getInitialSetIndex(TurretSet.class);
		     poj.EngineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(TurretSet.class, i)) {
			engineState.getComponentAt(AttackCycle.class, i)
				.startAttackCycle();
		}
	}
	// /ASE
	//
	protected Map getMap()
	{
		return this.map;
	}

	protected GJK getGJK()
	{
		return this.gjk;
	}

	protected void pushEventToEventHandler(PlayGameEvent event)
	{
		this.gameEventStack.push(event);
	}
}
