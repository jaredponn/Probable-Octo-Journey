package EntitySets;

/**
 * Type of mob, but stronger
 * @author Alex Stark
 * @version 1.0 - 03/31/19
 */

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class BossMob extends EntitySet
{

	/**
	 * Spawn mob at first spawn point in GameConfig.MOB_SPAWN_POINTS
	 */
	public BossMob()
	{
		this( GameConfig.MOB_SPAWN_POINTS.get(0) );
	}

	/**
	 * Spawn Mob at coordinates x , y
	 * @param x-coord
	 * @param y-coord
	 */
	public BossMob(float x, float y)
	{
		super();

		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.enemySpriteSheet)));
		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.MOB_WIDTH,
						 GameConfig.MOB_HEIGHT));

		addComponent(
			new HasAnimation(GameResources.enemyNMoveAnimation));
		addComponent(new Movement(GameConfig.BOSS_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.MOB_COLLISION_BODY));
		addComponent(new HitPoints(GameConfig.BOSS_HP,GameConfig.BOSS_MAX_HP));
		addComponent(new PHitBox(GameConfig.ENEMY_HITBOX_BODY));
		addComponent(new AttackCycle(GameConfig.MOB_ATTACK_CYCLE));
		addComponent(new AggroRange(GameConfig.MOB_AGGRO_RANGE));
	}

	/**
	 * Spawn mob at coordinates defined by a vector
	 * @param posVector to spawn mob at
	 */
	public BossMob(Vector2f n)
	{
		this(n.x, n.y);
	}
}
