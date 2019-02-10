package EntityTransforms;

import Components.*;
import poj.linear.*;
import poj.Render.Renderer;

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

	public static void updatePositions(WorldAttributes p, Render r)
	{
		Vector2f topleftcoord = p.getTopLeftCoordFromOrigin();
		r.setTopLeftCornerPosition(Math.round(topleftcoord.getX()),
					   Math.round(topleftcoord.getY()));
	}

	public static void pushRenderComponentToRenderer(Render r, Renderer ren)
	{
		ren.pushRenderObject(r.getGraphic());
	}

	public static void
	updateRenderComponentWindowFromHasAnimation(Render r, HasAnimation a)
	{
		r.setImageWindow(a.getImageWindow());
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
