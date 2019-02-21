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
	calcTimeForCollisionForAabbBodies(final CollisionAabbBodies a,
					  final CollisionAabbBodies b,
					  Vector2f va, Vector2f vb)
	{
		for (CollisionAabb i : a.getCollisionBodies()) {
			for (CollisionAabb j : b.getCollisionBodies()) {
				final Optional<Double> tmp =
					CollisionAabb.intersectionTimeOfMoving(
						i, j, va, vb);
				if (tmp.isPresent()) {
					return tmp;
				}
			}
		}
		return Optional.empty();
	}

	public static boolean
	areAabbCollisionBodiesColliding(final CollisionAabbBodies a,
					final CollisionAabbBodies b)
	{
		for (CollisionAabb i : a.getCollisionBodies()) {
			for (CollisionAabb j : b.getCollisionBodies()) {
				if (CollisionAabb.isColliding(i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void updateAabbCollisionBodiesTopLeftFromWorldAttributes(
		CollisionAabbBodies a, WorldAttributes w)
	{
		a.setCollisionBodyPositionFromTopLeft(
			w.getBottomRightCoordFromOrigin());
	}

	public static void
	aabbCollisionBodiesDebugRender(final CollisionAabbBodies c, Renderer r,
				       final Camera cam)
	{

		ArrayList<CollisionAabb> arr = c.pureGetCollisionBodies();


		for (int i = 0; i < arr.size(); ++i) {
			final CollisionAabb cb = arr.get(i);

			for (int j = 0; j < CollisionAabb.NUM_POINTS; ++j) {
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


	public static void
	updateCircleCollisionFromWorldAttributes(CircleCollisionBody c,
						 final WorldAttributes w)
	{
		c.setCollisionCircleCenter(w.getOriginCoord());
	}

	public static boolean
	areCollisionCirclesColliding(CircleCollisionBody a,
				     CircleCollisionBody b)
	{
		return CollisionTests.areCirclesColliding(
			a.getCollisionCircle(), b.getCollisionCircle());
	}

	public static void
	circleCollisionDebugRenderer(final CircleCollisionBody a, Renderer r,
				     final Camera cam)
	{

		CollisionCircle cc = a.getCollisionCircle();
		Vector2f center = cc.c();
		Vector2f perimeters[] = new Vector2f[12];

		for (int i = 0; i < 12; ++i) {
			final float j = (float)i * (float)Math.PI / 4f;
			perimeters[i] =
				new Vector2f((float)(cc.r() * Math.cos(j)
						     + center.x),
					     (float)(cc.r() * Math.sin(j)
						     + center.y))
					.pureMatrixMultiply(cam);
		}

		Vector2f centerScreenCoord = center.pureMatrixMultiply(cam);

		r.pushRenderObject(new RenderRect((int)centerScreenCoord.x,
						  (int)centerScreenCoord.y, 2,
						  2, Color.YELLOW));
		for (int i = 0; i < 12; ++i) {
			r.pushRenderObject(new RenderRect((int)perimeters[i].x,
							  (int)perimeters[i].y,

							  2, 2, Color.RED));
		}
	}
}
