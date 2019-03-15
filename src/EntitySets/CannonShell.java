package EntitySets;

/**
 * projectile for turrets
 * Date: March 11, 2019
 * @author Alex Stark
 * @version 1.0
 */

import poj.EntitySet.EntitySet;
import Components.*;
import poj.linear.*;

import Components.Render;

import Resources.GameConfig;
import Resources.GameResources;


public class CannonShell extends EntitySet
{
	public CannonShell()
	{
		this( new Vector2f() );
	}

	public CannonShell(Vector2f posVector)
	{
		super();
		addComponent(new Render(GameResources.bulletImage));
		addComponent(new WorldAttributes(posVector,
						 GameConfig.BULLET_WIDTH,
						 GameConfig.BULLET_HEIGHT));
		addComponent(new Movement(GameConfig.SHELL_SPEED));
		//addComponent(
		//	new Lifespan(GameConfig.BULLET_LIFE_SPAN, spawnTime));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.BULLET_COLLISION_BODY));
		addComponent(new Damage(GameConfig.SHELL_DAMAGE));
	}
}
