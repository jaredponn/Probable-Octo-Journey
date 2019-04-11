package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

/**
 * Power-up to allow the player to do more damage
 * @author Alex
 * @version 1.0 - 03/18/19
 */
public class PowerUp extends CollectibleSet
{

	public PowerUp(float x, float y)
	{

		super(x, y, GameConfig.PICKUP_COLLISION_BODY,
		      GameResources.powerupImage);
	}

	public PowerUp(Vector2f posVector)
	{

		this(posVector.x, posVector.y);
	}
}
