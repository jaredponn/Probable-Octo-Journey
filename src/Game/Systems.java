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

	/**
	 * updates render screen coords from world coord
	 * @param p: World attributes
	 * @param r: render
	 * @param c: camera
	 */
	public static void updateRenderScreenCoordinatesFromWorldCoordinates(
		WorldAttributes p, Render r, final Camera c)
	{
		Vector2f tmp = p.getTopLeftCoordFromOrigin();
		tmp.add(r.pureGetTranslation());
		tmp.matrixMultiply(c);

		r.setTopLeftCornerPosition(Math.round(tmp.getX()),
					   Math.round(tmp.getY()));
	}

	/**
	 * get render screen coordinte from world coordinates
	 * @param p: World attributes
	 * @param c: camera
	 */
	public static Vector2f
	getRenderScreenCoordinateFromWorldCoordinate(final WorldAttributes p,
						     final Camera c)
	{
		Vector2f topleftcoord = p.getTopLeftCoordFromOrigin();
		topleftcoord.matrixMultiply(c);
		return topleftcoord;
	}


	/**
	 * push render component to queue
	 * @param r: render object
	 * @param q: list
	 */
	public static void pushRenderComponentToList(Render r,
						     ArrayList<RenderObject> q)
	{
		q.add(new ImageRenderObject(r.getGraphic()));
	}


	/**
	 * Cull and pushes the render component yo renderer
	 * @param r: render object
	 * @param ren: renderer
	 * @param windowWidth: window width
	 * @param windowHeight: window height
	 */
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

		ren.pushRenderObject(new ImageRenderObject(r.getGraphic()));
	}


	/**
	 * Cull and pushes the render component yo renderer
	 * @param r: render object
	 * @param q: Arraylist
	 * @param windowWidth: window width
	 * @param windowHeight: window height
	 */
	public static void cullPushRenderComponent(Render r,
						   ArrayList<RenderObject> q,
						   int windowWidth,
						   int windowHeight)
	{
		ImageRenderObject tmp = r.getGraphic();

		q.add(new ImageRenderObject(r.getGraphic()));
	}


	/**
	 * updates render component animation window from has animation
	 * @param r: render object
	 * @param a: animation
	 */
	public static void
	updateRenderComponentWindowFromHasAnimation(Render r, HasAnimation a)
	{
		r.setImageWindow(a.getImageWindow());
	}

	/**
	 * updates has animation component
	 * @param a: animation
	 * @param dtms: delta time in ms
	 */
	public static void updateHasAnimationComponent(HasAnimation a,
						       double dtms)
	{
		a.updateAnimation(dtms);
	}


	/**
	 * set movement velocity form direction
	 * @param m: movement
	 * @param d: movement direction
	 */
	public static void
	setMovementVelocityFromMovementDirection(Movement m,
						 MovementDirection d)
	{
		Vector2f tmp = d.getUnitVector();
		tmp.mul(m.getSpeed());
		m.setVelocity(tmp);
	}


	/**
	 * steers movement velocity from direction
	 * @param m: movement
	 * @param d: movement direction
	 * @param steerRatio: steering ratio -- should be between 0 and 1
	 */
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

	/**
	 * updates world attrib position from movement
	 * @param w: World attributes
	 * @param m: movement
	 * @param dtms: delta time in ms
	 */
	public static void
	updateWorldAttribPositionFromMovement(WorldAttributes w, Movement m,
					      double dtms)
	{
		Vector2f vel = new Vector2f(m.getVelocity());
		vel.mul((float)dtms);
		w.add(vel);
	}

	/**
	 * checks if collision bodies are colliding
	 * @param a: collision body a
	 * @param b: collision body b
	 */
	public static boolean
	arePCollisionBodiesColliding(GJK g, PCollisionBody a, PCollisionBody b)
	{
		g.clearVerticies();
		return g.areColliding(a.getPolygon(), b.getPolygon());
	}

	/**
	 * checks if collision bodies are colliding
	 * @param a: collision body a
	 * @param b: collision body b
	 * @param dv: delta vector
	 */
	public static boolean arePCollisionBodiesColliding(GJK g,
							   PCollisionBody a,
							   PCollisionBody b,
							   Movement dv)
	{
		g.clearVerticies();
		return g.areColliding(a.getPolygon(), b.getPolygon(),
				      dv.getVelocity());
	}


	/**
	 * debug renderer for collision bodies
	 * @param pc: collision body
	 * @param q: render buffer
	 * @param c: camera
	 */
	public static void
	pCollisionBodyDebugRenderer(final PCollisionBody pc,
				    ArrayList<RenderObject> q, final Camera cam)
	{
		pCollisionBodyDebugRenderer(pc, q, cam, Color.RED);
	}

	/**
	 * debug renderer for collision bodies
	 * @param pc: collision body
	 * @param q: render buffer
	 * @param c: camera
	 * @param r: color
	 */
	private static Color CENTER_DEBUG_COLOR = Color.GREEN;
	public static void
	pCollisionBodyDebugRenderer(final PCollisionBody pc,
				    ArrayList<RenderObject> q, final Camera cam,
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

	/**
	 * updates collsion body from world attributes
	 * @param pc: collision body
	 * @param q: render buffer
	 * @param c: camera
	 * @param r: color
	 */
	public static void
	updatePCollisionBodyPositionFromWorldAttr(PCollisionBody p,
						  WorldAttributes w)
	{
		Vector2f tmp = w.getOriginCoord();
		p.setPositionPoint(tmp);
	}


	/**
	 * nudges collision bodies outside of each other
	 * @param a: collision body a
	 * @param b: collision body b
	 * @param bw: b's world attributes
	 * @param g: gjk
	 */
	private static float NUDGE_MULTIPLIER = 1.3f;
	public static void nudgeCollisionBodyBOutOfA(PCollisionBody a,
						     PCollisionBody b,
						     WorldAttributes bw, GJK g)
	{
		nudgeCollisionBodyBOutOfA(a.getPolygon(), b, bw, g);
	}

	/**
	 * nudges collision bodies outside of each other
	 * @param a: collision body a
	 * @param b: collision body b
	 * @param bw: b's world attributes
	 * @param g: gjk
	 */
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


	/**
	 * deletes all components
	 * @param e: engine state
	 * @param i: entity
	 */
	public static void deleteAllComponentsAt(EngineState e, int i)
	{
		e.deleteAllComponentsAt(i);
		e.markIndexAsFree(i);
	}

	/**
	 * damages if collding
	 * @param pbody: body a
	 * @param phbody: body b
	 * @param hp: hitpoints
	 * @param amount: amount to damage
	 */
	public static void damageIfPCollisionBodyIsCollidingWithSetPHitBoxBody(
		PCollisionBody pbody, PHitBox phbody, HitPoints hp, int amount)
	{
		GJK gjk = new GJK();

		if (gjk.areColliding(pbody.getPolygon(), phbody.getPolygon())) {
			hp.hurt(amount);
		}
	}
}
