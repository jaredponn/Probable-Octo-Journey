package Game;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.awt.Color;

import Components.*;

import poj.linear.*;
import poj.Render.RenderObject;
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

	public static void
	updateWorldAttribPositionFromMovement(WorldAttributes w, Movement m,
					      double dtms)
	{
		Vector2f vel = new Vector2f(m.getVelocity());
		vel.mul((float)dtms);
		w.add(vel);
	}

	public static boolean
	arePCollisionBodiesColliding(GJK g, PhysicsPCollisionBody a,
				     PhysicsPCollisionBody b)
	{
		g.clearVerticies();
		return g.areColliding(a.getPolygon(), b.getPolygon());
	}

	// faster form of just determining if collisions are colliding
	public static boolean
	arePCollisionBodiesColliding(GJK g, PhysicsPCollisionBody a,
				     PhysicsPCollisionBody b, Movement dv)
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
		return g.timeOfPolygonCollision(a.getPolygon(), b.getPolygon(),
						dv.getDistanceDelta((float)dt));
	}


	public static void
	pCollisionBodyDebugRenderer(final PhysicsPCollisionBody pc,
				    Queue<RenderObject> q, final Camera cam)
	{
		pCollisionBodyDebugRenderer(pc, q, cam, Color.RED);
	}

	public static void
	pCollisionBodyDebugRenderer(final PhysicsPCollisionBody pc,
				    Queue<RenderObject> q, final Camera cam,
				    Color r)
	{
		Polygon p = pc.getPolygon();
		Vector2f[] pts = p.pts();

		for (int i = 0; i < p.getSize(); ++i) {
			final Vector2f sc = pts[i].pureMatrixMultiply(cam);
			q.add(new RenderRect((int)sc.x, (int)sc.y, 2, 2, r));
		}
	}

	public static void
	updatePCollisionBodyPositionFromWorldAttr(PhysicsPCollisionBody p,
						  WorldAttributes w)
	{
		Vector2f tmp = w.getOriginCoord();
		p.setPositionPoint(tmp);
	}
}
