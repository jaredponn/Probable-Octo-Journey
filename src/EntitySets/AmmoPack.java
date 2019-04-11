package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

/**
 * Ammo pack that gives the player more bullets to shoot
 * @author Alex
 * @version 1.0 - 03/18/19
 */
public class AmmoPack extends CollectibleSet
{

	public AmmoPack(float x, float y)
	{

		super(x, y, GameConfig.PICKUP_COLLISION_BODY,
		      GameResources.ammoImage);
	}


	public AmmoPack(Vector2f n)
	{

		this(n.x, n.y);
	}
}
