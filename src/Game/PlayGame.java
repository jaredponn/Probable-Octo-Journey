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
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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
import Game.GameEvents.PlayGameEvent;
import Game.GameEvents.PlayGameEventStack;
import PathFinding.MapGeneration;
import Resources.GameConfig;
import Resources.GameResources;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Render.*;
import poj.GameWindow.*;
import poj.Collisions.GJK;
import poj.Collisions.QuadTree;
import poj.Collisions.Rectangle;
import poj.Logger.Logger;
import poj.Render.MinYFirstSortedRenderObjectBuffer;
import poj.Time.*;
import poj.Render.RenderObject;
import poj.Render.StringRenderObject;
import poj.linear.Vector2f;
import poj.EngineState;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;

public class PlayGame extends World
{
	// Render
	protected Map map;

	protected RenderThread renderThread;
	// buffers for the renderer
	private static int DEFAULT_RENDER_BUF_SIZE = 10000;
	protected PlayGameRenderBuffers writeToRenderBuffer;

	// references to the render buffers in the thread. Mainly here for
	// legacy reasons
	protected ArrayList<RenderObject> groundBuffer;
	protected ArrayList<RenderObject> entityBuffer;
	protected ArrayList<RenderObject> buildingBuffer;
	protected ArrayList<RenderObject> poleBuffer;
	protected ArrayList<RenderObject> guiBuffer;
	public ArrayList<RenderObject> debugBuffer;


	// Camera
	protected Camera cam;    // camera
	protected Camera invCam; // inverse camera

	// game event stack
	PlayGameEventStack gameEventStack;

	// Higher level game logic
	protected int player;
	protected static double EPSILON = 0.0001d;
	protected WeaponState curWeaponState = WeaponState.Gun;

	// references that are now deprecated
	protected Ammo playerAmmo;
	protected Money playerMoney;
	protected DamageBonus playerDamageBonus;
	protected KillCount killCount;

	protected int mobsSpawned = 0;
	protected double lastWaveDefeatedAt = 0.0;

	protected double timeOfLastMobSpawn = 0.0;
	protected double timeOfLastCashSpawn =
		0.0 - GameConfig.PICKUP_CASH_SPAWN_TIME;
	protected double timeOfLastPowerUpSpawn =
		0.0 - GameConfig.PICKUP_POWERUP_SPAWN_TIME;

	// Collision detection and resolution
	protected GJK gjk;
	protected QuadTree tileMapQuadTree;

	// path finding thread
	protected MapGeneration generateDiffusionMap;

	public PlayGame(int width, int height, Renderer renderer,
			InputPoller inputPoller)
		throws UnsupportedAudioFileException, IOException,
		       LineUnavailableException
	{

		super(width, height, renderer, inputPoller);


		gjk = new GJK();
		gjk.clearVerticies();

		this.gameEventStack = new PlayGameEventStack();

		// World loading
		this.map = new Map(6);

		this.map.addTileSet(GameResources.NoTreeofficialTileSetConfig);
		this.map.addMapConfig(GameResources.NoTreeofficialMapConfig);
		this.map.addMapLayer(GameResources.NoTreeofficialMapGround1);
		this.map.addMapLayer(GameResources.NoTreeofficialMapMisc2);
		this.map.addMapLayer(
			GameResources.NoTreeofficialMapCarsAndBuildings3);
		this.map.addMapLayer(
			GameResources.NoTreeofficialMapTreesAndRocks4);
		this.map.addMapLayer(
			GameResources.NoTreeofficialMapLightsAndSigns5);
		this.map.addMapLayer(
			GameResources.NoTreeofficialMapEmptyBlockForBuildings);
		/*
		this.map.addTileSet(GameResources.officialTileSetConfig);
		this.map.addMapConfig(GameResources.officialMapConfig);
		this.map.addMapLayer(GameResources.officialMapGround1);
		this.map.addMapLayer(GameResources.officialMapMisc2);
		this.map.addMapLayer(
			GameResources.officialMapCarsAndBuildings3);
		this.map.addMapLayer(GameResources.officialMapTreesAndRocks4);
		this.map.addMapLayer(GameResources.officialMapLightsAndSigns5);
		*/


		// deep copies the coolDown keys
		for (int i = 0; i < GameConfig.COOL_DOWN_KEYS.size(); ++i) {
			coolDownMax.set(GameConfig.COOL_DOWN_KEYS.get(i).fst,
					GameConfig.COOL_DOWN_KEYS.get(i).snd);
		}

		this.cam = new Camera();

		// rendering
		this.renderThread = new RenderThread(super.renderer);
		this.updateRenderWriteToBufferToUnfocusedBuffer();

		// camera initialization
		this.resetCamera();

		this.invCam = new Camera();
		this.updateInverseCamera();

		// initialize the path finding thread
		this.generateDiffusionMap = new MapGeneration(
			this.map, this.engineState, 0, 1f / 8f);


		// loading the quad tree
		this.tileMapQuadTree =
			TileMapCollisionAlgorithms.generateQuadTreeFromMap(
				this.map);
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
		super.engineState.registerComponent(
			AnimationWindowAssets.class);
		super.engineState.registerComponent(Movement.class);
		super.engineState.registerComponent(
			PhysicsPCollisionBody.class);
		super.engineState.registerComponent(PHitBox.class);
		super.engineState.registerComponent(Lifespan.class);
		super.engineState.registerComponent(HitPoints.class);
		super.engineState.registerComponent(Damage.class);
		super.engineState.registerComponent(AggroRange.class);
		super.engineState.registerComponent(Ammo.class);
		super.engineState.registerComponent(SoundEffectAssets.class);
		super.engineState.registerComponent(Money.class);
		super.engineState.registerComponent(DamageBonus.class);
		super.engineState.registerComponent(KillCount.class);
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


		EngineTransforms.addPlayerDiffusionValAtPlayerPos(
			this.engineState, this.map, 0, this.player);

		clearTime();

		// start the path finding thread
		this.generateDiffusionMap.setStart();

		// starts the render thread
		this.renderThread.startThread();

		EngineTransforms.updatePCollisionBodiesFromWorldAttr(
			this.engineState);

		this.playerAmmo =
			engineState.unsafeGetComponentAt(Ammo.class, player);
		this.playerMoney =
			engineState.unsafeGetComponentAt(Money.class, player);
		this.playerDamageBonus = engineState.unsafeGetComponentAt(
			DamageBonus.class, player);
		this.killCount = engineState.unsafeGetComponentAt(
			KillCount.class, player);

		runGame();
	}

	public void clearWorld()
	{

		try {
			this.generateDiffusionMap.setEnd();
			this.generateDiffusionMap.join();
			this.renderThread.endThread();
			this.renderThread.join();
		} catch (Exception e) {
			System.out.println(
				"what the heck was that error message -- error in clear world with threads. Probably will still be okay ");
			this.renderThread.endThread();
			this.generateDiffusionMap.setEnd();
		}
	}

	// use super.acct for the accumulated time, use this.dt for the time
	// step. Time is all in milliseconds
	public void runGame()
	{
		this.mobSpawner();


		this.processInputs();

		// ASE

		EngineTransforms.updatePCollisionBodiesFromWorldAttr(
			this.engineState);
		// this.handleTurrets();

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

		// attack cycles
		EntityCollisionAlgorithms
			.startAttackCycleIfAggroRadiusCollidesPhysicsPCollisionBody(
				this, TurretSet.class, MobSet.class);
		EntityCollisionAlgorithms
			.startAttackCycleIfAggroRadiusCollidesPhysicsPCollisionBody(
				this, MobSet.class, PlayerSet.class);
		EntityCollisionAlgorithms
			.startAttackCycleIfAggroRadiusCollidesPhysicsPCollisionBody(
				this, MobSet.class, TurretSet.class);

		EngineTransforms.pushOutOfHPEventsIfHPIsZeroOrLess(this);

		this.collectCash(GameConfig.PICKUP_CASH_AMOUNT);
		this.collectPowerUp();
		this.collectHealthPack();
		this.collectAmmoPack();


		// updating positions
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


		// resolving entity collision
		EntityCollisionAlgorithms
			.nudgeSetAAndBIfPCollisionBodiesAreTouching(
				this, MobSet.class, MobSet.class);

		EntityCollisionAlgorithms
			.nudgeSetAAndBIfPCollisionBodiesAreTouching(
				this, PlayerSet.class, MobSet.class);

		// Resolving  collisions against tilemap
		TileMapCollisionAlgorithms
			.nudgePhysicsPCollisionBodiesOutsideTileMapPhysicsPCollisionBody(
				this, MobSet.class);
		TileMapCollisionAlgorithms
			.nudgePhysicsPCollisionBodiesOutsideTileMapPhysicsPCollisionBody(
				this, PlayerSet.class);

		for (int i = 0; i < this.map.getNumberOfLayers(); ++i) {
			EngineTransforms.debugRenderPhysicsPCollisionBodies(
				this.map.getLayerEngineState(i), debugBuffer,
				this.cam, Color.RED);
		}


		//  attack cycles
		AttackCycleHandlers.runAttackCyclers(this);


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


		EngineTransforms.setMovementVelocityFromMovementDirectionForSet(
			this.engineState, PlayerSet.class);
		EngineTransforms
			.steerMovementVelocityFromMovementDirectionForSet(
				this.engineState, MobSet.class, 1 / 1f);
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
		PlayGameRender.renderPlayGame(this);
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
		this.cam.setScalingForVector2(
			-GameResources.TILE_SCREEN_WIDTH
				* GameResources.MAGIC_CONSTANT,
			GameResources.TILE_SCREEN_HEIGHT
				* GameResources.MAGIC_CONSTANT);
		this.cam.composeWithRotationForVector2XaxisCC(
			GameResources.TILE_SCREEN_ROTATION);
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
			engineState.unsafeGetComponentAt(WorldAttributes.class,
							 this.player));
	}

	// ASE
	/** @return: current time the game has been running in seconds */
	public double getPlayTime()
	{
		double playTime = Math.floor((super.acct / 1000) * 100) / 100;
		return playTime;
	}


	/**
	 * spawns a new mob entity if it has been at least
	 * MOB_SPAWN_TIMER seconds since the last spawn
	 */
	protected void mobSpawner()
	{
		double currentPlayTime = this.getPlayTime();
		if (currentPlayTime - this.timeOfLastMobSpawn
			    >= GameConfig.MOB_SPAWN_TIMER
		    || killCount.get() >= mobsSpawned) {
			this.timeOfLastMobSpawn = currentPlayTime;
			System.out.println("New zombies arrived at T+"
					   + currentPlayTime + " seconds!");
			for (int i = 0; i < GameConfig.MOB_SPAWN_POINTS.size();
			     i++) {
				engineState.spawnEntitySet(new MobSet(
					GameConfig.MOB_SPAWN_POINTS.get(i)));
				mobsSpawned++;
			}
			// play zombie spawn sound with first set index of the
			// mob (UNLESS WE WANT MULTIPLE SPAWN SOUNDS TO BE
			// PLAYED AT THE SAME TIME, we change this..)
			engineState
				.unsafeGetComponentAt(
					SoundEffectAssets.class,
					engineState.getInitialSetIndex(
						MobSet.class))
				.playSoundEffectAt(
					ThreadLocalRandom.current().nextInt(0,
									    3));
		}
		// TODO: make more mobs spawn over time
	}

	/**
	 * spawns a new cash pick-up at a location
	 * @param timed: only spawn if PICKUP_CASH_SPAWN_TIME seconds have
	 *         passed since last spawn
	 * @param x: x-coordinate to spawn the drop at
	 * @param y: y-coordinate to spawn the drop at
	 *   */
	public void cashSpawner(boolean timed, float x, float y)
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
	public void powerUpSpawner(boolean timed, float x, float y)
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

	public void ammoPackSpawner(boolean timed, float x, float y)
	{
		double currentPlayTime = this.getPlayTime();
		super.engineState.spawnEntitySet(
			new AmmoPack(x, y, currentPlayTime));
	}

	public void healthPackSpawner(boolean timed, float x, float y)
	{
		double currentPlayTime = this.getPlayTime();
		super.engineState.spawnEntitySet(
			new HealthPack(x, y, currentPlayTime));
	}

	/**
	 * deletes drops older than their lifespan
	 * prevents drops that have not been collected from piling up
	 */
	protected void timedDespawner()
	{
		// money
		for (int i = this.engineState.getInitialSetIndex(
			     CollectibleSet.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(CollectibleSet.class,
							  i)) {
			engineState.unsafeGetComponentAt(Lifespan.class, i)
				.checkLifeSpan(this, i);
		}

		// power-ups
		for (int i = this.engineState.getInitialSetIndex(PowerUp.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(PowerUp.class, i)) {

			engineState.unsafeGetComponentAt(Lifespan.class, i)
				.checkLifeSpan(this, i);
		}

		// health packs
		for (int i = this.engineState.getInitialSetIndex(
			     HealthPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(HealthPack.class,
							  i)) {

			engineState.unsafeGetComponentAt(Lifespan.class, i)
				.checkLifeSpan(this, i);
		}

		// ammo packs
		for (int i = this.engineState.getInitialSetIndex(
			     AmmoPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(AmmoPack.class, i)) {

			engineState.unsafeGetComponentAt(Lifespan.class, i)
				.checkLifeSpan(this, i);
		}
	}

	/**
	 * Add the money in a cash pick-up to the player
	 * @param amount of money in the pick-up
	 */
	protected void collectCash(int amount)
	{
		PhysicsPCollisionBody playerPosition =
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     CollectibleSet.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(CollectibleSet.class,
							  i)) {

			Optional<PhysicsPCollisionBody> collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			if (!collectiblePosition.isPresent())
				continue;

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition,
				    collectiblePosition.get())) {
				playerMoney.increase(amount);
				System.out.println("Picked up $" + amount
						   + ". You now have $"
						   + playerMoney.get());
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
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, this.player);

		for (int i = this.engineState.getInitialSetIndex(PowerUp.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(PowerUp.class, i)) {

			Optional<PhysicsPCollisionBody> collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);
			if (!collectiblePosition.isPresent()) {
				continue;
			}

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition,
				    collectiblePosition.get())) {
				playerDamageBonus.increase(
					GameConfig.PICKUP_POWERUP_AMOUNT);
				System.out.println(
					"The player now has an attack bonus of "
					+ this.playerDamageBonus.get());
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
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     HealthPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(HealthPack.class,
							  i)) {

			Optional<PhysicsPCollisionBody> collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);
			if (!collectiblePosition.isPresent()) {
				continue;
			}

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition,
				    collectiblePosition.get())) {
				engineState
					.unsafeGetComponentAt(HitPoints.class,
							      player)
					.heal(GameConfig
						      .PICKUP_HEALTHPACK_AMOUNT);
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
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, this.player);

		for (int i = this.engineState.getInitialSetIndex(
			     AmmoPack.class);
		     this.engineState.isValidEntity(i);
		     i = this.engineState.getNextSetIndex(AmmoPack.class, i)) {

			Optional<PhysicsPCollisionBody> collectiblePosition =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			if (!collectiblePosition.isPresent())
				continue;

			if (Systems.arePCollisionBodiesColliding(
				    gjk, playerPosition,
				    collectiblePosition.get())) {
				this.playerAmmo.increaseAmmo(
					GameConfig.PICKUP_AMMOPACK_AMOUNT);
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
			engineState.unsafeGetComponentAt(AttackCycle.class, i)
				.startAttackCycle();
		}
	}

	protected void updateRenderWriteToBufferToUnfocusedBuffer()
	{
		this.writeToRenderBuffer =
			this.renderThread.getUnfocusedBuffer();

		this.groundBuffer = this.writeToRenderBuffer.getBuf(
			PlayGameRenderBuffers.groundBuf);
		this.entityBuffer = this.writeToRenderBuffer.getBuf(
			PlayGameRenderBuffers.entityBuf);
		this.buildingBuffer = this.writeToRenderBuffer.getBuf(
			PlayGameRenderBuffers.buildingBuf);
		this.poleBuffer = this.writeToRenderBuffer.getBuf(
			PlayGameRenderBuffers.poleBuf);
		this.debugBuffer = this.writeToRenderBuffer.getBuf(
			PlayGameRenderBuffers.debugBuf);
		this.guiBuffer = this.writeToRenderBuffer.getBuf(
			PlayGameRenderBuffers.guiBuf);
	}

	protected Map getMap()
	{
		return this.map;
	}

	protected GJK getGJK()
	{
		return this.gjk;
	}

	protected Camera getInvCam()
	{
		return this.invCam;
	}

	protected Camera getCam()
	{
		return this.cam;
	}

	protected void pushEventToEventHandler(PlayGameEvent event)
	{
		this.gameEventStack.push(event);
	}

	public int getKillCount()
	{
		return killCount.get();
	}

	public int getMobsSpawned()
	{
		return mobsSpawned;
	}

	public void setLastWaveDefeatedAt(double t)
	{
		lastWaveDefeatedAt = t;
	}

	public QuadTree getTileMapCollisionQuadTree()
	{
		return this.tileMapQuadTree;
	}
}
