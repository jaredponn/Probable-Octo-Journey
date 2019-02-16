package EntitySets;

import Components.*;    //include this
import poj.EntitySet.*; //include this
import Resources.GameConfig;

public class Bullet extends EntitySet
{
	public Bullet()
	{
		super();
		addComponent(new WorldAttributes(GameConfig.BULLET_WIDTH,
						 GameConfig.BULLET_HEIGHT));
		addComponent(new Speed(GameConfig.BULLET_SPEED));
		addComponent(new MovementDirection(CardinalDirections.N));
	}
}
