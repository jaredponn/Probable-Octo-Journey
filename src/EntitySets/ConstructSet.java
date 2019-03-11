package EntitySets;

/**
 * ConstructSet. ConstructSet -- UNSUED AND DEPREACTED
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

public class ConstructSet extends EntitySet
{
	// towers and traps
	// TODO: consider walls and resource harvesters?

	public ConstructSet()
	{

		super();

		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.playerSpriteSheet)));
		// TODO: Construct sprite sheet

		addComponent(new WorldAttributes(new Vector2f(50.f, 50.f),
						 GameConfig.CONSTRUCT_WIDTH,
						 GameConfig.CONSTRUCT_HEIGHT));

		// TODO: construct animations ( idle, attacking, dying)
	}
}
