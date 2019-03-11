package Game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;

import Components.CardinalDirections;
import Components.HasAnimation;
import Components.Movement;
import Components.MovementDirection;
import Components.PCollisionBody;
import Components.PathFindCord;
import Components.Render;
import Components.WorldAttributes;
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
import poj.Logger.LogLevels;
import poj.Logger.Logger;
import poj.Render.RenderObject;
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
					.getComponentAt(WorldAttributes.class,
							mob1)
					.getCenteredBottomQuarter()),
			mapLayer);
		float maxValue = 0;
		Vector2f maxPosition = new Vector2f();
		Vector2f mobPosition =
			engineState.getComponentAt(WorldAttributes.class, mob1)
				.getCenteredBottomQuarter();

		Vector2f playerPosition =
			engineState
				.getComponentAt(WorldAttributes.class, player)
				.getCenteredBottomQuarter();

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

		final PCollisionBody a =
			engineState.getComponentAt(PCollisionBody.class, mob1);

		final PCollisionBody b = engineState.getComponentAt(
			PCollisionBody.class, player);

		if (Systems.arePCollisionBodiesColliding(gjk, a, b)) {
			// idle position
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(0);
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
		else if (
			maxValue
			<= map.getLayerEngineState(0)
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
				   .getDiffusionValue()

		) {
			System.out.println(
				" went inside this cord is bigger than all neightbours!!");
			System.out.println(
				"set the mob speed equal to 0!!!!!!!");
			// zombie will be in idle
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(0f);
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

		final PCollisionBody a = engineState.getComponentAt(
			PCollisionBody.class, turretPosition);

		final PCollisionBody b =
			engineState.getComponentAt(PCollisionBody.class, mob1);

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
		Vector2f playerPosition =
			engineState
				.getComponentAt(WorldAttributes.class, player)
				.getCenteredBottomQuarter();
		for (int i = engineState.getInitialSetIndex(TurretSet.class);
		     engineState.isValidEntity(i);
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

	public static void debugRenderPolygons(final EngineState e,
					       Queue<RenderObject> q,
					       final Camera cam)
	{

		for (int i = e.getInitialSetIndex(PCollisionBody.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PCollisionBody.class, i)) {
			Systems.pCollisionBodyDebugRenderer(
				e.getComponentAt(PCollisionBody.class, i), q,
				cam);
		}
	}


	public static void
	arePCollisionBodiesColliding(EngineState engineState, GJK g,
				     Class<? extends Component> set0,
				     Class<? extends Component> set1)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final PCollisionBody a = engineState.getComponentAt(
				PCollisionBody.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final PCollisionBody b =
					engineState.getComponentAt(
						PCollisionBody.class, j);

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

	public static void resolvePCollisionBodiesAgainstTileMap(
		EngineState engineState, GJK g,
		final Class<? extends Component> set0, final MapLayer map,
		final double dt)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final PCollisionBody a = engineState.getComponentAt(
				PCollisionBody.class, i);

			Movement va =
				engineState.getComponentAt(Movement.class, i);

			for (PCollisionBody b :
			     map.getRawComponentArrayListPackedData(
				     PCollisionBody.class)) {

				Optional<Double> tmp =
					Systems.pCollisionBodiesTimeOfCollision(
						g, b, a, va, dt);

				if (tmp.isPresent()) {
					final double t = tmp.get() - 0.3d;

					final double rt = t / dt;
					va.getVelocity().mul((float)rt);
					va.getVelocity().log();

					//	System.out.println(
					//		a.getPolygon().toString());
					//	System.out.println(
					//		b.getPolygon().toString());
					break;
				}
			}
		}
	}

	public static void updatePCollisionFromWorldAttr(final EngineState e)
	{

		for (int i = e.getInitialSetIndex(PCollisionBody.class);
		     Components.isValidEntity(i);
		     i = e.getNextSetIndex(PCollisionBody.class, i)) {
			Systems.updatePCollisionBodyPositionFromWorldAttr(
				e.getComponentAt(PCollisionBody.class, i),
				e.getComponentAt(WorldAttributes.class, i));
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
				return GameResources.enemyNIdleAnimation;
			} else {
				return GameResources.enemyNMoveAnimation;
			}
		case NE:
			if (flag == 0) {
				return GameResources.enemyNIdleAnimation;
			} else {
				return GameResources.enemyNMoveAnimation;
			}
		case NW:
			if (flag == 0) {
				return GameResources.enemyNIdleAnimation;
			} else {
				return GameResources.enemyNMoveAnimation;
			}
		case S:
			if (flag == 0) {
				return GameResources.enemySIdleAnimation;
			} else {
				return GameResources.enemySMoveAnimation;
			}
		case SE:
			if (flag == 0) {
				return GameResources.enemySIdleAnimation;
			} else {
				return GameResources.enemySMoveAnimation;
			}
		case SW:
			if (flag == 0) {
				return GameResources.enemySIdleAnimation;
			} else {
				return GameResources.enemySMoveAnimation;
			}
		case W:
			if (flag == 0) {
				return GameResources.enemyWIdleAnimation;
			} else {
				return GameResources.enemyWMoveAnimation;
			}
		case E:
			if (flag == 0) {
				return GameResources.enemyEIdleAnimation;
			} else {
				return GameResources.enemyEMoveAnimation;
			}
		default:
			return GameResources.enemyNMoveAnimation;
		}
	}
}
