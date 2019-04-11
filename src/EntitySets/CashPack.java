package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

import java.awt.image.BufferedImage;

public class CashPack extends CollectibleSet
{
	public CashPack(float x, float y)
	{
		super(x, y, GameConfig.PICKUP_COLLISION_BODY,
		      GameResources.cashImage);
	}

	public CashPack(Vector2f n)
	{
		super(n.x, n.y, GameConfig.PICKUP_COLLISION_BODY,
		      GameResources.cashImage);
	}
}
