package Game;
import java.awt.Color;

/**
 * Engine Transforms. Mutations to the PlayGame engineState.
 * Date: February 10, 2019
 * @author Jared, Haiyang He, Ramiro Piquer, Alex Stark
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import Components.AggroRange;
import Components.Ammo;
import Components.AnimationWindowAssets;
import Components.AttackCycle;
import Components.CardinalDirections;
import Components.Damage;
import Components.DespawnTimer;
import Components.HasAnimation;
import Components.HitPoints;
import Components.Money;
import Components.Movement;
import Components.MovementDirection;
import Components.PCollisionBody;
import Components.PHitBox;
import Components.PathFindCord;
import Components.PathfindSeek;
import Components.PhysicsPCollisionBody;
import Components.Render;
import Components.SoundEffectAssets;
import Components.WorldAttributes;
import EntitySets.AmmoPack;
import EntitySets.BossSet;
import EntitySets.CashPack;
import EntitySets.HealthPack;
import EntitySets.MobSet;
import EntitySets.PlayerSet;
import EntitySets.PowerUp;
import EntitySets.TurretSet;
import Game.GameEvents.BossDefeatedEvent;
import Game.GameEvents.FocusedPlayGameEvent;
import Game.GameEvents.MobOutOfHPEvent;
import Game.GameEvents.PlayerOutOfHPEvent;
import Game.GameEvents.TurretOutOfHPEvent;
import Resources.GameConfig;
import Resources.GameResources;
import TileMap.Map;
import TileMap.MapLayer;

import poj.EngineState;
import poj.Collisions.GJK;
import poj.Component.Component;
import poj.Component.Components;
import poj.Render.RenderObject;
import poj.Time.Timer;
import poj.linear.Vector2f;

public class EngineTransforms
{


	/**
	 * Updates the animation windows
	 * @param engineState: engine state
	 * @param dt: delta time
	 */
	public static void updateAnimationWindows(EngineState engineState,
						  double dt)
	{
		for (HasAnimation i :
		     engineState.getRawComponentArrayListPackedData(
			     HasAnimation.class)) {
			Systems.updateHasAnimationComponent(i, dt);
		}
	}

	/**
	 * Updates the animation windows
	 * @param engineState: engine state
	 * @param type: type
	 */
	public static <T extends Render> void
	cropSpriteSheetsFromAnimationWindows(EngineState engineState,
					     Class<T> type)
	{

		for (int i = engineState.getInitialComponentIndex(
			     HasAnimation.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(HasAnimation.class,
							   i)) {

			Optional<T> rc = engineState.getComponentAt(type, i);

			if (!rc.isPresent())
				continue;

			Systems.updateRenderComponentWindowFromHasAnimation(
				rc.get(), engineState.unsafeGetComponentAt(
						  HasAnimation.class, i));
		}
	}


	/**
	 * Updates the render coorsd from world coords
	 * @param engineState: engine state
	 * @param type: type
	 * @param cam: camera
	 */
	public static <T extends Render> void
	updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera(
		EngineState engineState, Class<T> type, final Camera cam)
	{
		for (int i = engineState.getInitialComponentIndex(type);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(type, i)) {

			Optional<WorldAttributes> rc =
				engineState.getComponentAt(
					WorldAttributes.class, i);
			if (!rc.isPresent())
				continue;


			Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
				rc.get(),
				engineState.unsafeGetComponentAt(type, i), cam);
		}
	}

	/**
	 * Updates world attr from movement
	 * @param engineState: engine state
	 * @param dt: time
	 */
	public static void
	updateWorldAttribPositionFromMovement(EngineState engineState,
					      double dt)
	{
		for (int i = engineState.getInitialComponentIndex(
			     Movement.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(Movement.class, i)) {

			Optional<WorldAttributes> wc =
				engineState.getComponentAt(
					WorldAttributes.class, i);

			if (!wc.isPresent())
				continue;

			Systems.updateWorldAttribPositionFromMovement(
				wc.get(),
				engineState.unsafeGetComponentAt(Movement.class,
								 i),
				dt);
		}
	}


	/**
	 * sets movement velociy from movement direction for a set
	 * @param engineState: engine state
	 * @param c: type
	 */
	public static void setMovementVelocityFromMovementDirectionForSet(
		EngineState engineState, Class<? extends Component> c)
	{
		for (int i = engineState.getInitialSetIndex(c);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(c, i)) {
			Optional<Movement> mc =
				engineState.getComponentAt(Movement.class, i);
			Optional<MovementDirection> mdc =
				engineState.getComponentAt(
					MovementDirection.class, i);

			if (!mc.isPresent())
				continue;

			if (!mdc.isPresent())
				continue;


			Systems.setMovementVelocityFromMovementDirection(
				mc.get(), mdc.get());
		}
	}

	/**
	 * steers movement velociy from movement direction for a set
	 * @param engineState: engine state
	 * @param c: type
	 * @param steerRatio: steering ratio
	 */
	public static void steerMovementVelocityFromMovementDirectionForSet(
		EngineState engineState, Class<? extends Component> c,
		float steerRatio)
	{
		for (int i = engineState.getInitialSetIndex(c);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(c, i)) {

			Optional<Movement> mc =
				engineState.getComponentAt(Movement.class, i);

			Optional<MovementDirection> mdc =
				engineState.getComponentAt(
					MovementDirection.class, i);

			if (!mc.isPresent())
				continue;

			if (!mdc.isPresent())
				continue;

			Systems.steerMovementVelocityFromMovementDirection(
				mc.get(), mdc.get(), steerRatio);
		}
	}

	/**
	 * path finding -- gets the eight neightbors
	 * @param map: tile map
	 * @param indexOfEcs : index in ECS
	 * @param mapLayer: maplayer
	 */
	public static ArrayList<PathFindCord>
	getEightNeighbourVector(Map map, int indexOfEcs, MapLayer mapLayer)
	{
		ArrayList<Vector2f> neighbours = new ArrayList<Vector2f>();
		ArrayList<PathFindCord> tmp = new ArrayList<PathFindCord>();
		Vector2f centerVector =
			mapLayer.unsafeGetComponentAt(PathFindCord.class,
						      indexOfEcs)
				.getCord();
		// add the 8 neighbours
		neighbours.add(centerVector.addAndReturnVector(-1, 0));
		neighbours.add(centerVector.addAndReturnVector(0, -1));
		neighbours.add(centerVector.addAndReturnVector(0, 1));
		neighbours.add(centerVector.addAndReturnVector(1, 0));

		neighbours.add(centerVector.addAndReturnVector(1, 1));
		neighbours.add(centerVector.addAndReturnVector(-1, 1));
		neighbours.add(centerVector.addAndReturnVector(1, -1));
		neighbours.add(centerVector.addAndReturnVector(-1, -1));
		for (Vector2f neib : neighbours) {
			// if the tile is valid

			if (map.isValidCord(neib)
			    && (mapLayer.hasComponent(
				       PathFindCord.class,
				       map.getEcsIndexFromWorldVector2f(
					       neib)))) {
				tmp.add(mapLayer.unsafeGetComponentAt(
					PathFindCord.class,
					map.getEcsIndexFromWorldVector2f(
						neib)));
			}
		}
		return tmp;
	}

	/**
	 * updates position from the player
	 * @param engineState: engine state
	 * @param map: tile map
	 * @param layerNumber : layernumber
	 * @param player : player entity
	 * @param mob1 : mob entity
	 * @param gjk: GJK
	 */
	public static void
	updateEnemyPositionFromPlayer(EngineState engineState, Map map,
				      int layerNumber, int player, int mob1,
				      GJK gjk)
	{
		EngineTransforms.addPlayerDiffusionValAtPlayerPos(
			engineState, map, layerNumber, player);
		MapLayer mapLayer = map.getLayerEngineState(layerNumber);
		if (map.getEcsIndexFromWorldVector2f(
			    engineState
				    .unsafeGetComponentAt(
					    PhysicsPCollisionBody.class, mob1)
				    .pureGetCenter())
		    == -1) {
			System.out.println(
				"bad thing happened in update enemy position. the return value is -1. Printing all components of the mob");
			engineState.printAllComponentsAt(mob1);
			System.out.println(
				"printing the PhysicsPCollisionBody polygon points");
			engineState
				.unsafeGetComponentAt(
					PhysicsPCollisionBody.class, mob1)
				.print();
			return;
		}
		ArrayList<PathFindCord> mobNeighb = getEightNeighbourVector(
			map,
			map.getEcsIndexFromWorldVector2f(
				engineState
					.unsafeGetComponentAt(
						PhysicsPCollisionBody.class,
						mob1)
					.pureGetCenter()),
			mapLayer);

		float maxValue = 0;

		Vector2f maxPosition = new Vector2f();
		Vector2f mobPosition =
			engineState
				.unsafeGetComponentAt(
					PhysicsPCollisionBody.class, mob1)
				.pureGetCenter();

		Vector2f playerPosition =
			engineState
				.unsafeGetComponentAt(
					PhysicsPCollisionBody.class, player)
				.pureGetCenter();

		// get the mob's highest neighbour value
		for (PathFindCord neib : mobNeighb) {
			if (neib.getDiffusionValue() >= maxValue) {
				maxValue = neib.getDiffusionValue();
				maxPosition = neib.getCord();
			}
		}

		boolean tileHaveTurret = false;
		Vector2f turretPos = new Vector2f();
		// loop through all of the turrets and check if the turret is on
		// the maximum
		for (int j = engineState.getInitialSetIndex(TurretSet.class);
		     poj.EngineState.isValidEntity(j);
		     j = engineState.getNextSetIndex(TurretSet.class, j)) {
			turretPos =
				engineState
					.unsafeGetComponentAt(
						PhysicsPCollisionBody.class, j)
					.pureGetCenter();
			// if the turret is on the maxposition tile
			if ((int)turretPos.x == (int)maxPosition.x
			    && (int)turretPos.y == (int)maxPosition.y) {
				tileHaveTurret = true;
				break;
			}
		}


		// if mob and player are at the same tile

		final PCollisionBody a = engineState.unsafeGetComponentAt(
			AggroRange.class, mob1);


		final PhysicsPCollisionBody b =
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, player);

		// if the zombie is in aggro range with the player
		if (engineState.unsafeGetComponentAt(PathfindSeek.class, mob1)
			    .isNotPathfinding()
		    || Systems.arePCollisionBodiesColliding(
			       gjk, a, b)) { // TODO refactor this --
			return;
		}
		// in the same world cord
		// check if the tile have player or turret on it (if have both,
		// will focus on the player first)
		if ((int)mobPosition.x == (int)playerPosition.x
		    && (int)mobPosition.y == (int)playerPosition.y) {
			// TODO: NEED TO INTEGRATE THIS WITH COLLISION!!

			engineState
				.unsafeGetComponentAt(MovementDirection.class,
						      mob1)
				.setDirection(
					CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition)));

			engineState.unsafeGetComponentAt(Movement.class, mob1)
				.setVelocity(0f, 0f);
			// if the mob does not have the same position as the
			// player
			if (Math.abs(mobPosition.x - playerPosition.x)
				    >= PlayGame.EPSILON
			    && Math.abs(mobPosition.y - playerPosition.y)
				       >= PlayGame.EPSILON) {
				engineState
					.unsafeGetComponentAt(
						MovementDirection.class, mob1)
					.setDirection(CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition)));
			}
			// mob have the same position as the player
			else {
				engineState
					.unsafeGetComponentAt(Movement.class,
							      mob1)
					.setVelocity(0f, 0f);
			}
		}
		// if the tile have a turret
		else if (tileHaveTurret) {
			engineState
				.unsafeGetComponentAt(MovementDirection.class,
						      mob1)
				.setDirection(
					CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition)));

			// if the mob does not have the same position as the
			// turret
			if (Math.abs(mobPosition.x - turretPos.x)
				    >= PlayGame.EPSILON
			    && Math.abs(mobPosition.y - turretPos.y)
				       >= PlayGame.EPSILON) {
				engineState
					.unsafeGetComponentAt(
						MovementDirection.class, mob1)
					.setDirection(CardinalDirections.getClosestDirectionFromDirectionVector(
						turretPos
							.subtractAndReturnVector(
								mobPosition)));
			}
			// mob have the same position as the turret
			else {
				engineState
					.unsafeGetComponentAt(Movement.class,
							      mob1)
					.setVelocity(0f, 0f);
			}
		}
		// test if the current tile the mob is at is bigger than the max
		// value
		else if (maxValue
			 <= map.getLayerEngineState(0)
				    .unsafeGetComponentAt(
					    PathFindCord.class,
					    map.getEcsIndexFromWorldVector2f(
						    mobPosition))
				    .getDiffusionValue()) {

			CardinalDirections tempDir =
				engineState
					.unsafeGetComponentAt(
						MovementDirection.class, mob1)
					.getDirection();

		}
		// the max neighbour value is bigger than the value of the tile
		// that the mob is standing on
		else {
			mobPosition.floor();
			maxPosition.floor();
			CardinalDirections tempDir =
				CardinalDirections
					.getClosestDirectionFromDirectionVector(
						maxPosition
							.subtractAndReturnVector(
								mobPosition));
			engineState
				.unsafeGetComponentAt(MovementDirection.class,
						      mob1)
				.setDirection(tempDir);


			engineState
				.unsafeGetComponentAt(HasAnimation.class, mob1)
				.setAnimation(
					engineState
						.unsafeGetComponentAt(
							AnimationWindowAssets
								.class,
							mob1)
						.getAnimation(tempDir, 1));
		}
	}


	/**
	 * adds diffusion val at player pos
	 * @param engineState: engine state
	 * @param map: tile map
	 * @param layerNumber : layernumber
	 * @param player : player entity
	 */
	public static void
	addPlayerDiffusionValAtPlayerPos(EngineState engineState, Map map,
					 int layerNumber, int player)
	{

		MapLayer mapLayer = map.getLayerEngineState(layerNumber);
		Vector2f playerPosition =
			engineState
				.unsafeGetComponentAt(
					PhysicsPCollisionBody.class, player)
				.pureGetCenter();

		// TODO: turret diffusion value.. NEED TO BE CHANGED LATER TO
		// MATCH THE PHYSICS COLLISION BODY!
		for (int i = engineState.getInitialSetIndex(TurretSet.class);
		     poj.EngineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(TurretSet.class, i)) {
			mapLayer.unsafeGetComponentAt(
					PathFindCord.class,
					map.getEcsIndexFromWorldVector2f(
						engineState
							.unsafeGetComponentAt(
								PhysicsPCollisionBody
									.class,
								i)
							.pureGetCenter()))
				.setDiffusionValue(
					GameConfig.TOWER_DIFFUSION_VALUE);
		}

		if (map.getEcsIndexFromWorldVector2f(playerPosition) != -1) {

			mapLayer.unsafeGetComponentAt(
					PathFindCord.class,
					map.getEcsIndexFromWorldVector2f(
						playerPosition))
				.setDiffusionValue(
					GameConfig.PLAYER_DIFFUSION_VALUE);
		}
	}

	/**
	 * pushes tile map layer to queue with culling
	 * @param map: tile map
	 * @param tileLayer: map layer
	 * @param windowWidth: window width
	 * @param windowHeight: window height
	 * @param tileScreenWidth: tile screen wdith
	 * @param tileScreenHeight: tile screen height
	 * @param cam: camera
	 * @param invCam: inverse camera
	 * @param q : render objs
	 */
	private static int TILE_MAP_RENDER_HELPER_SET_CAPACITY = 10000;
	private static HashSet<Integer> tileMapRenderHelperSet =
		new HashSet<Integer>(
			TILE_MAP_RENDER_HELPER_SET_CAPACITY); // used to help
							      // render the
							      // tiles in
							      // O(1) time
	public static void
	pushTileMapLayerToQueue(final Map map, final MapLayer tileLayer,
				final int windowWidth, final int windowHeight,
				final int tileScreenWidth,
				final int tileScreenHeight, final Camera cam,
				final Camera invCam, ArrayList<RenderObject> q)
	{
		tileMapRenderHelperSet.clear();

		for (float i = -tileScreenWidth;
		     i <= windowWidth + tileScreenWidth;
		     i += tileScreenWidth / 2f) {
			for (float j = -tileScreenHeight;
			     j <= windowHeight + 3 * tileScreenHeight;
			     j += tileScreenHeight / 2f) {
				Vector2f wc =
					new Vector2f(i, j).pureMatrixMultiply(
						invCam);

				int e = map.getEcsIndexFromWorldVector2f(wc);

				if (e == -1
				    || tileMapRenderHelperSet.contains(e)
				    || !tileLayer.hasComponent(Render.class, e))
					continue;

				Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
					tileLayer.unsafeGetComponentAt(
						WorldAttributes.class, e),
					tileLayer.unsafeGetComponentAt(
						Render.class, e),
					cam);
				Systems.pushRenderComponentToList(
					tileLayer.unsafeGetComponentAt(
						Render.class, e),
					q);
				tileMapRenderHelperSet.add(e);
			}
		}
	}


	/**
	 * debug render physics collisions
	 * @param e: engine State
	 * @param q : render objs
	 * @param cam :camera
	 * @param c :Color
	 */
	public static void
	debugRenderPhysicsPCollisionBodies(final EngineState e,
					   ArrayList<RenderObject> q,
					   final Camera cam, Color c)
	{

		for (int i = e.getInitialSetIndex(PhysicsPCollisionBody.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PhysicsPCollisionBody.class, i)) {
			Systems.pCollisionBodyDebugRenderer(
				e.unsafeGetComponentAt(
					PhysicsPCollisionBody.class, i),
				q, cam, c);
		}
	}


	/**
	 * debug render PHitBox collisions
	 * @param e: engine State
	 * @param q : render objs
	 * @param cam :camera
	 * @param c :Color
	 */
	public static void debugRenderPHitBox(final EngineState e,
					      ArrayList<RenderObject> q,
					      final Camera cam)
	{

		for (int i = e.getInitialSetIndex(PHitBox.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PHitBox.class, i)) {
			Systems.pCollisionBodyDebugRenderer(
				e.unsafeGetComponentAt(PHitBox.class, i), q,
				cam, Color.BLUE);
		}
	}

	/**
	 * debug render aggro collisions
	 * @param e: engine State
	 * @param q : render objs
	 * @param cam :camera
	 * @param c :Color
	 */
	public static void debugRenderAggro(final EngineState e,
					    ArrayList<RenderObject> q,
					    final Camera cam)
	{

		for (int i = e.getInitialSetIndex(AggroRange.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(AggroRange.class, i)) {
			Systems.pCollisionBodyDebugRenderer(
				e.unsafeGetComponentAt(AggroRange.class, i), q,
				cam, Color.magenta);
		}
	}


	/**
	 * updates collision body from world attr
	 * @param e: engine State
	 */
	public static void
	updatePCollisionBodiesFromWorldAttr(final EngineState e)
	{

		for (int i = e.getInitialSetIndex(PhysicsPCollisionBody.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PhysicsPCollisionBody.class, i)) {

			PhysicsPCollisionBody ppe = e.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, i);

			Optional<WorldAttributes> wwe =
				e.getComponentAt(WorldAttributes.class, i);

			if (!wwe.isPresent()) {
				continue;
			}

			Systems.updatePCollisionBodyPositionFromWorldAttr(
				ppe, wwe.get());
		}

		for (int i = e.getInitialSetIndex(PHitBox.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PHitBox.class, i)) {

			Optional<WorldAttributes> wwe =
				e.getComponentAt(WorldAttributes.class, i);

			if (!wwe.isPresent()) {
				continue;
			}

			Systems.updatePCollisionBodyPositionFromWorldAttr(
				e.unsafeGetComponentAt(PHitBox.class, i),
				wwe.get());
		}

		for (int i = e.getInitialSetIndex(AggroRange.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(AggroRange.class, i)) {

			Optional<WorldAttributes> wwe =
				e.getComponentAt(WorldAttributes.class, i);

			if (!wwe.isPresent()) {
				continue;
			}

			Systems.updatePCollisionBodyPositionFromWorldAttr(
				e.unsafeGetComponentAt(AggroRange.class, i),
				wwe.get());
		}
	}


	/**
	 * update the triggered attack cycles
	 * @param e: engine State
	 * @param dt: delta time
	 */
	public static void updateTriggeredAttackCycles(final EngineState e,
						       double dt)
	{
		ArrayList<AttackCycle> atkCycle =
			e.getRawComponentArrayListPackedData(AttackCycle.class);

		for (int i = 0; i < atkCycle.size(); ++i) {
			AttackCycle a = atkCycle.get(i);

			if (a.isAttacking()) {
				a.updateAccTime(dt);
			}
		}
	}

	/**
	 *  deletes the component if the despawn timer is finished
	 * @param e: engine State
	 * @param dt: delta time
	 */
	public static void
	deleteAllComponentsAtIfDespawnTimerIsFinishedAndUpdateDespawnTimerTime(
		EngineState engineState, double dt)
	{

		for (int i = engineState.getInitialSetIndex(DespawnTimer.class);
		     engineState.isValidEntity(i);
		     i = engineState.getNextComponentIndex(DespawnTimer.class,
							   i)) {
			DespawnTimer n = engineState.unsafeGetComponentAt(
				DespawnTimer.class, i);

			n.decrementTimerBy(dt);

			if (n.isOutOfTime()) {
				Systems.deleteAllComponentsAt(engineState, i);
			}
		}
	}

	/**
	 *  pushes the out of hp events if they are out of hp
	 * @param g: play game
	 */
	public static void pushOutOfHPEventsIfHPIsZeroOrLess(PlayGame g)
	{
		ifSetIsOutOfHPPushEventToEventStack(g, MobSet.class,
						    new MobOutOfHPEvent(g));

		ifSetIsOutOfHPPushEventToEventStack(g, PlayerSet.class,
						    new PlayerOutOfHPEvent(g));

		ifSetIsOutOfHPPushEventToEventStack(g, TurretSet.class,
						    new TurretOutOfHPEvent(g));

		ifSetIsOutOfHPPushEventToEventStack(g, BossSet.class,
						    new BossDefeatedEvent(g));
	}


	/**
	 *  generalized push to game stack event
	 * @param g: play game
	 * @param c: type
	 * @param event: event
	 */
	private static void
	ifSetIsOutOfHPPushEventToEventStack(PlayGame g,
					    Class<? extends Component> c,
					    FocusedPlayGameEvent event)
	{
		EngineState engineState = g.getEngineState();

		for (int i = engineState.getInitialSetIndex(c);
		     engineState.isValidEntity(i);
		     i = engineState.getNextComponentIndex(c, i)) {

			Optional<HitPoints> hOpt =
				engineState.getComponentAt(HitPoints.class, i);

			if (!hOpt.isPresent())
				continue;

			if (hOpt.get().getHP() <= 0) {
				event.setFocus1(i);
				g.pushEventToEventHandler(event);
			}
		}
	}


	/**
	 *  player pick up collectibles
	 * @param g: play game
	 */
	public static void playerPickUpCollectibles(PlayGame g)
	{
		EntityCollisionAlgorithms.pickUpEventForPlayer(
			g, GameConfig.PICKUP_CASH_AMOUNT, CashPack.class,
			Money.class);

		EntityCollisionAlgorithms.pickUpEventForPlayer(
			g, GameConfig.PICKUP_AMMOPACK_AMOUNT, AmmoPack.class,
			Ammo.class);

		EntityCollisionAlgorithms.pickUpEventForPlayer(
			g, GameConfig.PICKUP_HEALTHPACK_AMOUNT,
			HealthPack.class, HitPoints.class);

		EntityCollisionAlgorithms.pickUpEventForPlayer(
			g, GameConfig.PICKUP_AMMOPACK_AMOUNT, PowerUp.class,
			Damage.class);
	}


	/**
	 *  spawns the wave
	 * @param g: play game
	 * @param speed_bonus: speed bonus
	 * @param hp_bonus: hp bonus
	 * @param damage_bonus: damage bonus
	 */
	public static void spawnWave(PlayGame g, float speed_bonus,
				     int hp_bonus, int damage_bonus)
	{
		EngineState engineState = g.getEngineState();

		for (int i = 0; i < GameConfig.MOB_SPAWN_POINTS.size(); i++) {
			engineState.spawnEntitySet(new MobSet(
				GameConfig.MOB_SPAWN_POINTS.get(i).x,
				GameConfig.MOB_SPAWN_POINTS.get(i).y,
				speed_bonus, hp_bonus, damage_bonus));
		}
		g.incrementWaveNumber();
		engineState
			.unsafeGetComponentAt(
				SoundEffectAssets.class,
				engineState.getInitialSetIndex(MobSet.class))
			.playSoundEffectAt(
				ThreadLocalRandom.current().nextInt(0, 3));
	}

	/**
	 *  spawns the boss
	 * @param g: play game
	 * @param speed_bonus: speed bonus
	 * @param hp_bonus: hp bonus
	 * @param damage_bonus: damage bonus
	 */
	public static void spawnBoss(PlayGame g, float speed_bonus,
				     int hp_bonus, int damage_bonus)
	{
		GameResources.bossAlertSound.play();
		GameResources.bossAlertSound.resetClip();
		// stop the game background sound
		GameResources.gameBgSound.end();
		// play boss sound effect
		GameResources.bossSpawnSound.play();

		GameResources.bossSound.playContinuously();

		g.getEngineState().spawnEntitySet(new BossSet(
			30f, 30f, speed_bonus, hp_bonus, damage_bonus));
	}

	public static void mobSpawner(PlayGame g)
	{
		EngineState engineState = g.getEngineState();

		// spawning zombies
		//
		if (engineState.getRawComponentArrayListPackedData(MobSet.class)
			    .size()
		    < GameConfig.MAX_MOBS) {

			if ((int)g.getAcct() % (int)g.getWaveSpawnTimer()
			    < 25) {

				int n = ThreadLocalRandom.current().nextInt(0,
									    5);

				// magic constatns to make the game feel right
				float speedBonus =
					Math.min(GameConfig.MAX_SPEED_BONUS,
						 (float)g.waveNumber / 20000f);

				int damageBonus = (int)Math.min(
					GameConfig.MAX_DAMAGE, n * 2);


				switch (n) {
				case 0:
					// the fast one
					EngineTransforms.spawnWave(
						g, speedBonus, -10 + n * 2, 0);
					break;
				case 1:
					// the tanky one
					EngineTransforms.spawnWave(
						g, 0f, n * 30, damageBonus);

					break;

				case 2:
					// the tanky one
					EngineTransforms.spawnWave(
						g, 0f, n * 30, damageBonus);

					break;
				default:

					EngineTransforms.spawnWave(g, 0, n * 5,
								   0);
					break;
				}
			}
		}

		if (g.getWaveNumber() % 30 == 0
		    && g.getEngineState()
				       .getRawComponentArrayListPackedData(
					       BossSet.class)
				       .size()
			       == 0) {

			spawnBoss(g, 0, 0, 0);
		}
	}
	public static void spawnRandomCollectibles(PlayGame g, int everyNFrames,
						   int nCollectiblesToSpawn)
	{
		int frameNumber = g.getFrameNumber();
		Map map = g.getMap();
		EngineState engineState = g.getEngineState();

		if (frameNumber % everyNFrames == 0) {


			for (int i = 0; i < nCollectiblesToSpawn; ++i) {

				int n = ThreadLocalRandom.current().nextInt(
					0, map.getMapSize() - 1);
				// if it is not a wall
				if (!map.getIsWallFromLayerAndCord(
					    TileMap.Map.COLLISION_LAYER, n)) {
					switch (ThreadLocalRandom.current()
							.nextInt(0, 4)) {
					case 0:
						engineState.spawnEntitySet(new CashPack(
							map.getVector2fFromEcsIndex(
								n)));
						break;

					case 1:
						engineState.spawnEntitySet(new AmmoPack(
							map.getVector2fFromEcsIndex(
								n)));
						break;

					case 2:
						engineState.spawnEntitySet(
							new HealthPack(
								map.getVector2fFromEcsIndex(
									n)));
						break;

					case 3:
						engineState.spawnEntitySet(new PowerUp(
							map.getVector2fFromEcsIndex(
								n)));
						break;
					}
				} else
					--i;
			}
		}
	}
}
