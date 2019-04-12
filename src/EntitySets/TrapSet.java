package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class TrapSet extends EntitySet
{

	public TrapSet(Vector2f position)
	{
		super();
		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.turret)));
		addComponent(new WorldAttributes(new Vector2f(position)));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.TURRET_COLLISION_BODY));
	}
}
