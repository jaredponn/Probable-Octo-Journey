package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

/**
 * Health pack that heals the player on pick-up
 * @author Alex
 * @version 1.0 - 03/20/19
 */
public class HealthPack extends CollectibleSet
{

	public HealthPack(float x, float y)
	{

		super(x, y, GameConfig.PICKUP_COLLISION_BODY,
		      GameResources.healthImage);
	}

	public HealthPack(Vector2f posVector)
	{

		this(posVector.x, posVector.y);
	}
}
