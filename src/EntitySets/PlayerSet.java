package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class PlayerSet extends EntitySet
{

	public PlayerSet()
	{
		super();
		addComponent(new Velocity(10));
		addComponent(Direction.NORTH);
		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.playerSpriteSheet)));

		addComponent(new WorldAttributes(new Vector2f(50f, 50f),
						 GameResources.playerWidth,
						 GameResources.playerHeight));
		addComponent(
			new HasAnimation(GameResources.playerNIdleAnimation));
	}
}
