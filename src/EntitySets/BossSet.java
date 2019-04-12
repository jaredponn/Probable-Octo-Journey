package EntitySets;

import java.awt.image.BufferedImage;

import Components.*;
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

public class BossSet extends MobSet
{

	public BossSet(float x, float y, float speed_bonus, int hp_bonus,
		       int damage_bonus, BufferedImage enemySpriteSheet,
		       float MOB_WIDTH, float MOB_HEIGHT, float MOB_SPEED,
		       PCollisionBody MOB_COLLISION_BODY, int MOB_HP,
		       AttackCycle MOB_ATTACK_CYCLE,
		       PCollisionBody MOB_AGGRO_RANGE, int MOB_ATTACK_DAMAGE,
		       AnimationWindowAssets MOB_ANIMATION_WINDOW_ASSETS,
		       Animation enemyNMoveAnimation, int MOB_MAX_HP,
		       PCollisionBody ENEMY_HITBOX_BODY,
		       OctoMeleeAttackBuffer melee)
	{
		super(x, y, speed_bonus, hp_bonus, damage_bonus,
		      enemySpriteSheet, MOB_WIDTH, MOB_HEIGHT, MOB_SPEED,
		      MOB_COLLISION_BODY, MOB_HP, MOB_ATTACK_CYCLE,
		      MOB_AGGRO_RANGE, MOB_ATTACK_DAMAGE,
		      MOB_ANIMATION_WINDOW_ASSETS, enemyNMoveAnimation,
		      MOB_MAX_HP, ENEMY_HITBOX_BODY, melee);
	}

	public BossSet(float x, float y, float speed_bonus, int hp_bonus,
		       int damage_bonus)
	{

		this(x, y, speed_bonus, hp_bonus, damage_bonus,
		     GameResources.bossSpriteSheet, GameConfig.MOB_WIDTH,
		     GameConfig.MOB_HEIGHT, GameConfig.MOB_SPEED,
		     GameConfig.BOSS_PHYSICS_BODY, GameConfig.BOSS_HP,
		     GameConfig.BOSS_ATTACK_CYCLE, GameConfig.BOSS_AGGRO_RANGE,
		     GameConfig.BOSS_ATTACK_DAMAGE,
		     GameConfig.BOSS_ANIMATION_WINDOW_ASSETS,
		     GameResources.bossNMoveAnimation, GameConfig.MOB_MAX_HP,
		     GameConfig.BOSS_HIT_BOX_BODY,
		     GameConfig.BOSS_MELEE_ATTACK_BUFFER);
	}
}
