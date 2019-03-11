package Resources;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import poj.linear.*;
import Components.*;
import poj.Collisions.*;
import poj.PackedVector;
import poj.Collisions.*;
import poj.Pair;


// IMPORTANT: Everything in this document should be in screen coordinates. That
// is, the height and width of things are expressed as ratios of the tile size.
public class GameConfig
{

	// player config
	public static final float PLAYER_SPEED = 0.003f;
	public static final float PLAYER_WIDTH =
		GameResources.PLAYER_SPRITE_WIDTH
		/ GameResources.TILE_SCREEN_WIDTH;
	public static final int PLAYER_HP = 100;
	public static final int PLAYER_DIFFUSION_VALUE = (int)Math.pow(2, 12);
	public static final int TOWER_DIFFUSION_VALUE = (int)Math.pow(2, 5);
	public static final float PLAYER_HEIGHT =
		GameResources.PLAYER_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;
	public static final Vector2f PLAYER_SPAWNNING_POS =
		new Vector2f(3f, 3f);
	/*
	public static final PCollisionBody PLAYER_COLLISION_BODY =
		new PCollisionBody(
			new Vector2f(0f, 0.6f), // displacement
						// collision body:
			new Vector2f(0.25f, 1), new Vector2f(0.75f, 1),
			new Vector2f(0, 0.75f), new Vector2f(1, 0.75f),
			new Vector2f(0, 0.25f), new Vector2f(0.25f, 0),
			new Vector2f(0.75f, 0), new Vector2f(1, 0.25f));
			*/
	public static final PCollisionBody PLAYER_COLLISION_BODY =
		new PCollisionBody(
			new Vector2f(0f, 0f),
			// new Vector2f(1.9f, 3.4f), // displacement
			// collision body:
			new Vector2f(0.25f, 1), new Vector2f(0.75f, 1),
			new Vector2f(0, 0.75f), new Vector2f(1, 0.75f),
			new Vector2f(0, 0.25f), new Vector2f(0.25f, 0),
			new Vector2f(0.75f, 0), new Vector2f(1, 0.25f));

	public static final PCollisionBody TURRET_COLLISION_BODY =
		new PCollisionBody(
			new Vector2f(0f, 0.8f), // displacement
						// collision body:
			new Vector2f(0.25f, 1), new Vector2f(0.75f, 1),
			new Vector2f(0, 0.75f), new Vector2f(1, 0.75f),
			new Vector2f(0, 0.25f), new Vector2f(0.25f, 0),
			new Vector2f(0.75f, 0), new Vector2f(1, 0.25f));

	// bullet config
	public static final float BULLET_SPEED = 0.02f;
	public static final float BULLET_WIDTH =
		GameResources.BULLET_SPRITE_WIDTH
		/ GameResources.TILE_SCREEN_WIDTH;
	public static final float BULLET_HEIGHT =
		GameResources.BULLET_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;
	public static final double BULLET_LIFE_SPAN = 0.8;
	public static final int BULLET_DAMAGE = 10;

	public static final PCollisionBody BULLET_COLLISION_BODY =
		new PCollisionBody(new Vector2f(0.0f, 0.0f), // displacement
							     // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(0, BULLET_HEIGHT),
				   new Vector2f(BULLET_WIDTH, 0),
				   new Vector2f(BULLET_WIDTH, BULLET_HEIGHT));
	/*
	public static final PCollisionBody BULLET_COLLISION_BODY =
		new PCollisionBody(
			new Vector2f(-0.1f, 0.1f), // displacement
						   // collision body:
			new Vector2f(0.0f, BULLET_HEIGHT),
			new Vector2f(BULLET_WIDTH / 2f, BULLET_HEIGHT),
			new Vector2f(BULLET_WIDTH, BULLET_HEIGHT),
			new Vector2f(0.0f, BULLET_HEIGHT / 2f),
			new Vector2f(BULLET_WIDTH / 2f, BULLET_HEIGHT / 2f),
			new Vector2f(BULLET_WIDTH, BULLET_HEIGHT / 2f),
			new Vector2f(0.0f, 0.0f),
			new Vector2f(BULLET_WIDTH / 2f, 0.0f),
			new Vector2f(BULLET_WIDTH, 0.0f));
			*/

	// mob config
	public static final float MOB_SPEED = 0.6f * PLAYER_SPEED;
	// public static final float MOB_SPEED = 0f;
	public static final float MOB_HEIGHT =
		GameResources.ENEMY_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;
	public static final float MOB_WIDTH = GameResources.ENEMY_SPRITE_WIDTH
					      / GameResources.TILE_SCREEN_WIDTH;

	public static final int MOB_HP = 100;
	/*public static final CircleCollisionBody MOB_COLLISION_BODY =
		new CircleCollisionBody(new CollisionCircle(0, 0, MOB_WIDTH));*/
	public static final PCollisionBody MOB_COLLISION_BODY =

		new PCollisionBody(
			new Vector2f(0f, 0.6f), // displacement
						// collision body:
			new Vector2f(0.25f, 1), new Vector2f(0.75f, 1),
			new Vector2f(0, 0.75f), new Vector2f(1, 0.75f),
			new Vector2f(0, 0.25f), new Vector2f(0.25f, 0),
			new Vector2f(0.75f, 0), new Vector2f(1, 0.25f));

	public static final float MOB_SPAWN_TIMER = 10.0f;
	// spawn points:
	public static final Vector2f MOB_SPAWNER_1 = new Vector2f(20f, 20f);

	// construct config
	public static final float CONSTRUCT_HEIGHT = 64;
	public static final float CONSTRUCT_WIDTH = 48;
	public static final int CONSTRUCT_HP = 100;

	public static final int TOWER_BUILD_COST = 250;

	// pickup config
	public static final int PICKUP_CASH_AMOUNT = 100;
	public static final float PICKUP_CASH_SPAWN_TIME = 20.0f;

	// input config
	public static final int SWITCH_WEAPONS = KeyEvent.VK_X;
	public static final int ATTACK_KEY = KeyEvent.VK_SPACE;
	public static final int BUILD_TOWER = KeyEvent.VK_Q;
	public static final int BUILD_TRAP = KeyEvent.VK_E;

	// key, cooldown (ms)
	public static final ArrayList<Pair<Integer, Double>> COOL_DOWN_KEYS =
		new ArrayList<Pair<Integer, Double>>() {
			{
				add(new Pair<Integer, Double>(SWITCH_WEAPONS,
							      0.3d));
				add(new Pair<Integer, Double>(ATTACK_KEY,
							      0.08d));
				add(new Pair<Integer, Double>(BUILD_TOWER, 1d));
				add(new Pair<Integer, Double>(SWITCH_WEAPONS,
							      1d));
				add(new Pair<Integer, Double>(BUILD_TRAP, 1d));
			}
		};
}
