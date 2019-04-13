package Components;
/**
 * PCollisionBody .
 * Date: March 10, 2019
 * @author Jared Pon
 * Version: 1.0
 */


import poj.Component.*;
import poj.linear.*;
import poj.Collisions.*;
public class PCollisionBody implements Component
{

	private Polygon p;
	private Vector2f displacement;
	private Vector2f center;
	private GJK gjk;

	/**
	 * Constructs a PCollisionBody object that is used for collision
	 * resolution
	 *
	 * @param  d the displacemnt added to the object just
	 *         before setting its position
	 *
	 * @param  c the center of collision body (do not also add the
	 *         displacement to the centers position.)
	 * @param  pts ... the collision body
	 */
	public PCollisionBody(Vector2f d, Vector2f c, Vector2f... pts)
	{
		displacement = new Vector2f(d);
		center = new Vector2f(c);
		p = new Polygon(pts);
		gjk = new GJK();
	}


	/**
	 * Copy constructor
	 *
	 * @param  pb to copy
	 */
	public PCollisionBody(PCollisionBody pb)
	{
		displacement = pb.pureGetDisplacement();
		center = pb.pureGetCenter();
		p = pb.pureGetPolygon();
		gjk = new GJK();
	}


	/**
	 * pure get displacement
	 *
	 * @return  displacement
	 */
	public Vector2f pureGetDisplacement()
	{
		return new Vector2f(displacement);
	}

	/**
	 * get displacement
	 *
	 * @return  displacement
	 */
	public Vector2f getDisplacement()
	{
		return displacement;
	}

	/**
	 * pure get center
	 *
	 * @return  center
	 */
	public Vector2f pureGetCenter()
	{
		return new Vector2f(center);
	}

	/**
	 * get center
	 *
	 * @return  center
	 */
	public Vector2f getCenter()
	{
		return center;
	}

	/**
	 * get gjk
	 *
	 * @return  gjk
	 */
	public GJK getGJK()
	{
		return gjk;
	}

	/**
	 * is colliding with
	 * @param p -- other collision body
	 * @return  boolean if colliding with
	 */
	public boolean isCollidingWith(PCollisionBody p)
	{
		return isCollidingWith(p.getPolygon());
	}

	/**
	 * is colliding with
	 * @param p -- other collision body
	 * @return  boolean if colliding with
	 */
	public boolean isCollidingWith(CollisionShape p)
	{
		this.gjk.clearVerticies();
		return this.gjk.areColliding(p, this.getPolygon());
	}

	/**
	 * is colliding with
	 * @param p -- other collision body
	 * @param d -- delta vector
	 * @return  boolean if colliding with
	 */
	public boolean isCollidingWith(Vector2f d, PCollisionBody p)
	{
		this.gjk.clearVerticies();
		return this.gjk.areColliding(p.getPolygon(), this.getPolygon(),
					     d);
	}

	/**
	 * Calculating penetration vector
	 * @return  Vector2f of the normal
	 */
	public Vector2f calculateThisPenetrationVector()
	{
		return this.gjk.calculatePenetrationVector();
	}

	/**
	 * Sets the position point. Adds the displacement, then calls the
	 * setFirstPointAndShiftAllPoints method of a polygon, and moves the
	 * center accordingly.
	 *
	 * @param  n the new point
	 * @return   void
	 */
	public void setPositionPoint(Vector2f n)
	{

		Vector2f tmp =
			p.setFirstPositionAndShiftAll(n.pureAdd(displacement));
		center.add(tmp);
	}


	/**
	 * gets polygon
	 * @return  Polygon
	 */
	public Polygon getPolygon()
	{
		return p;
	}

	/**
	 * pure gets polygon
	 * @return  Polygon
	 */
	public Polygon pureGetPolygon()
	{
		return new Polygon(p);
	}


	/**
	 * print
	 */
	public void print()
	{
		System.out.println("PCollisionBody: displacement: "
				   + displacement.toString() + " , center: "
				   + center.toString() + " , " + p.toString());
	}
}
