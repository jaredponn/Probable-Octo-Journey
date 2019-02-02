package EntityTransforms;

import Components.*;
import poj.linear.Rectanglef;

public class EntitySetTransforms
{

	public static void printPhysics(Physics p)
	{
		System.out.println("physics print: " + p.a);
	}

	public static void incrementPhysics(Physics p)
	{
		p.a += 1;
	}

	public static void printRender(Render r)
	{
		System.out.println("redner print " + r);
	}


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
}
