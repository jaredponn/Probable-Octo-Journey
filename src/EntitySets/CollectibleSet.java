package EntitySets;

/**
 * CollectibleSet. Colletible entities
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

public class CollectibleSet extends EntitySet
{

	public CollectibleSet(double spawnTime)
	{
		super();

		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.cashImage)));

		addComponent(new WorldAttributes(new Vector2f(1f, 1f),
						 GameConfig.PICKUP_WIDTH,
						 GameConfig.PLAYER_HEIGHT));

		addComponent(new Lifespan(GameConfig.PICKUP_CASH_SPAWN_TIME,
					  spawnTime));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PICKUP_COLLISION_BODY));
	}

	public CollectibleSet(float x, float y, double spawnTime)
	{
		super();

		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.cashImage)));

		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.PICKUP_WIDTH,
						 GameConfig.PICKUP_HEIGHT));

		addComponent(new Lifespan(GameConfig.PICKUP_CASH_SPAWN_TIME,
					  spawnTime));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PICKUP_COLLISION_BODY));
	}
}
