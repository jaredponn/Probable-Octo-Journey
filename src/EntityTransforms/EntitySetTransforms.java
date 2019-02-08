package EntityTransforms;

import Components.*;
import poj.linear.*;
import poj.Render.Renderer;

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
	public static void pushRenderComponentToRenderer(Render r, Renderer ren)
	{
		ren.pushRenderObject(r.getGraphic());
	}

	public static void updateHasAnimationComponent(HasAnimation a,
						       long dtms)
	{
		a.updateAnimation(dtms);
	}

	public static Vector2f getVelocity(Direction d, Speed v)
	{
		return new Vector2f();
		// TODO
	}
}
