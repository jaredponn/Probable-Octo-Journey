package EntitySets;
/**
 *
 * TurretSet. Turret entity set
 * Date: February 10, 2019
 * @author Alex Stark, Haiyang He
 * @version 1.0
 */

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class TurretSet extends EntitySet
{
	public TurretSet()
	{
		super();
		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.turret)));
		addComponent(new WorldAttributes(new Vector2f(),
						 GameConfig.MOB_WIDTH,
						 GameConfig.MOB_HEIGHT));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.TURRET_COLLISION_BODY));
		addComponent(new HitPoints(GameConfig.TURRET_HP,
					   GameConfig.TURRET_MAX_HP));
		addComponent(new PHitBox(GameConfig.TURRET_HITBOX_BODY));
		addComponent(new AggroRange(GameConfig.TURRET_AGGRO_BODY));
		addComponent(new AttackCycle(GameConfig.TURRET_ATTACK_CYCLE));
		addComponent(new Ammo(GameConfig.TURRET_STARTING_AMMO,
				      GameConfig.TURRET_STARTING_AMMO));
		addComponent(
			new HasAnimation(GameResources.turretNAttackAnimation));

		addComponent(new AnimationWindowAssets(
			GameConfig.TURRET_ANIMATION_WINDOW_ASSETS));
	}
}
