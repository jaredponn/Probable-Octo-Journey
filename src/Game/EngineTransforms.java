package Game;
import java.awt.Color;

/**
 * Engine Transforms. Mutations to the PlayGame engineState.
 * Date: February 10, 2019
 * @author Jared, Haiyang He, Romiro Piquer, Alex Stark
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

import Components.AttackCycle;
import Components.*;
import Components.CardinalDirections;
import Components.DespawnTimer;
import Components.HasAnimation;
import Components.Movement;
import Components.MovementDirection;
import Components.PHitBox;
import Components.PathFindCord;
import Components.PhysicsPCollisionBody;
import Components.Render;
import Components.WorldAttributes;
import EntitySets.PlayerSet;
import EntitySets.MobSet;
import EntitySets.TurretSet;
import Resources.GameConfig;
import Resources.GameResources;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Animation;
import poj.EngineState;
import poj.Collisions.GJK;
import poj.Component.Component;
import poj.Component.Components;
import poj.GameWindow.InputPoller;
import poj.Render.RenderObject;
import poj.Time.Timer;
import poj.linear.Vector2f;

import java.util.Optional;

public class EngineTransforms
{
	public static void updateAnimationWindows(EngineState engineState,
						  double dt)
	{
		for (HasAnimation i :
		     engineState.getRawComponentArrayListPackedData(
			     HasAnimation.class)) {
			Systems.updateHasAnimationComponent(i, dt);
		}
	}

	public static void
	cropSpriteSheetsFromAnimationWindows(EngineState engineState)
	{

		for (int i = engineState.getInitialComponentIndex(
			     HasAnimation.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(HasAnimation.class,
							   i)) {

			Optional<Render> rc =
				engineState.getComponentAt(Render.class, i);

			if (!rc.isPresent())
				continue;

			Systems.updateRenderComponentWindowFromHasAnimation(
				engineState.getComponentAt(Render.class, i)
					.get(),
				engineState.unsafeGetComponentAt(
					HasAnimation.class, i));
		}
	}


	public static void
	updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera(
		EngineState engineState, final Camera cam)
	{
		for (int i = engineState.getInitialComponentIndex(
			     WorldAttributes.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(
			     WorldAttributes.class, i)) {

			Optional<Render> rc =
				engineState.getComponentAt(Render.class, i);
			if (!rc.isPresent())
				continue;


			Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
				engineState.unsafeGetComponentAt(
					WorldAttributes.class, i),
				rc.get(), cam);
		}
	}

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

	// path finding
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
				"bad thing happened in update enemy position..");
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

		/*
		CardinalDirections.print(
			CardinalDirections
				.getClosestDirectionFromDirectionVector(
					playerPosition.subtractAndReturnVector(
						mobPosition)));*/
		/*
		System.out.println(
			" the diffusion value at mob is ="
			+ map.getLayerEngineState(0)
				  .getComponentAt(
					  PathFindCord.class,
					  map.getEcsIndexFromWorldVector2f(
						  engineState
							  .getComponentAt(
								  WorldAttributes
									  .class
								  ,
								  mob1)
							  .getCenteredBottomQuarter()))
				  .getDiffusionValue());
		System.out.println("player x position  floor ="
				   + playerPosition.x);
		System.out.println("player y position  floor ="
				   + playerPosition.y);
		System.out.println("mob x position before floor ="
				   + mobPosition.x);
		System.out.println("mob y position before floor ="
				   + mobPosition.y);*/

		// if mob and player are at the same tile

		// LINE 309 HAIYANG HE PATH FINDING WORKING WITH ATTACK CYCLE
		final PCollisionBody a = engineState.unsafeGetComponentAt(
			AggroRange.class, mob1);


		for (int j = engineState.getInitialSetIndex(TurretSet.class);
		     poj.EngineState.isValidEntity(j);
		     j = engineState.getNextSetIndex(TurretSet.class, j)) {
			if (checkTurretCollisionWithMob(engineState, j, mob1,
							gjk)) {
				return;
			}
		}

		final PhysicsPCollisionBody b =
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, player);

		if (Systems.arePCollisionBodiesColliding(gjk, a, b)
		    || engineState.unsafeGetComponentAt(AttackCycle.class, mob1)
			       .isAttacking()) { // TODO refactor this -- move
						 // to a component
			// engineState.unsafeGetComponentAt(Movement.class,
			// mob1) .setSpeed(0);

			// CardinalDirections tempDir =
			// engineState
			//.unsafeGetComponentAt(
			// MovementDirection.class, mob1)
			//.getDirection();

			// set animation as idle position
			// engineState
			//.unsafeGetComponentAt(HasAnimation.class, mob1)
			//.setAnimation(AnimationGetter.queryEnemySprite(
			// tempDir, 0));
			return;
		}

		// in the same world cord
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
				.setSpeed(GameConfig.MOB_SPEED);
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
				engineState
					.unsafeGetComponentAt(Movement.class,
							      mob1)
					.setSpeed(GameConfig.MOB_SPEED);
			}
			// mob have the same position as the player
			else {
				engineState
					.unsafeGetComponentAt(Movement.class,
							      mob1)
					.setSpeed(0);
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
			/*
			System.out.println(
				" went inside this cord is bigger than all
			neightbours!!"); System.out.println( "set the mob speed
			equal to 0!!!!!!!");
				*/

			/*
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(0f);
				*/
			// zombie will be in idle

			CardinalDirections tempDir =
				engineState
					.unsafeGetComponentAt(
						MovementDirection.class, mob1)
					.getDirection();

			// when the current tile the enemy/mob is standing on
			// is HIGHER than all other values, it will display the
			// previous walking animation!!

			// engineState.getComponentAt(HasAnimation.class, mob1)
			//.setAnimation(AnimationGetter.queryEnemySprite(
			// tempDir, 0));
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
				.setAnimation(AnimationGetter.queryEnemySprite(
					tempDir, 1));
			engineState.unsafeGetComponentAt(Movement.class, mob1)
				.setSpeed(GameConfig.MOB_SPEED);
		}

		/*
		 weired error:
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: -1
at java.util.ArrayList.elementData(ArrayList.java:422)
at java.util.ArrayList.get(ArrayList.java:435)
at poj.Component.Components.hasComponent(Components.java:187)
at poj.Component.Components.getComponentAt(Components.java:121)
at poj.Component.Components.unsafeGetComponentAt(Components.java:138)
at Game.EngineTransforms.getEightNeighbourVector(EngineTransforms.java:189)
at
Game.EngineTransforms.updateEnemyPositionFromPlayer(EngineTransforms.java:226)
at Game.PlayGame.runGame(PlayGame.java:386)
at Game.World.runGameLoop(World.java:155)
at App.App.runAppLoop(App.java:120)
at Main.main(Main.java:25)

		 */
	}

	public static boolean
	checkTurretCollisionWithMob(EngineState engineState,
				    // Map map, int layerNumber,
				    int turretPosition, int mob1, GJK gjk)
	{

		final PhysicsPCollisionBody a =
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, turretPosition);

		final PhysicsPCollisionBody b =
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, mob1);

		return Systems.arePCollisionBodiesColliding(gjk, a, b);
	}

	public static void
	addPlayerDiffusionValAtPlayerPos(EngineState engineState, Map map,
					 int layerNumber, int player)
	{

		MapLayer mapLayer = map.getLayerEngineState(layerNumber);
		/*
		Vector2f playerPosition =
			engineState
				.getComponentAt(WorldAttributes.class, player)
				.getCenteredBottomQuarter();
				*/
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
								WorldAttributes
									.class,
								i)
							.getCenteredBottomQuarter()))
				.setDiffusionValue(
					GameConfig.TOWER_DIFFUSION_VALUE);
		}


		/*
		System.out.println("player X=" + playerPosition.x);
		System.out.println("player Y=" + playerPosition.y);
		System.out.println(
			"this.map.getEcsIndexFromWorldVector2f(playerPosition)"
			+ this.map.getEcsIndexFromWorldVector2f(
				  playerPosition));
		System.out.println(
			"player x position inside
		addPlayerDiffusionValAtPlayerPos ="
			+ playerPosition.x);
		System.out.println(
			"player y position inside
		addPlayerDiffusionValAtPlayerPos ="
			+ playerPosition.y);
		*/
		if (map.getEcsIndexFromWorldVector2f(playerPosition) != -1) {
			// map.printPathfindCord(0);

			if (!mapLayer.unsafeGetComponentAt(
					     PathFindCord.class,
					     map.getEcsIndexFromWorldVector2f(
						     playerPosition))
				     .getIsWall()) {

				mapLayer.unsafeGetComponentAt(
						PathFindCord.class,
						map.getEcsIndexFromWorldVector2f(
							playerPosition))
					.setDiffusionValue(
						GameConfig
							.PLAYER_DIFFUSION_VALUE);
			} else {
				mapLayer.unsafeGetComponentAt(
						PathFindCord.class,
						map.getEcsIndexFromWorldVector2f(
							playerPosition))
					.setDiffusionValue(0f);
			}
		}
	}

	private static int TILE_MAP_RENDER_HELPER_SET_CAPACITY = 5000;
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
				final Camera invCam, Queue<RenderObject> q)
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
				Systems.pushRenderComponentToQueue(
					tileLayer.unsafeGetComponentAt(
						Render.class, e),
					q);
				tileMapRenderHelperSet.add(e);
			}
		}
	}


	public static void
	debugRenderPhysicsPCollisionBodies(final EngineState e,
					   Queue<RenderObject> q,
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


	public static void debugRenderPHitBox(final EngineState e,
					      Queue<RenderObject> q,
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

	public static void debugRenderAggro(final EngineState e,
					    Queue<RenderObject> q,
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


	public static void
	arePhysicsPCollisionBodiesColliding(EngineState engineState, GJK g,
					    Class<? extends Component> set0,
					    Class<? extends Component> set1)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final Optional<PhysicsPCollisionBody> a =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			if (!a.isPresent())
				continue;

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final Optional<PhysicsPCollisionBody> b =
					engineState.getComponentAt(
						PhysicsPCollisionBody.class, j);

				if (!b.isPresent())
					continue;

				if (Systems.arePCollisionBodiesColliding(
					    g, a.get(), b.get())) {
					System.out.println(
						"PCOllision detected");
					break;
				}
			}
		}
	}

	public static void resolvePhysicsPCollisionBodiesAgainstTileMap(
		EngineState engineState, GJK g,
		final Class<? extends Component> set0, final MapLayer map,
		final double dt)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final Optional<PhysicsPCollisionBody> a =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			Optional<Movement> va =
				engineState.getComponentAt(Movement.class, i);
			if (!a.isPresent())
				continue;

			if (!va.isPresent())
				continue;

			for (PhysicsPCollisionBody b :
			     map.getRawComponentArrayListPackedData(
				     PhysicsPCollisionBody.class)) {

				// sets velocity so that it never enters the
				// wall
				Vector2f tmp =
					Systems.pCollisionBodiesGetCollisionBodyBDisplacementDelta(
						g, b, a.get(), va.get(), dt);
				tmp.mul(1 / ((float)dt));
				va.get().setVelocity(tmp);
			}
		}
	}

	public static void nudgePhysicsPCollisionBodiesOutsideTileMap(
		EngineState engineState, GJK g,
		final Class<? extends Component> set0, final MapLayer map,
		final double dt)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final Optional<PhysicsPCollisionBody> a =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			Optional<WorldAttributes> aw =
				engineState.getComponentAt(
					WorldAttributes.class, i);

			if (!a.isPresent())
				continue;

			if (!aw.isPresent())
				continue;

			for (PhysicsPCollisionBody b :
			     map.getRawComponentArrayListPackedData(
				     PhysicsPCollisionBody.class)) {
				g.clearVerticies();
				if (g.areColliding(b.getPolygon(),
						   a.get().getPolygon())) {
					Systems.nudgeCollisionBodyBOutOfA(
						b, a.get(), aw.get(), g);
				}
			}
		}
	}

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

	public static void pushOutOfHPEventsIfHPIsZeroOrLess(PlayGame g)
	{
		ifSetIsOutOfHPPushEventToEventStack(g, MobSet.class,
						    new MobOutOfHPEvent(g));

		ifSetIsOutOfHPPushEventToEventStack(g, PlayerSet.class,
						    new PlayerOutOfHPEvent(g));

		ifSetIsOutOfHPPushEventToEventStack(g, TurretSet.class,
						    new TurretOutOfHPEvent(g));
	}

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
				event.setFocus(i);
				g.pushEventToEventHandler(event);
			}
		}
	}

	public static boolean
	doDamageInSetifPCollisionBodyAndSetPHitBoxAreColliding(
		EngineState engineState, PCollisionBody pbody,
		Class<? extends Component> c, int damage)
	{
		GJK gjk = new GJK();

		for (int i = engineState.getInitialSetIndex(c);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(c, i)) {

			Optional<PHitBox> phbodyOpt =
				engineState.getComponentAt(PHitBox.class, i);

			if (!phbodyOpt.isPresent())
				continue;

			PHitBox phbody = phbodyOpt.get();

			if (Systems.arePCollisionBodiesColliding(gjk, pbody,
								 phbody)) {

				Optional<HitPoints> hpOpt =
					engineState.getComponentAt(
						HitPoints.class, i);

				if (!hpOpt.isPresent())
					continue;

				HitPoints hp = hpOpt.get();

				hp.hurt(damage);

				// only do damage to one thing at a time
				return true;
			}
		}
		return false;
	}
}
