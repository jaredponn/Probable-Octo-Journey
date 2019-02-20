package Game;

import java.util.ArrayList;
import java.util.Optional;
import java.awt.Color;

import Components.*;

import poj.linear.*;
import poj.Collisions.*;
import poj.Render.ImageRenderObject;
import poj.Render.Renderer;
import poj.Render.RenderRect;

public class Systems
{

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

	public static Optional<Double>
	calcTimeForCollision(final CollisionBoxBody a, final CollisionBoxBody b,
			     Vector2f va, Vector2f vb)
	{
		for (CollisionBox i : a.getCollisionBodies()) {
			for (CollisionBox j : b.getCollisionBodies()) {
				final Optional<Double> tmp =
					CollisionBox.intersectionTimeOfMoving(
						i, j, va, vb);
				if (tmp.isPresent()) {
					return tmp;
				}
			}
		}
		return Optional.empty();
	}

	public static boolean
	areCollisionBodiesCollding(final CollisionBoxBody a,
				   final CollisionBoxBody b)
	{
		for (CollisionBox i : a.getCollisionBodies()) {
			for (CollisionBox j : b.getCollisionBodies()) {
				if (CollisionBox.isColliding(i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void
	updateCollisionBoxBodyTopLeftFromWorldAttributes(CollisionBoxBody a,
							 WorldAttributes w)
	{
		a.setCollisionBodyPositionFromTopLeft(
			w.getTopLeftCoordFromOrigin());
	}

	public static void debugCollisionRenderPush(final CollisionBoxBody c,
						    Renderer r,
						    final Camera cam)
	{

		ArrayList<CollisionBox> arr = c.pureGetCollisionBodies();


		for (int i = 0; i < arr.size(); ++i) {
			final CollisionBox cb = arr.get(i);

			for (int j = 0; j < CollisionBox.NUM_POINTS; ++j) {
				Vector2f tmp = cb.pureGetPoints()[j]
						       .pureMatrixMultiply(cam);
				r.pushRenderObject(new RenderRect(
					(int)tmp.x, (int)tmp.y, 1, 1));
			}

			Vector2f smin = cb.min().pureMatrixMultiply(cam);
			r.pushRenderObject(new RenderRect(
				(int)smin.x, (int)smin.y, 2, 2, Color.YELLOW));


			Vector2f smax = cb.max().pureMatrixMultiply(cam);
			r.pushRenderObject(new RenderRect(
				(int)smax.x, (int)smax.y, 2, 2, Color.pink));
		}
	}
}
