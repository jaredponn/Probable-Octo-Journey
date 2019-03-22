package EntitySets;

/**
 * MobSet. Enemies and zombies.
 * Date: February 10, 2019
 * @author Alex Stark
 * @version 1.0
 */

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class MobSet extends EntitySet
{

	public MobSet()
	{
		this( GameConfig.MOB_SPAWN_POINTS.get(0) );
	}

	/**
	 * Spawn Mob at coordinates x , y
	 * @param x-coord
	 * @param y-coord
	 */
	public MobSet(float x, float y)
	{
		super();

		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.enemySpriteSheet)));
		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.MOB_WIDTH,
						 GameConfig.MOB_HEIGHT));

		addComponent(
			new HasAnimation(GameResources.enemyNMoveAnimation));
		addComponent(new Movement(GameConfig.MOB_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.MOB_COLLISION_BODY));
		addComponent(new HitPoints(GameConfig.MOB_HP));
		addComponent(new PHitBox(GameConfig.ENEMY_HITBOX_BODY));
		addComponent(new AttackCycle(GameConfig.MOB_ATTACK_CYCLE));
		addComponent(new AggroRange(GameConfig.MOB_AGGRO_RANGE));
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
