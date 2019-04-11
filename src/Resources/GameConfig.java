package Resources;

import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.ArrayList;

import Components.*;

import poj.Pair;
import poj.linear.Vector2f;


// IMPORTANT: Everything in this document should be in screen coordinates. That
// is, the height and width of things are expressed as ratios of the tile size.
public class GameConfig
{

	////////////////////////////
	////// HUD/GUI config //////
	////////////////////////////
	public static final int HUD_FONT_SIZE = 20;
	public static final int HUD_LINE_SPACING = HUD_FONT_SIZE * 2;
	// public static final Font HUD_FONT = new Font("TimesRoman",
	// Font.PLAIN, HUD_FONT_SIZE);
	public static final Font HUD_FONT = GameResources.CREEPER_FONT;
	public static final Font HUD_FONT_SMALL =
		GameResources.CREEPER_FONT_SMALL;

	public static final Color HUD_FONT_COLOR = Color.WHITE;
	public static final int A_INTEGER = 65;
	public static final int Z_INTEGER = 90;

	/////////////////////////////////////
	////// animation assets config //////
	/////////////////////////////////////
	public static final int IDLE_ANIMATION = 0;
	public static final int ATTACK_ANIMATION = 10;

	///////////////////////////
	////// player config //////
	///////////////////////////
	// public static final float PLAYER_SPEED = 0.0015f;
	public static final float PLAYER_SPEED = 0.005f;
	public static final float PLAYER_WIDTH =
		GameResources.PLAYER_SPRITE_WIDTH
		/ GameResources.TILE_SCREEN_WIDTH;
	public static final int PLAYER_HP = 100000;     // 50;
	public static final int PLAYER_MAX_HP = 100000; // 75;
	public static final int PLAYER_DIFFUSION_VALUE = (int)Math.pow(2, 12);
	public static final int TOWER_DIFFUSION_VALUE = (int)Math.pow(2, 5);
	public static final float PLAYER_HEIGHT =
		GameResources.PLAYER_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;
	public static final Vector2f PLAYER_SPAWNNING_POS =
		new Vector2f(32, 32);
	public static final PCollisionBody PLAYER_COLLISION_BODY =
		new PCollisionBody(
			new Vector2f(0.2f, 0.55f),  // displacement
			new Vector2f(0.15f, 0.15f), // center
						    // collision body:
			new Vector2f(0.25f / 4f, 1 / 4f),
			new Vector2f(0.75f / 4f, 1 / 4f),
			new Vector2f(0 / 4f, 0.75f / 4f),
			new Vector2f(1 / 4f, 0.75f / 4f),
			new Vector2f(0 / 4f, 0.25f / 4f),
			new Vector2f(0.25f / 4f, 0 / 4f),
			new Vector2f(0.75f / 4f, 0 / 4f),
			new Vector2f(1 / 4f, 0.25f / 4f));

	// hitbox by inspection
	public static final PCollisionBody PLAYER_HITBOX_BODY =
		new PCollisionBody(
			new Vector2f(-0.5f, 0f),   // displacement
			new Vector2f(1.5f, 0.75f), // center
						   // collision body:
			new Vector2f(1, 0.5f), new Vector2f(1.25f, 0.2f),
			new Vector2f(1.7f, 1.25f), new Vector2f(1.95f, 1f));

	public static final AttackCycle PLAYER_ATTACK_CYCLE =
		new AttackCycle(100, 100);

	public static final int PLAYER_STARTING_CASH = 100000;
	public static final int PLAYER_STARTING_AMMO = 20;
	public static final int PLAYER_MAX_AMMO = 100;
	public static final int PLAYER_STARTING_DAMAGE = 30;
	public static final float MELEE_HEIGHT = 0.3555f;
	public static final float MELEE_WIDTH = 0.2f;
	public static final PCollisionBody PLAYER_MELEE_N_ATK_BODY =
		new PCollisionBody(new Vector2f(0, 0.25f),   // displacement
				   new Vector2f(0.3f, 0.3f), // center
							     // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(0, MELEE_HEIGHT),
				   new Vector2f(MELEE_WIDTH, 0),
				   new Vector2f(MELEE_WIDTH, MELEE_HEIGHT));

	public static final PCollisionBody PLAYER_MELEE_NE_ATK_BODY =
		new PCollisionBody(new Vector2f(0f, 0.25f),  // displacement
				   new Vector2f(0.3f, 0.3f), // center
							     // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(0, MELEE_HEIGHT),
				   new Vector2f(MELEE_HEIGHT, 0),
				   new Vector2f(MELEE_HEIGHT, MELEE_HEIGHT));


	public static final PCollisionBody PLAYER_MELEE_E_ATK_BODY =
		new PCollisionBody(new Vector2f(0.25f, 0.25f), // displacement
				   new Vector2f(0.3f, 0.3f),   // center
							     // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(MELEE_HEIGHT, 0),
				   new Vector2f(0, MELEE_WIDTH),
				   new Vector2f(MELEE_HEIGHT, MELEE_WIDTH));

	public static final PCollisionBody PLAYER_MELEE_SE_ATK_BODY =
		new PCollisionBody(new Vector2f(0.1f, 0f),   // displacement
				   new Vector2f(0.3f, 0.3f), // center
							     // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(0, MELEE_HEIGHT),
				   new Vector2f(MELEE_HEIGHT, 0),
				   new Vector2f(MELEE_HEIGHT, MELEE_HEIGHT));

	public static final PCollisionBody PLAYER_MELEE_S_ATK_BODY =
		new PCollisionBody(new Vector2f(0, 0.25f),    // displacement
				   new Vector2f(0.3f, -0.3f), // center
							      // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(0, -MELEE_HEIGHT),
				   new Vector2f(MELEE_WIDTH, 0),
				   new Vector2f(MELEE_WIDTH, -MELEE_HEIGHT));

	public static final PCollisionBody PLAYER_MELEE_SW_ATK_BODY =
		new PCollisionBody(new Vector2f(-0.25f, -0.1f), // displacement
				   new Vector2f(0.3f, 0.3f),    // center
							     // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(0, MELEE_HEIGHT),
				   new Vector2f(MELEE_HEIGHT, 0),
				   new Vector2f(MELEE_HEIGHT, MELEE_HEIGHT));

	public static final PCollisionBody PLAYER_MELEE_W_ATK_BODY =
		new PCollisionBody(new Vector2f(0.25f, 0.25f), // displacement
				   new Vector2f(-0.3f, 0.2f),  // center
							      // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(-MELEE_HEIGHT, 0),
				   new Vector2f(0, MELEE_WIDTH),
				   new Vector2f(-MELEE_HEIGHT, MELEE_WIDTH));

	public static final PCollisionBody PLAYER_MELEE_NW_ATK_BODY =
		new PCollisionBody(new Vector2f(-0.25f, 0.25f), // displacement
				   new Vector2f(0.3f, 0.3f),    // center
							     // collision body:
				   new Vector2f(0, 0),
				   new Vector2f(0, MELEE_HEIGHT),
				   new Vector2f(MELEE_HEIGHT, 0),
				   new Vector2f(MELEE_HEIGHT, MELEE_HEIGHT));

	public static final AnimationWindowAssets
		PLAYER_ANIMATION_WINDOW_ASSETS = new AnimationWindowAssets(
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.playerGunIdleAnimation,
				IDLE_ANIMATION),
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.playerGunMoveAnimation,
				IDLE_ANIMATION + 1),
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.playerMeleeIdleAnimation,
				IDLE_ANIMATION + 2),
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.playerMeleeMoveAnimation,
				IDLE_ANIMATION + 3),
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.playerMeleeAttackAnimation,
				ATTACK_ANIMATION));

	/////////////////////////
	///// turret config /////
	/////////////////////////
	public static final int TOWER_BUILD_COST = 250;
	public static final PCollisionBody TURRET_COLLISION_BODY =
		new PCollisionBody(
			new Vector2f(0.27f,
				     1.24f), // displacement
			new Vector2f(0.5f,
				     0.5f), // center
					    // collision body:
			new Vector2f(0.35f, 0.5f), new Vector2f(0.65f, 0.5f),
			new Vector2f(0.5f, 0.65f), new Vector2f(0.5f, 0.65f),
			new Vector2f(0.5f, 0.35f), new Vector2f(0.35f, 0.5f),
			new Vector2f(0.65f, 0.5f), new Vector2f(0.5f, 0.35f));
	public static final int TURRET_HP = 100;
	public static final int TURRET_MAX_HP = 100;
	public static final float SHELL_SPEED = 0.04f;
	public static final int SHELL_DAMAGE = 75;
	public static final int TURRET_STARTING_AMMO = 40;
	public static final PCollisionBody TURRET_HITBOX_BODY =
		new PCollisionBody(
			new Vector2f(-0.22f, 0.86f), // displacement
			new Vector2f(1.5f, 0.75f),   // center
						     // collision body:
			new Vector2f(1, 0.5f), new Vector2f(1.25f, 0.2f),
			new Vector2f(1.7f, 1.25f), new Vector2f(1.95f, 1f));

	public static final PCollisionBody TURRET_AGGRO_BODY =
		// clang-format off
		new PCollisionBody(
			new Vector2f(-1f, 12.5f),  // displacement
			new Vector2f(0.15f/ 0.25f, 0.15f/ 0.25f), // center
						    // collision body:
			new Vector2f(0.25f / 0.05f, 1     / 0.05f),
			new Vector2f(0.75f / 0.05f, 1     / 0.05f),
			new Vector2f(0     / 0.05f, 0.75f / 0.05f),
			new Vector2f(1     / 0.05f, 0.75f / 0.05f),
			new Vector2f(0     / 0.05f, 0.25f / 0.05f),
			new Vector2f(0.25f / 0.05f, 0     / 0.05f),
			new Vector2f(0.75f / 0.05f, 0     / 0.05f),
			new Vector2f(1     / 0.05f, 0.25f / 0.05f));
	// clang-format on

	public static final AttackCycle TURRET_ATTACK_CYCLE =
		new AttackCycle(1000, 1000);

	public static final AnimationWindowAssets
		TURRET_ANIMATION_WINDOW_ASSETS = new AnimationWindowAssets(
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.turretAttackAnimation,
				ATTACK_ANIMATION));

	//////////////////////////
	////// bullet config /////
	//////////////////////////
	public static final float BULLET_SPEED = 0.01f;
	public static final float BULLET_WIDTH =
		GameResources.BULLET_SPRITE_WIDTH
		/ GameResources.TILE_SCREEN_WIDTH;
	public static final float BULLET_HEIGHT =
		GameResources.BULLET_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;
	public static final double BULLET_LIFE_SPAN = 0.8;
	public static final int BULLET_DAMAGE = 30;
	public static final int BULLET_COST = 5;

	public static final PCollisionBody BULLET_COLLISION_BODY =
		new PCollisionBody(new Vector2f(0.0f,
						0.0f), // displacement
				   new Vector2f(BULLET_WIDTH / 2,
						BULLET_HEIGHT / 2), // center
								    // collision
								    // body:
				   new Vector2f(0, 0),
				   new Vector2f(0, BULLET_HEIGHT),
				   new Vector2f(BULLET_WIDTH, 0),
				   new Vector2f(BULLET_WIDTH, BULLET_HEIGHT));

	///////////////////////
	////// mob config /////
	///////////////////////
	public static final float MOB_SPEED = 0.6f * PLAYER_SPEED;
	public static final float BOSS_SPEED = 0.95f * MOB_SPEED;
	public static final float MOB_HEIGHT =
		GameResources.ENEMY_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;
	public static final float MOB_WIDTH = GameResources.ENEMY_SPRITE_WIDTH
					      / GameResources.TILE_SCREEN_WIDTH;

	// percent chance for a mob to drop cash on death
	public static final int MOB_DROP_RATE = 33;

	// pathfinding/collision detection
	public static final PCollisionBody MOB_COLLISION_BODY =
		// clang-format off
			new PCollisionBody(
				new Vector2f(0.2f , 0.55f), // displacement
				new Vector2f(0.5f/4f ,0.5f/4f), // center
							  // collision body:
				new Vector2f(0.25f/4f,   1    /4f), new Vector2f(0.75f /4f, 1    /4f),
				new Vector2f(0    /4f,   0.75f/4f), new Vector2f(1     /4f, 0.75f/4f),
				new Vector2f(0    /4f, 0.25f  /4f), new Vector2f(0.25f /4f, 0    /4f),
				new Vector2f(0.75f/4f, 0      /4f), new Vector2f(1     /4f, 0.25f/4f));
	// clang-format on

	// combat configs
	public static final int MOB_ATTACK_DAMAGE = 10;
	public static final int MOB_HP = 100;
	public static final int MOB_MAX_HP = MOB_HP;

	public static final int BOSS_ATTACK_DAMAGE = MOB_ATTACK_DAMAGE * 2;
	public static final int BOSS_HP = MOB_HP * 2;
	public static final int BOSS_MAX_HP = BOSS_HP;

	public static final AttackCycle MOB_ATTACK_CYCLE =
		new AttackCycle(GameResources.animationDurationms * 8,
				GameResources.animationDurationms * 6);
	public static final PCollisionBody ENEMY_HITBOX_BODY =
		PLAYER_HITBOX_BODY;

	public static final PCollisionBody MOB_MELEE_ATTACK_BODY =
		new PCollisionBody(new Vector2f(-0.5f, 0), // displacement
				   new Vector2f(0.5f,
						0.5f), // center
						       // collision body:
				   new Vector2f(0, 0), new Vector2f(2, 0),
				   new Vector2f(0, 2), new Vector2f(2, 2));

	public static final PCollisionBody MOB_MELEE_N_ATK_BODY =
		PLAYER_MELEE_N_ATK_BODY;
	public static final PCollisionBody MOB_MELEE_NE_ATK_BODY =
		PLAYER_MELEE_NE_ATK_BODY;
	public static final PCollisionBody MOB_MELEE_E_ATK_BODY =
		PLAYER_MELEE_E_ATK_BODY;
	public static final PCollisionBody MOB_MELEE_SE_ATK_BODY =
		PLAYER_MELEE_SE_ATK_BODY;
	public static final PCollisionBody MOB_MELEE_S_ATK_BODY =
		PLAYER_MELEE_S_ATK_BODY;
	public static final PCollisionBody MOB_MELEE_SW_ATK_BODY =
		PLAYER_MELEE_SW_ATK_BODY;
	public static final PCollisionBody MOB_MELEE_W_ATK_BODY =
		PLAYER_MELEE_W_ATK_BODY;
	public static final PCollisionBody MOB_MELEE_NW_ATK_BODY =
		PLAYER_MELEE_NW_ATK_BODY;

	public static final PCollisionBody MOB_AGGRO_RANGE = new PCollisionBody(
		// clang-format off
		new Vector2f(0.2f, 0.55f),  // displacement
		new Vector2f(0.25f, 0.25f), // center
					    // collision body:
		new Vector2f(0.25f / 3f, 1 / 3f),
		new Vector2f(0.75f / 3f, 1 / 3f),
		new Vector2f(0 / 3f    , 0.75f / 3f),
		new Vector2f(1 / 3f    , 0.75f / 3f),
		new Vector2f(0 / 3f    , 0.25f / 3f),
		new Vector2f(0.25f / 3f, 0 / 3f),
		new Vector2f(0.75f / 3f, 0 / 3f),
		new Vector2f(1 / 3f    , 0.25f / 3f));
	// clang-format on

	// spawn configs
	public static final float MOB_SPAWN_TIMER = 10.0f;
	public static final double MOB_DESPAWN_TIMER = 5000d; // in ms

	// spawn points:
	public static final ArrayList<Vector2f> MOB_SPAWN_POINTS =
		new ArrayList<Vector2f>() {
			{
				add(new Vector2f(9.5f,
						 19.5f)); // western Blockbuster
				add(new Vector2f(30f,
						 14f)); // parking lot
				add(new Vector2f(40f,
						 30f)); // near fountain
				add(new Vector2f(60f,
						 24f)); // park past parking lot
				add(new Vector2f(42f,
						 50f)); // gas station
			}
		};


	public static final AnimationWindowAssets MOB_ANIMATION_WINDOW_ASSETS =
		new AnimationWindowAssets(
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.enemyMoveAnimation, 1),
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.enemyAttackAnimation, 10),
			new Pair<OctoAnimationBuffer, Integer>(
				GameResources.enemyDeathAnimation, 30));

	////////////////////////////
	///// construct config /////
	////////////////////////////
	public static final float CONSTRUCT_HEIGHT = 64;
	public static final float CONSTRUCT_WIDTH = 48;

	/////////////////////////
	///// pickup config /////
	/////////////////////////
	public static final float PICKUP_WIDTH =
		GameResources.CASH_SPRITE_WIDTH
		/ GameResources.TILE_SCREEN_WIDTH;
	public static final float PICKUP_HEIGHT =
		GameResources.CASH_SPRITE_HEIGHT
		/ GameResources.TILE_SCREEN_HEIGHT;

	public static final double PICKUP_MAX_TIME = 10 * 1000d;

	public static final PCollisionBody PICKUP_COLLISION_BODY =
		new PCollisionBody(
			new Vector2f(0.0f,
				     0.4f), // displacement
			new Vector2f(PICKUP_WIDTH / 2,
				     PICKUP_HEIGHT / 4), // center
							 // collision
							 // body:
			new Vector2f(0, 0), new Vector2f(0, PICKUP_HEIGHT / 2),
			new Vector2f(PICKUP_WIDTH, 0),
			new Vector2f(PICKUP_WIDTH, PICKUP_HEIGHT / 2));

	// money:
	public static final int PICKUP_CASH_AMOUNT = 100;
	public static final float PICKUP_CASH_SPAWN_TIME = 20.0f;
	// power-up:
	public static final int PICKUP_POWERUP_AMOUNT = 5;
	public static final float PICKUP_POWERUP_SPAWN_TIME = 20.0f;
	// health:
	public static final int PICKUP_HEALTHPACK_AMOUNT = 50;
	public static final float PICKUP_HEALTHPACK_SPAWN_TIME = 20.0f;
	// ammo:
	public static final int PICKUP_AMMOPACK_AMOUNT = 20;
	public static final float PICKUP_AMMOPACK_SPAWN_TIME = 20.0f;
	public static final int PURCHASE_AMMOPACK_AMOUNT = 20;

	////////////////////////
	///// input config /////
	////////////////////////
	public static final int SWITCH_WEAPONS = KeyEvent.VK_X;
	public static final int ATTACK_KEY = KeyEvent.VK_SPACE;
	public static final int BUILD_TOWER = KeyEvent.VK_Q;
	public static final int BUILD_TRAP = KeyEvent.VK_E;
	public static final int BUY_AMMO = KeyEvent.VK_B;
	public static final int QUIT_KEY = KeyEvent.VK_ESCAPE;

	public static final int ARROW_UP = KeyEvent.VK_UP;
	public static final int ARROW_DOWN = KeyEvent.VK_DOWN;
	public static final int ARROW_LEFT = KeyEvent.VK_LEFT;
	public static final int ARROW_RIGHT = KeyEvent.VK_RIGHT;

	// key, cooldown (ms)
	public static final ArrayList<Pair<Integer, Double>> COOL_DOWN_KEYS =
		new ArrayList<Pair<Integer, Double>>() {
			{
				add(new Pair<Integer, Double>(ATTACK_KEY,
							      0.3d));
				add(new Pair<Integer, Double>(BUILD_TOWER, 1d));
				add(new Pair<Integer, Double>(SWITCH_WEAPONS,
							      1d));
				add(new Pair<Integer, Double>(BUILD_TRAP, 1d));
				add(new Pair<Integer, Double>(BUY_AMMO, 1d));

				add(new Pair<Integer, Double>(ARROW_UP, 0.1d));
				add(new Pair<Integer, Double>(ARROW_DOWN,
							      0.1d));
				add(new Pair<Integer, Double>(ARROW_LEFT,
							      0.5d));
				add(new Pair<Integer, Double>(ARROW_RIGHT,
							      0.5d));
			}
		};

	public static final Color APP_CLEAR_COLOR = new Color(131, 173, 239);
}
