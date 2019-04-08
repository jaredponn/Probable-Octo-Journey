package Components;

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
	 * Constructs a PhysicsCollisionBody object that is used for collision
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

	public PCollisionBody(PCollisionBody pb)
	{
		displacement = pb.pureGetDisplacement();
		center = pb.pureGetCenter();
		p = pb.pureGetPolygon();
		gjk = new GJK();
	}

	public Vector2f pureGetDisplacement()
	{
		return new Vector2f(displacement);
	}

	public Vector2f getDisplacement()
	{
		return displacement;
	}

	public Vector2f pureGetCenter()
	{
		return new Vector2f(center);
	}

	public Vector2f getCenter()
	{
		return center;
	}

	public GJK getGJK()
	{
		return gjk;
	}

	public boolean isCollidingWith(PCollisionBody p)
	{
		this.gjk.clearVerticies();
		return this.gjk.areColliding(p.getPolygon(), this.getPolygon());
	}

	// d is this delta movement
	public boolean isCollidingWith(Vector2f d, PCollisionBody p)
	{
		this.gjk.clearVerticies();
		return this.gjk.areColliding(p.getPolygon(), this.getPolygon(),
					     d);
	}

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

	public Polygon getPolygon()
	{
		return p;
	}

	public Polygon pureGetPolygon()
	{
		return new Polygon(p);
	}


	public void print()
	{
		System.out.println("PCollisionBody: displacement: "
				   + displacement.toString() + " , center: "
				   + center.toString() + " , " + p.toString());
	}
}
