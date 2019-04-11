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

/**
 * Health pick-up entity
 * @author Alex
 * 03/11/19
 */
public class CollectibleSet extends EntitySet
{


	public CollectibleSet(double spawnTime)
	{

		this(1f, 1f, spawnTime);
	}

	public CollectibleSet(float x, float y, double spawnTime)
	{

		super();

		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.healthImage)));

		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.PICKUP_WIDTH,
						 GameConfig.PICKUP_HEIGHT));

		addComponent(new Lifespan(
			GameConfig.PICKUP_HEALTHPACK_SPAWN_TIME, spawnTime));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PICKUP_COLLISION_BODY));
	}

	public CollectibleSet(Vector2f posVector, double spawnTime)
	{

		this(posVector.x, posVector.y, spawnTime);
	}
}
