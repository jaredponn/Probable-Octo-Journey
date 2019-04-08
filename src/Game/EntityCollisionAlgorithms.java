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

	public static <T extends PCollisionBody> void
	ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
		PlayGame g, Class<? extends Component> a,
		Class<? extends Component> b, Class<T> collisionBodyType,
		FocusedPlayGameEvent event)
	{
		EngineState engineState = g.getEngineState();

		for (int i = engineState.getInitialSetIndex(a);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(a, i)) {

			Optional<? extends Component> apopt =
				engineState.getComponentAt(collisionBodyType,
							   i);

			if (!apopt.isPresent())
				continue;

			PCollisionBody ap = (PCollisionBody)apopt.get();

			for (int j = engineState.getInitialSetIndex(b);
			     engineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(b, j)) {

				Optional<? extends Component> bpopt =
					engineState.getComponentAt(
						collisionBodyType, j);

				if (!bpopt.isPresent())
					continue;

				PCollisionBody bp = (PCollisionBody)bpopt.get();

				if (ap.isCollidingWith(bp) && i != j) {
					event.setFocus(i);
					event.f();
				}
			}
		}
	}


	private static NudgeAOutOfBPCollisionBodyEvent<
		PhysicsPCollisionBody> NUDGE_A_OUT_OF_B_P_COLLISION_BODY_MEMO =
		new NudgeAOutOfBPCollisionBodyEvent<PhysicsPCollisionBody>(
			PhysicsPCollisionBody.class);
	public static void
	nudgeSetAAndBIfPCollisionBodiesAreTouching(PlayGame g,
						   Class<? extends Component> a,
						   Class<? extends Component> b)
	{
		NUDGE_A_OUT_OF_B_P_COLLISION_BODY_MEMO.setPlayGame(g);
		ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
			g, a, b, PhysicsPCollisionBody.class,
			NUDGE_A_OUT_OF_B_P_COLLISION_BODY_MEMO);
	}
}
