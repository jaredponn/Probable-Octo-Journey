package EntityTransforms;

import Components.*;
import Game.Camera;
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

	public static void updateRenderScreenCoordinatesFromWorldCoordinates(
		WorldAttributes p, Render r, final Camera c)
	{
		Vector2f topleftcoord = p.getTopLeftCoordFromOrigin();
		topleftcoord.matrixMultiply(c);
		topleftcoord.log();
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
	}
}
