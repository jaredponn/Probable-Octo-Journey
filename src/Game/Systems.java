package Game;

/**
 * Systems -- transforms between or on a single component
 * Date: February 10, 2019
 * @author Jared Pon, Haiyang He, Romiro Piquer, Alex Stark
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.awt.Color;

import Components.*;

import poj.linear.*;
import poj.Render.RenderObject;
import poj.EngineState;
import poj.Collisions.*;
import poj.Render.ImageRenderObject;
import poj.Render.Renderer;
import poj.Render.RenderRect;
import poj.Collisions.*;

public class Systems
{
	public static void updateRenderScreenCoordinatesFromWorldCoordinates(
		WorldAttributes p, Render r, final Camera c)
	{
		Vector2f tmp = p.getTopLeftCoordFromOrigin();
		tmp.add(r.pureGetTranslation());
		tmp.matrixMultiply(c);

		r.setTopLeftCornerPosition(Math.round(tmp.getX()),
					   Math.round(tmp.getY()));
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

	public static void pushRenderComponentToQueue(Render r,
						      Queue<RenderObject> q)
	{
		q.add(r.getGraphic());
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

	public static void cullPushRenderComponentToQueue(Render r,
							  Queue<RenderObject> q,
							  int windowWidth,
							  int windowHeight)
	{
		ImageRenderObject tmp = r.getGraphic();
		if (tmp.getX() > windowWidth || tmp.getY() > windowHeight)
			return;

		if (tmp.getX() + tmp.getImageWindow().getWidth() < 0
		    || tmp.getY() + tmp.getImageWindow().getHeight() < 0)
			return;

		q.add(r.getGraphic());
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

	public static void steerMovementVelocityFromMovementDirection(
		Movement m, MovementDirection d, float steerRatio)
	{
		Vector2f a = d.getUnitVector();
		a.mul(m.getSpeed() * steerRatio);


		Vector2f b = m.getVelocity();

		Vector2f finalVel =
			a.pureAdd(b).safePureNormalize().pureMul(m.getSpeed());

		m.setVelocity(finalVel);
	}

	public static void
	updateWorldAttribPositionFromMovement(WorldAttributes w, Movement m,
					      double dtms)
	{
		Vector2f vel = new Vector2f(m.getVelocity());
		vel.mul((float)dtms);
		w.add(vel);
	}

	public static boolean
	arePCollisionBodiesColliding(GJK g, PCollisionBody a, PCollisionBody b)
	{
		g.clearVerticies();
		return g.areColliding(a.getPolygon(), b.getPolygon());
	}

	// faster form of just determining if collisions are colliding
	public static boolean arePCollisionBodiesColliding(GJK g,
							   PCollisionBody a,
							   PCollisionBody b,
							   Movement dv)
	{
		g.clearVerticies();
		return g.areColliding(a.getPolygon(), b.getPolygon(),
				      dv.getVelocity());
	}

	public static Optional<Double>
	pCollisionBodiesTimeOfCollision(GJK g, PhysicsPCollisionBody a,
					PhysicsPCollisionBody b, Movement dv,
					double dt)
	{
		g.clearVerticies();
		return g.upperBoundTimeOfPolygonCollision(
			a.getPolygon(), b.getPolygon(),
			dv.getDistanceDelta((float)dt));
	}

	public static Vector2f
	pCollisionBodiesGetCollisionBodyBDisplacementDelta(
		GJK g, PhysicsPCollisionBody a, PhysicsPCollisionBody b,
		Movement dv, double dt)
	{
		g.clearVerticies();
		return g.determineCollisionBodyBVector(
			a.getPolygon(), b.getPolygon(),
			dv.getDistanceDelta((float)dt));
	}


	public static void pCollisionBodyDebugRenderer(final PCollisionBody pc,
						       Queue<RenderObject> q,
						       final Camera cam)
	{
		pCollisionBodyDebugRenderer(pc, q, cam, Color.RED);
	}

	private static Color CENTER_DEBUG_COLOR = Color.GREEN;
	public static void pCollisionBodyDebugRenderer(final PCollisionBody pc,
						       Queue<RenderObject> q,
						       final Camera cam,
						       Color r)
	{
		Polygon p = pc.getPolygon();
		Vector2f[] pts = p.pts();

		for (int i = 0; i < p.getSize(); ++i) {
			final Vector2f sc = pts[i].pureMatrixMultiply(cam);
			q.add(new RenderRect((int)sc.x, (int)sc.y, 1, 1, r));
		}
		Vector2f center = pc.pureGetCenter().pureMatrixMultiply(cam);


		q.add(new RenderRect((int)center.x, (int)center.y, 1, 1,
				     CENTER_DEBUG_COLOR));
	}

	public static void
	updatePCollisionBodyPositionFromWorldAttr(PCollisionBody p,
						  WorldAttributes w)
	{
		Vector2f tmp = w.getOriginCoord();
		p.setPositionPoint(tmp);
	}


	private static float NUDGE_MULTIPLIER = 1.3f;
	public static void nudgeCollisionBodyBOutOfA(PCollisionBody a,
						     PCollisionBody b,
						     WorldAttributes bw, GJK g)
	{
		nudgeCollisionBodyBOutOfA(a.getPolygon(), b, bw, g);
	}

	public static void nudgeCollisionBodyBOutOfA(CollisionShape a,
						     PCollisionBody b,
						     WorldAttributes bw, GJK g)
	{
		final Vector2f tmp =
			g.calculatePenetrationVector(a, b.getPolygon());
		tmp.mul(NUDGE_MULTIPLIER); // nudge a little
					   // further so it easily
					   // goes outside of the
					   // box
		bw.add(tmp);

		Systems.updatePCollisionBodyPositionFromWorldAttr(b, bw);
	}


	public static void deleteAllComponentsAt(EngineState e, int i)
	{
		e.deleteAllComponentsAt(i);
		e.markIndexAsFree(i);
	}

	public static void damageIfPCollisionBodyIsCollidingWithSetPHitBoxBody(
		PCollisionBody pbody, PHitBox phbody, HitPoints hp, int amount)
	{
		GJK gjk = new GJK();

		if (gjk.areColliding(pbody.getPolygon(), phbody.getPolygon())) {
			hp.hurt(amount);
		}
	}
}
