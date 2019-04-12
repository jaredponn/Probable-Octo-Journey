package EntitySets;

import java.awt.image.BufferedImage;

import Components.AggroRange;
import Components.AnimationWindowAssets;
import Components.AttackCycle;
import Components.CardinalDirections;
import Components.Damage;
import Components.FacingDirection;
import Components.HasAnimation;
import Components.HitPoints;
import Components.Movement;
import Components.MovementDirection;
import Components.PCollisionBody;
import Components.PHitBox;
import Components.PhysicsPCollisionBody;
import Components.Render;
import Components.SoundEffectAssets;
import Components.WorldAttributes;
import Resources.GameConfig;
import Resources.GameResources;

import poj.Animation;
/**
 * MobSet. Enemies and zombies.
 * Date: February 10, 2019
 * @author Alex Stark
 * @version 1.0
 */
import poj.EntitySet.EntitySet;
import poj.Render.ImageRenderObject;
import poj.linear.Vector2f;

public class MobSet extends EntitySet
{

	/**
	 * Spawn mob at first spawn point in GameConfig.MOB_SPAWN_POINTS
	 */
	public MobSet()
	{
		this(GameConfig.MOB_SPAWN_POINTS.get(0));
	}

	/**
	 * Spawn Mob at coordinates x , y
	 * @param x-coord
	 * @param y-coord
	 */
	public MobSet(float x, float y)
	{
		this(x, y, 0, 0, 0, GameResources.enemySpriteSheet,
		     GameConfig.MOB_WIDTH, GameConfig.MOB_HEIGHT,
		     GameConfig.MOB_SPEED, GameConfig.MOB_COLLISION_BODY,
		     GameConfig.MOB_HP, GameConfig.MOB_ATTACK_CYCLE,
		     GameConfig.MOB_AGGRO_RANGE, GameConfig.MOB_ATTACK_DAMAGE,
		     GameConfig.MOB_ANIMATION_WINDOW_ASSETS,
		     GameResources.enemyNMoveAnimation, GameConfig.MOB_MAX_HP,
		     GameConfig.ENEMY_HITBOX_BODY);

		/*
				int damage_bonus, float MOB_WIDTH =
		   GameConfig.MOB_WIDTH, float MOB_HEIGHT =
		   GameConfig.MOB_HEIGHT, float MOB_SPEED =
		   GameConfig.MOB_SPEED, PCollisionBody MOB_COLLISION_BODY =
					GameConfig.MOB_COLLISION_BODY,
				int MOB_HP = GameConfig.MOB_HP,
				AttackCycle MOB_ATTACK_CYCLE =
		   GameConfig.MOB_ATTACK_CYCLE, PCollisionBody MOB_AGGRO_RANGE =
		   GameConfig.MOB_AGGRO_RANGE, int MOB_ATTACK_DAMAGE =
		   GameConfig.MOB_ATTACK_DAMAGE, AnimationWindowAssets
		   MOB_ANIMATION_WINDOW_ASSETS =
					GameConfig.MOB_ANIMATION_WINDOW_ASSETS)
		 */
	}

	/**
	 * Spawn Mob at coordinates x , y
	 * @param x-coord x coordinate
	 * @param y-coord y coordinate
	 * @param speed_bonus  bonus speed
	 * @param hp_bonus bonus hp
	 */
	public MobSet(float x, float y, float speed_bonus, int hp_bonus,
		      int damage_bonus, BufferedImage enemySpriteSheet,
		      float MOB_WIDTH, float MOB_HEIGHT, float MOB_SPEED,
		      PCollisionBody MOB_COLLISION_BODY, int MOB_HP,
		      AttackCycle MOB_ATTACK_CYCLE,
		      PCollisionBody MOB_AGGRO_RANGE, int MOB_ATTACK_DAMAGE,
		      AnimationWindowAssets MOB_ANIMATION_WINDOW_ASSETS,
		      Animation enemyNMoveAnimation, int MOB_MAX_HP,
		      PCollisionBody ENEMY_HITBOX_BODY)
	{
		super();

		addComponent(new Render(
			new ImageRenderObject(0, 0, enemySpriteSheet)));
		addComponent(new WorldAttributes(new Vector2f(x, y), MOB_WIDTH,
						 MOB_HEIGHT));

		addComponent(new HasAnimation(enemyNMoveAnimation));
		addComponent(new Movement(MOB_SPEED + speed_bonus));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));
		addComponent(new PhysicsPCollisionBody(MOB_COLLISION_BODY));
		addComponent(new HitPoints(MOB_HP + hp_bonus,
					   MOB_MAX_HP + hp_bonus));
		addComponent(new PHitBox(ENEMY_HITBOX_BODY));
		addComponent(new AttackCycle(MOB_ATTACK_CYCLE));
		addComponent(new AggroRange(MOB_AGGRO_RANGE));
		addComponent(new Damage(MOB_ATTACK_DAMAGE + damage_bonus));
		addComponent(
			new AnimationWindowAssets(MOB_ANIMATION_WINDOW_ASSETS));
		// zombie sound effects:
		addComponent(
			new SoundEffectAssets(GameResources.zombieSoundAsset));
	}

	/**
	 * Spawn mob at coordinates defined by a vector
	 * @param posVector to spawn mob at
	 */
	public MobSet(Vector2f n)
	{
		this(n.x, n.y);
	}
}
/*
		int damage_bonus, float MOB_WIDTH = GameConfig.MOB_WIDTH,
		float MOB_HEIGHT = GameConfig.MOB_HEIGHT,
		float MOB_SPEED = GameConfig.MOB_SPEED,
		PCollisionBody MOB_COLLISION_BODY =
			GameConfig.MOB_COLLISION_BODY,
		int MOB_HP = GameConfig.MOB_HP,
		AttackCycle MOB_ATTACK_CYCLE = GameConfig.MOB_ATTACK_CYCLE,
		PCollisionBody MOB_AGGRO_RANGE = GameConfig.MOB_AGGRO_RANGE,
		int MOB_ATTACK_DAMAGE = GameConfig.MOB_ATTACK_DAMAGE,
		AnimationWindowAssets MOB_ANIMATION_WINDOW_ASSETS =
			GameConfig.MOB_ANIMATION_WINDOW_ASSETS)
 */
