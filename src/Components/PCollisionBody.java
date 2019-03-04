package Components;
import poj.Component.*;
import poj.linear.*;
import poj.Collisions.*;

public class PCollisionBody implements Component
{
	private Polygon p;
	private Vector2f displacement;

	/**
	 * Constructs a PCollisionBody object
	 *
	 * @param  d the displacemnt added to the object just
	 *         before setting its position
	 * @param  pts ... the collision body
	 */
	public PCollisionBody(Vector2f d, Vector2f... pts)
	{
		displacement = d;
		p = new Polygon(pts);
	}

	public PCollisionBody(PCollisionBody pb)
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
