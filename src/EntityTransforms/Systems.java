package EntityTransforms;

import Components.*;
import Game.Camera;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import poj.Render.Renderer;
import java.util.ArrayList;

public class Systems
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

	/*
	public static boolean isValidCord(WorldAttributes tile, int mapWidth,
					  int mapHeight)
	{
		Vector2f cord = tile.getOriginCoord();
		return !(cord.x >= mapHeight || cord.y >= mapWidth)
			&& (cord.x >= 0 && cord.y >= 0);
	}

	public static boolean isValidCord(PathFindCord tile, int mapWidth,
					  int mapHeight)
	{
		MatrixCord cord = tile.getCord();
		return !(cord.row >= mapHeight || cord.col >= mapWidth)
			&& (cord.row >= 0 && cord.col >= 0);
	}
	*/

	public static void updateRenderScreenCoordinatesFromWorldCoordinates(
		WorldAttributes p, Render r, final Camera c)
	{
		Vector2f tmp =
			Systems.getRenderScreenCoordinateFromWorldCoordinate(p,
									     c);
		r.setTopLeftCornerPosition(Math.round(tmp.getX()),
					   Math.round(tmp.getY()));
		r.addTranslation();
	}

	public static Vector2f
	getRenderScreenCoordinateFromWorldCoordinate(final WorldAttributes p,
						     final Camera c)
	{
		Vector2f topleftcoord = p.getTopLeftCoordFromOrigin();
		topleftcoord.matrixMultiply(c);
		return topleftcoord;
	}

	public static void pushRenderComponentToRenderer(Render r, Renderer ren)
	{
		ren.pushRenderObject(r.getGraphic());
	}

	public static void cullPushRenderComponentToRenderer(Render r,
							     Renderer ren,
							     int windowWidth,
							     int windowHeight)
	{
		ImageRenderObject tmp = r.getGraphic();
		if (tmp.getX() > windowWidth || tmp.getY() > windowHeight)
			return;

		if (tmp.getX() + tmp.getImageWindow().getWidth() < 0
		    || tmp.getY() + tmp.getImageWindow().getHeight() < 0)
			return;

		ren.pushRenderObject(r.getGraphic());
	}

	public static void
	updateRenderComponentWindowFromHasAnimation(Render r, HasAnimation a)
	{
		r.setImageWindow(a.getImageWindow());
	}

	public static void updateHasAnimationComponent(HasAnimation a,
						       double dtms)
	{
		a.updateAnimation(dtms);
	}


	public static void
	setMovementVelocityFromMovementDirection(Movement m,
						 MovementDirection d)
	{
		Vector2f tmp = d.getUnitVector();
		tmp.mul(m.getSpeed());
		m.setVelocity(tmp);
	}

	public static void
	updateWorldAttribPositionFromMovement(WorldAttributes w, Movement m,
					      double dtms)
	{
		Vector2f vel = new Vector2f(m.getVelocity());
		vel.mul((float)dtms);
		w.add(vel);
	}
}
