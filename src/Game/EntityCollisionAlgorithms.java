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
import PathFinding.MapGeneration;
import Resources.GameConfig;
import Resources.GameResources;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Render.*;
import poj.GameWindow.*;
import poj.Component.*;
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


public class EntityCollisionAlgorithms
{

	public static void ifSetAAndBPhysicsBodyAreCollidingRunGameEvent(
		PlayGame g, Class<? extends Component> a,
		Class<? extends Component> b, PlayGameEvent event)
	{
		EngineState engineState = g.getEngineState();

		for (int i = 0; engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(a, i)) {

			Optional<PhysicsPCollisionBody> apopt =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class, i);

			if (!apopt.isPresent())
				continue;

			PhysicsPCollisionBody ap = apopt.get();


			for (int j = 0; engineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(b, j)) {

				Optional<PhysicsPCollisionBody> bpopt =
					engineState.getComponentAt(
						PhysicsPCollisionBody.class, i);

				if (!bpopt.isPresent())
					continue;

				PhysicsPCollisionBody bp = bpopt.get();

				if (ap.isCollidingWith(bp))
					event.f();
			}
		}
	}

	public static <T extends PCollisionBody> void f(EngineState e,
							Class<T> a)
	{
		Optional<PCollisionBody> test =
			(Optional<PCollisionBody>)e.getComponentAt(a, 2);
	}
}
