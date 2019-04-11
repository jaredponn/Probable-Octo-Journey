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

import java.awt.image.BufferedImage;
/**
 * Health pick-up entity
 * @author Alex
 * 03/11/19
 */
public class CollectibleSet extends EntitySet
{
	public CollectibleSet(float x, float y, PCollisionBody pBody,
			      BufferedImage image)
	{

		super();

		addComponent(new Render(new ImageRenderObject(0, 0, image)));

		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.PICKUP_WIDTH,
						 GameConfig.PICKUP_HEIGHT));

		addComponent(new DespawnTimer(GameConfig.PICKUP_MAX_TIME));
		addComponent(new PhysicsPCollisionBody(pBody));
	}
}
