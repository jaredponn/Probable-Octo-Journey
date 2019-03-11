package EntitySets;
/**
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
		addComponent(new HitPoints(GameConfig.CONSTRUCT_HP));
	}
}
