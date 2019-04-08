package Game;

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
import poj.Collisions.CollisionShape;
import poj.Collisions.QuadTree;
import poj.Collisions.Rectangle;
import poj.Component.Component;
import poj.Component.Components;
import poj.GameWindow.InputPoller;
import poj.Render.RenderObject;
import poj.Time.Timer;
import poj.linear.Vector2f;

import java.util.Optional;

public class TileMapCollisionAlgorithms
{

	private static ArrayList<CollisionShape>
		SURROUNDING_TILES_COLLISION_BUF =
			new ArrayList<CollisionShape>(9);
	public static QuadTree generateQuadTreeFromMap(Map map)
	{

		QuadTree q =
			new QuadTree(0, new Rectangle(0, 0, map.mapWidth + 1,
						      map.mapHeight + 1));
		for (int i = 0; i < map.getNumberOfLayers(); ++i) {
			EngineState tmp = map.getLayerEngineState(i);

			ArrayList<PhysicsPCollisionBody> arr =
				tmp.getRawComponentArrayListPackedData(
					PhysicsPCollisionBody.class);

			for (PhysicsPCollisionBody col : arr)
				q.insert(col.getPolygon());
		}
		return q;
	}

	public static void nudgePhysicsPCollisionBodiesOutsideTileMap(
		PlayGame g, Class<? extends Component> set)
	{
		EngineState engineState = g.getEngineState();
		Map map = g.getMap();
		GJK gjk = g.getGJK();

		for (int i = engineState.getInitialSetIndex(set);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set, i)) {

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

			SURROUNDING_TILES_COLLISION_BUF.clear();

			Vector2f wc = a.get().getCenter().pureFloor();

			querySurroundingCollisionBodiesFromTileMap(
				wc, map, SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(1, 0), map,
				SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(0, 1), map,
				SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(1, 1), map,
				SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(-1, 0), map,
				SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(0, -1), map,
				SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(-1, -1), map,
				SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(1, -1), map,
				SURROUNDING_TILES_COLLISION_BUF);

			querySurroundingCollisionBodiesFromTileMap(
				wc.pureAdd(-1, 1), map,
				SURROUNDING_TILES_COLLISION_BUF);


			for (CollisionShape cs :
			     SURROUNDING_TILES_COLLISION_BUF) {
				gjk.clearVerticies();
				if (gjk.areColliding(cs,
						     a.get().getPolygon())) {
					Systems.nudgeCollisionBodyBOutOfA(
						cs, a.get(), aw.get(), gjk);
				}
			}
		}
	}

	private static void querySurroundingCollisionBodiesFromTileMap(
		Vector2f v, Map map, ArrayList<CollisionShape> destBuf)
	{
		final int tmp = map.getEcsIndexFromWorldVector2f(v);
		MapLayer ml = map.getLayerEngineState(map.COLLISION_LAYER);

		if (tmp == (-1))
			return;

		if (ml.hasComponent(PhysicsPCollisionBody.class, tmp)) {
			destBuf.add(ml.unsafeGetComponentAt(
					      PhysicsPCollisionBody.class, tmp)
					    .getPolygon());
		}

		// destBuf.add();
	}

	private static ArrayList<CollisionShape> QUERIED_Q_TREE_COLLISION_BUF =
		new ArrayList<CollisionShape>(100);
	public static void
	nudgePhysicsPCollisionBodiesOutsideTileMapWithQuadTree(
		PlayGame g, Class<? extends Component> set)
	{
		EngineState engineState = g.getEngineState();
		GJK gjk = g.getGJK();

		for (int i = engineState.getInitialSetIndex(set);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set, i)) {

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

			QuadTree qtree = g.getTileMapCollisionQuadTree();

			QUERIED_Q_TREE_COLLISION_BUF.clear();
			qtree.queryCollisions(a.get().getPolygon(),
					      QUERIED_Q_TREE_COLLISION_BUF);

			for (CollisionShape cs : QUERIED_Q_TREE_COLLISION_BUF) {
				gjk.clearVerticies();
				if (gjk.areColliding(cs,
						     a.get().getPolygon())) {
					Systems.nudgeCollisionBodyBOutOfA(
						cs, a.get(), aw.get(), gjk);
				}
			}
		}
	}


	public static void nudgePhysicsPCollisionBodiesOutsideTileMap(
		EngineState engineState, GJK g,
		final Class<? extends Component> set0, final MapLayer map)
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
}
