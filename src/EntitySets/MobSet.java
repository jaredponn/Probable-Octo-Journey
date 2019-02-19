package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class MobSet extends EntitySet
{

	public MobSet()
	{
		super();

		addComponent(new Render(new ImageRenderObject(
			0, 0, GameResources.enemySpriteSheet)));
		addComponent(new WorldAttributes(new Vector2f(0f, 0f),
						 GameConfig.MOB_WIDTH,
						 GameConfig.MOB_HEIGHT));

		addComponent(
			new HasAnimation(GameResources.enemyNMoveAnimation));
		// addComponent(new Movement(GameConfig.MOB_VELOCITY));
		addComponent(new Movement(0f));
		addComponent(new MovementDirection(CardinalDirections.N));
		addComponent(new FacingDirection(CardinalDirections.N));

		// TODO: mob sprite sheet


		//
		// TODO: make mobs a different size than the player?

		// TODO: mob animations (walking, idle, attacking, dying)
	}
}
