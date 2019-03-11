package EntitySets;

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

	/**
	 * Constructor that places pick-up at a default location
	 * @param spawnTime: game time that the pick-up was spawned at
	 */
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

	/**
	 * Constructor that places pick-up at specified x,y coordinates
	 * @param x: x location of the pick-up
	 * @param y: y location of the pick-up
	 * @param spawnTime: game time that the pick-up was spawned at
	 */
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
