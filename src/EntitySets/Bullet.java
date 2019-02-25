package EntitySets;

import poj.EntitySet.EntitySet;
import Components.*;

import Components.Render;

import Resources.GameConfig;
import Resources.GameResources;


public class Bullet extends EntitySet
{
	public Bullet( double spawnTime )
	{
		super();
		addComponent(new Render(GameResources.bulletImage));
		addComponent(new WorldAttributes(GameConfig.BULLET_WIDTH,
						 GameConfig.BULLET_HEIGHT));
		addComponent(new Movement(GameConfig.BULLET_SPEED));
		addComponent(new Lifespan( GameConfig.BULLET_LIFE_SPAN , spawnTime));
	}
}
