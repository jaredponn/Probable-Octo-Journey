package EntityTransforms;

import Components.*;
import poj.linear.*;

public class EntitySetTransforms
{
	public static boolean areCollisionBodiesColliding(CollisionBody a,
							  CollisionBody b)
	{

		for (Rectanglef i : a.getCollisionBodies()) {

			for (Rectanglef j : b.getCollisionBodies()) {
				if (Rectanglef.isColliding(i, j))
					return true;
			}
		}
		return false;
	}

	public static Vector2f getVelocity(Direction d, Speed v)
	{
		return new Vector2f();
		// TODO
	}
}
