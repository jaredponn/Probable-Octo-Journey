package Components;
import poj.Component.*;
import poj.linear.*;
import poj.Collisions.*;

public class PhysicsPCollisionBody implements Component
{
	private Polygon p;
	private Vector2f displacement;

	/**
	 * Constructs a PCollisionBody object that is used for collision
	 * resolution
	 *
	 * @param  d the displacemnt added to the object just
	 *         before setting its position
	 * @param  pts ... the collision body
	 */
	public PhysicsPCollisionBody(Vector2f d, Vector2f... pts)
	{
		displacement = d;
		p = new Polygon(pts);
	}

	public PhysicsPCollisionBody(PhysicsPCollisionBody pb)
	{
		displacement = pb.pureGetDisplacement();
		p = pb.pureGetPolygon();
	}

	protected Vector2f pureGetDisplacement()
	{
		return new Vector2f(displacement);
	}

	/**
	 * Sets the position point. Adds the displacement, then calls the
	 * setFirstPointAndShiftAllPoints method of a polygon
	 *
	 * @param  n the new point
	 * @return   void
	 */
	public void setPositionPoint(Vector2f n)
	{
		p.setFirstPositionAndShiftAll(n.pureAdd(displacement));
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
				   + displacement.toString() + " , "
				   + p.toString());
	}
}
