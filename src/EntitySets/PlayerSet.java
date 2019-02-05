package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class PlayerSet extends EntitySet
{

	public PlayerSet()
	{
		super();
		addComponent(new Speed(GameConfig.PLAYER_VELOCITY));
		// addComponent(Direction.N);
		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.playerSpriteSheet)));

		addComponent(new WorldAttributes(new Vector2f(50.f, 50.f),
						 GameConfig.PLAYER_WIDTH,
						 GameConfig.PLAYER_HEIGHT));
		addComponent(
			new HasAnimation(GameResources.playerNIdleAnimation));
	}
}
