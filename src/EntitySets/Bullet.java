package EntitySets;

import poj.EntitySet.EntitySet;
import Components.*;

import Components.Render;

import Resources.GameConfig;
import Resources.GameResources;


public class Bullet extends EntitySet
{
	public Bullet()
	{
		super();
		addComponent(new Render(GameResources.bulletImage));
		addComponent(new WorldAttributes(GameConfig.BULLET_WIDTH,
						 GameConfig.BULLET_HEIGHT));
		addComponent(new Speed(GameConfig.BULLET_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
	}
}
