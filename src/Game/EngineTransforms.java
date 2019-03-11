package Game;

/**
 * Engine Transforms. Mutations to the PlayGame engineState.
 * Date: February 10, 2019
 * @author Jared, Haiyang He, Romiro Piquer, Alex Stark
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;

import Components.AttackCycle;
import Components.CardinalDirections;
import Components.HasAnimation;
import Components.Movement;
import Components.MovementDirection;
import Components.PhysicsPCollisionBody;
import Components.PathFindCord;
import Components.Render;
import Components.WorldAttributes;
import EntitySets.PlayerSet;
import EntitySets.TurretSet;
import Resources.GameConfig;
import Resources.GameResources;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Animation;
import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.Collisions.GJK;
import poj.Component.Component;
import poj.Component.Components;
import poj.Logger.LogLevels;
import poj.Logger.Logger;
import poj.Render.RenderObject;
import poj.Time.Timer;
import poj.linear.Vector2f;

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
			Systems.updateRenderComponentWindowFromHasAnimation(
				engineState.getComponentAt(Render.class, i),
				engineState.getComponentAt(HasAnimation.class,
							   i));
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
			Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
				engineState.getComponentAt(
					WorldAttributes.class, i),
				engineState.getComponentAt(Render.class, i),
				cam);
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
			Systems.updateWorldAttribPositionFromMovement(
				engineState.getComponentAt(
					WorldAttributes.class, i),
				engineState.getComponentAt(Movement.class, i),
				dt);
		}
	}


	public static void
	setMovementVelocityFromMovementDirection(EngineState engineState)
	{
		for (int i = engineState.getInitialSetIndex(
			     MovementDirection.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(MovementDirection.class,
						     i)) {
			Systems.setMovementVelocityFromMovementDirection(
				engineState.getComponentAt(Movement.class, i),
				engineState.getComponentAt(
					MovementDirection.class, i));
		}
	}

	// path finding
	public static ArrayList<PathFindCord>
	getEightNeighbourVector(Map map, int indexOfEcs, MapLayer mapLayer)
	{
		ArrayList<Vector2f> neighbours = new ArrayList<Vector2f>();
		ArrayList<PathFindCord> tmp = new ArrayList<PathFindCord>();
		Vector2f centerVector =
			mapLayer.getComponentAt(PathFindCord.class, indexOfEcs)
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
				tmp.add(mapLayer.getComponentAt(
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
		ArrayList<PathFindCord> mobNeighb = getEightNeighbourVector(
			map,
			map.getEcsIndexFromWorldVector2f(
				engineState
					.getComponentAt(
						PhysicsPCollisionBody.class,
						mob1)
					.getPolygon()
					.pureGetAPointInPolygon(0)),
			mapLayer);
		float maxValue = 0;
		Vector2f maxPosition = new Vector2f();
		Vector2f mobPosition =
			engineState
				.getComponentAt(PhysicsPCollisionBody.class,
						mob1)
				.getPolygon()
				.pureGetAPointInPolygon(0);

		Vector2f playerPosition =
			engineState
				.getComponentAt(PhysicsPCollisionBody.class,
						player)
				.getPolygon()
				.pureGetAPointInPolygon(0);

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

		final PhysicsPCollisionBody a = engineState.getComponentAt(
			PhysicsPCollisionBody.class, mob1);

		final PhysicsPCollisionBody b = engineState.getComponentAt(
			PhysicsPCollisionBody.class, player);

		if (Systems.arePCollisionBodiesColliding(gjk, a, b)) {
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(0);

			CardinalDirections tempDir =
				engineState
					.getComponentAt(MovementDirection.class,
							mob1)
					.getDirection();

			// idle position
			engineState.getComponentAt(HasAnimation.class, mob1)
				.setAnimation(
					findEnemyFacingSprite(tempDir, 0));
			// TODO REMOVE THIS
			System.out.println(
				"GAME OVER -- PLAYER TOUCHED ZOMBIE");
			System.exit(0);
			return;
		}

		if ((int)mobPosition.x == (int)playerPosition.x
		    && (int)mobPosition.y == (int)playerPosition.y) {
			// TODO: NEED TO INTEGRATE THIS WITH COLLISION!!

			engineState
				.getComponentAt(MovementDirection.class, mob1)
				.setDirection(
					CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition)));
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(GameConfig.MOB_SPEED);
			/*
			// if the mob does not have the same position as the
			// player
			if (Math.abs(mobPosition.x - playerPosition.x)
				    >= PlayGame.EPSILON
			    && Math.abs(mobPosition.y - playerPosition.y)
				       >= PlayGame.EPSILON) {
				engineState
					.getComponentAt(MovementDirection.class,
							mob1)
					.setDirection(CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition)));
				engineState.getComponentAt(Movement.class, mob1)
					.setSpeed(GameConfig.MOB_SPEED);
			}
			// mob have the same position as the player
			else {
				engineState.getComponentAt(Movement.class, mob1)
					.setSpeed(0);
			}
			*/
		}
		// test if the current tile the mob is at is bigger than the max
		// value
		else if (maxValue
			 <= map.getLayerEngineState(0)
				    .getComponentAt(
					    PathFindCord.class,
					    map.getEcsIndexFromWorldVector2f(
						    mobPosition))
				    .getDiffusionValue()

		) {
			System.out.println(
				" went inside this cord is bigger than all neightbours!!");
			System.out.println(
				"set the mob speed equal to 0!!!!!!!");
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(0f);
			// zombie will be in idle
			CardinalDirections tempDir =
				engineState
					.getComponentAt(MovementDirection.class,
							mob1)
					.getDirection();
			engineState.getComponentAt(HasAnimation.class, mob1)
				.setAnimation(
					findEnemyFacingSprite(tempDir, 0));
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
				.getComponentAt(MovementDirection.class, mob1)
				.setDirection(tempDir);

			engineState.getComponentAt(HasAnimation.class, mob1)
				.setAnimation(
					findEnemyFacingSprite(tempDir, 1));
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(GameConfig.MOB_SPEED);
		}
	}

	public static void
	checkTurretCollisionWithMob(EngineState engineState,
				    // Map map, int layerNumber,
				    int turretPosition, int mob1, GJK gjk)
	{

		final PhysicsPCollisionBody a = engineState.getComponentAt(
			PhysicsPCollisionBody.class, turretPosition);

		final PhysicsPCollisionBody b = engineState.getComponentAt(
			PhysicsPCollisionBody.class, mob1);

		if (Systems.arePCollisionBodiesColliding(gjk, a, b)) {
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(0);
		}
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
				.getComponentAt(PhysicsPCollisionBody.class,
						player)
				.getPolygon()
				.pureGetAPointInPolygon(0);


		// TODO: turret diffusion value.. NEED TO BE CHANGED LATER TO
		// MATCH THE PHYSICS COLLISION BODY!
		for (int i = engineState.getInitialSetIndex(TurretSet.class);
		     poj.EngineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(TurretSet.class, i)) {
			mapLayer.getComponentAt(
					PathFindCord.class,
					map.getEcsIndexFromWorldVector2f(
						engineState
							.getComponentAt(
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

			if (!mapLayer.getComponentAt(
					     PathFindCord.class,
					     map.getEcsIndexFromWorldVector2f(
						     playerPosition))
				     .getIsWall()) {

				mapLayer.getComponentAt(
						PathFindCord.class,
						map.getEcsIndexFromWorldVector2f(
							playerPosition))
					.setDiffusionValue(
						GameConfig
							.PLAYER_DIFFUSION_VALUE);
			} else {

				mapLayer.getComponentAt(
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
					tileLayer.getComponentAt(
						WorldAttributes.class, e),
					tileLayer.getComponentAt(Render.class,
								 e),
					cam);
				Systems.pushRenderComponentToQueue(
					tileLayer.getComponentAt(Render.class,
								 e),
					q);
				tileMapRenderHelperSet.add(e);
			}
		}
	}

	public static void debugRenderPhysicsPCollisionBodies(
		final EngineState e, Queue<RenderObject> q, final Camera cam)
	{

		for (int i = e.getInitialSetIndex(PhysicsPCollisionBody.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PhysicsPCollisionBody.class, i)) {
			Systems.pCollisionBodyDebugRenderer(
				e.getComponentAt(PhysicsPCollisionBody.class,
						 i),
				q, cam);
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

			final PhysicsPCollisionBody a =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final PhysicsPCollisionBody b =
					engineState.getComponentAt(
						PhysicsPCollisionBody.class, j);

				if (Systems.arePCollisionBodiesColliding(g, a,
									 b)) {
					System.out.println(
						"PCOllision detected");
					// if (set1 instanceof TurretSet) {
					//	System.out.println(
					// 		"Collision with turrets
					// detected");
					//}
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

			final PhysicsPCollisionBody a =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			Movement va =
				engineState.getComponentAt(Movement.class, i);

			for (PhysicsPCollisionBody b :
			     map.getRawComponentArrayListPackedData(
				     PhysicsPCollisionBody.class)) {

				Optional<Double> tmp =
					Systems.pCollisionBodiesTimeOfCollision(
						g, b, a, va, dt);

				if (tmp.isPresent()) {
					final double t = tmp.get() - 0.3d;

					final double rt = t / dt;
					va.getVelocity().mul((float)rt);
					va.getVelocity().log();

					break;
				}
			}
		}
	}

	public static void
	updatePhysicsPCollisionFromWorldAttr(final EngineState e)
	{

		for (int i = e.getInitialSetIndex(PhysicsPCollisionBody.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PhysicsPCollisionBody.class, i)) {
			Systems.updatePCollisionBodyPositionFromWorldAttr(
				e.getComponentAt(PhysicsPCollisionBody.class,
						 i),
				e.getComponentAt(WorldAttributes.class, i));
		}
	}

	public static void runAttackCycleHandlersAndFreezeMovement(
		EngineState engineState, int player,
		WeaponState playerCurWPState, InputPoller ip, Camera invCam,
		double gameElapsedTime)
	{

		// players attacking
		for (int i = engineState.getInitialSetIndex(PlayerSet.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(PlayerSet.class, i)) {
			AttackCycle a = engineState.getComponentAt(
				AttackCycle.class, i);

			if (a.isAttacking()) {
				switch (a.getAttackState()) {
				case 0:
					break;

				case 1:
					AttackCycleHandlers.playerAttackHandler(
						engineState, player,
						playerCurWPState, ip, invCam,
						gameElapsedTime);
					break;
				case 2:
					break;
				case 3:
					a.endAttackCycle();
					a.resetCycle();
					break;
				}

				// setting velocity to 0
				engineState.getComponentAt(Movement.class, i)
					.setVelocity(new Vector2f(0, 0));
			}
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


	public static Animation findEnemyFacingSprite(CardinalDirections dir,
						      int flag)
	{
		// flag =0, will return idle position, if flag =1, will return
		// move direction

		switch (dir) {
		case N:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyNMoveAnimation;
			}
		case NE:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyNEMoveAnimation;
			}
		case NW:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyNWMoveAnimation;
			}
		case S:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemySMoveAnimation;
			}
		case SE:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemySEMoveAnimation;
			}
		case SW:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemySWMoveAnimation;
			}
		case W:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyWMoveAnimation;
			}
		case E:
			if (flag == 0) {
			} else if (flag == 1) {
				return GameResources.enemyEMoveAnimation;
			}
		default:
			return GameResources.enemyNMoveAnimation;
		}
	}

	public static Animation findPlayerFacingSprite(CardinalDirections dir,
						       int flag)
	{

		// flag =0, will return idle position, if flag =1, will return
		// move direction
		switch (dir) {
		case N:
			if (flag == 0) {
				return GameResources.playerNIdleAnimation;
			} else {
				return GameResources.playerNMoveAnimation;
			}
		case NE:
			if (flag == 0) {
				return GameResources.playerNEIdleAnimation;
			} else {
				return GameResources.playerNEMoveAnimation;
			}
		case NW:
			if (flag == 0) {
				return GameResources.playerNWIdleAnimation;
			} else {
				return GameResources.playerNWMoveAnimation;
			}
		case S:
			if (flag == 0) {
				return GameResources.playerSIdleAnimation;
			} else {
				return GameResources.playerSMoveAnimation;
			}
		case SE:
			if (flag == 0) {
				return GameResources.playerSEIdleAnimation;
			} else {
				return GameResources.playerSEMoveAnimation;
			}
		case SW:
			if (flag == 0) {
				return GameResources.playerSWIdleAnimation;
			} else {
				return GameResources.playerSWMoveAnimation;
			}
		case W:
			if (flag == 0) {
				return GameResources.playerWIdleAnimation;
			} else {
				return GameResources.playerWMoveAnimation;
			}
		case E:
			if (flag == 0) {
				return GameResources.playerEIdleAnimation;
			} else {
				return GameResources.playerEMoveAnimation;
			}
		default:
			return GameResources.playerNIdleAnimation;
		}
	}
}
