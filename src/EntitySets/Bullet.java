package EntitySets;

import poj.EntitySet.EntitySet;
import Components.*;
import poj.linear.*;

import Components.Render;

import Resources.GameConfig;
import Resources.GameResources;


public class Bullet extends EntitySet
{
	public Bullet(double spawnTime)
	{
		super();
		addComponent(new Render(GameResources.bulletImage));
		addComponent(new WorldAttributes(GameConfig.BULLET_WIDTH,
						 GameConfig.BULLET_HEIGHT));
		addComponent(new Movement(GameConfig.BULLET_SPEED));
		addComponent(
			new Lifespan(GameConfig.BULLET_LIFE_SPAN, spawnTime));
		addComponent(
			new PCollisionBody(GameConfig.BULLET_COLLISION_BODY));
	}
	
	public Bullet(double spawnTime , Vector2f posVector)
	{
		super();
		addComponent(new Render(GameResources.bulletImage));
		addComponent(new WorldAttributes(posVector , GameConfig.BULLET_WIDTH,
						 GameConfig.BULLET_HEIGHT));
		System.out.println("Bullet spawned at "+ posVector.toString());
		addComponent(new Movement(GameConfig.BULLET_SPEED));
		addComponent(
			new Lifespan(GameConfig.BULLET_LIFE_SPAN, spawnTime));
		addComponent(
			new PCollisionBody(GameConfig.BULLET_COLLISION_BODY));
	}
}
