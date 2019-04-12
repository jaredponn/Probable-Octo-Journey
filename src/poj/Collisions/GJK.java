package poj.Collisions;
/**
 * GJK -- implementation of the GJK algorthim with penetration vector
 * calculations using EPA.
 *
 * Stateful compution to determine if convex polygons are colliding. Supports
 * moving convex polygons, time of collision of moving convex polygons, and
 * calculating the penetration vector of 2 colliding convex polygons.
 *
 * Also, my apologies this API is a little messy -- my first collision engine
 *i've written so I did not really know the architecture or algorthims going in
 * so feel free to ask me questions with regards of how to use it.
 *
 * Other useful readings on collision resolution:
 * https://stackoverflow.com/questions/36232613/how-can-one-have-right-movement-into-diagonal-collision-boxes-giving-diagonal-mo
 *
 * Other notes on the implementing the full collision engine:
 * n cross dv cross n == perpendicular vector
 *
 * Date: March 10, 2019
 * @author  Jared Pon and code was taken / translated from / heavily influenced
 * from the following links:
 * https://caseymuratori.com/blog_0003
 * http://www.dyn4j.org/2010/04/gjk-gilbert-johnson-keerthi/
 * https://blog.hamaluik.ca/posts/building-a-collision-engine-part-1-2d-gjk-collision-detection/
 * https://blog.hamaluik.ca/posts/building-a-collision-engine-part-2-2d-penetration-vectors/
 * http://www.dyn4j.org/2010/05/epa-expanding-polytope-algorithm/
 * https://github.com/hamaluik/headbutt/blob/3985a0a39c77a9539fad2383c84f5d448b4e87ae/src/headbutt/twod/Headbutt.hx
 * https://github.com/hamaluik/headbutt/blob/master/src/headbutt/twod/Headbutt.hx
 * @version  1.00
 */

import poj.linear.*;
import poj.Pair;
import poj.Logger.*;

import java.util.ArrayList;
import java.util.Optional;


public class GJK
{

	public ArrayList<Vector2f> verticies = new ArrayList<Vector2f>(3);
	public Vector2f direction;

	public CollisionShape collisionShapeA;
	public CollisionShape collisionShapeB;

	/**
	 *  constructor
	 */
	public GJK()
	{
		this.clearVerticies();
	}

	/**
	 *  clears the verticies
	 */
	public void clearVerticies()
	{
		verticies.clear();
	}

	/**
	 * Tests if things are colliding. Ensure "clearVerticies" is run before
	 * using this function
	 *
	 * @param  cola first static (stationary)collision box
	 * @param  colb second collision box that is moving with the delta
	 *         specified with db
	 * @return      boolean to see if they are colliding
	 */
	private static int MAX_ITERATIONS = 10;
	public boolean areColliding(final CollisionShape cola,
				    final CollisionShape colb)
	{
		// verticies.clear();
		collisionShapeA = cola;
		collisionShapeB = colb;

		EvolveResult evl = EvolveResult.STILL_EVOLVING;

		int i = 0;

		while (evl == EvolveResult.STILL_EVOLVING
		       && i < MAX_ITERATIONS) {

			evl = evolveSimplex();
			Vector2f a = support(cola, colb);
			verticies.add(a);

			if (Vector2f.dot(a, direction) < 0)
				return false;
			++i;
		}

		return evl == EvolveResult.FOUND_INTERSECTION;
	}


	/**
	 * Tests if things are colliding, where colb's movement is given by db .
	 * Ensure "clearVerticies" is run before using this function.
	 *
	 * @param  cola first static (stationary)collision box
	 * @param  colb second collision box that is moving with the delta
	 *         specified with db
	 * @param  db delta of the second collision box
	 * @return      boolean to see if they are colliding
	 */
	public boolean areColliding(final Polygon cola, final Polygon colb,
				    final Vector2f db)
	{
		final Polygon p =
			generateStretchedPolygonWithDirectionVector(colb, db);

		return this.areColliding(cola, p);
	}


	/**
	 * Calculates the pentration vector of 2 colliding CollisionShapes --
	 * the penetration vector returned is the how much to shift "colb" --
	 * ensure that areColliding is run before this so that there is a
	 * simplex generated.
	 *
	 * @param  cola first static (stationary) collision box
	 * @param  colb second collision box that is moving with the delta
	 *         specified with db
	 * @return      Vector2f -- shortest shift of colb out of cola
	 */

	private static final int MAX_PENETRATION_VEC_ITERATIONS = 10;
	public Vector2f calculatePenetrationVector(final CollisionShape cola,
						   final CollisionShape colb)
	{

		Vector2f penVector = new Vector2f(0, 0);

		// while true
		for (int i = 0; i < MAX_PENETRATION_VEC_ITERATIONS; ++i) {
			Edge e = findClosestEdgeToOriginOnSimplex(
				PolygonWinding.CW);

			Vector2f support =
				calculateSupport(cola, colb, e.normal);

			float dist = support.dot(e.normal);

			penVector = e.normal.pureMul(dist);

			if (Math.abs(dist - e.distance) <= EPSILON) {
				// found edge closest to origin
				break;
			} else {
				// there is an edge closer to origin
				verticies.add(e.index, support);
			}
		}

		return penVector;
	}

	/**
	 *  calculate penetration vector of focused collision shapes
	 *  @return penetration vector
	 */
	public Vector2f calculatePenetrationVector()
	{
		return calculatePenetrationVector(collisionShapeA,
						  collisionShapeB);
	}


	/**
	 *  finds the closest edge to the origin on the simplex
	 *  @return winding -- polygon winding -- optimization for 2D
	 */
	private Edge findClosestEdgeToOriginOnSimplex(PolygonWinding winding)
	{

		Edge edge = new Edge();

		edge.distance = Float.MAX_VALUE;

		// iterate through simplex edges i -> j wise
		for (int i = 0; i < this.verticies.size(); ++i) {

			int j = (i + 1 >= verticies.size())
					? 0
					: i + 1; // wind j back to 0
			final Vector2f a = verticies.get(i);
			final Vector2f b = verticies.get(j);

			// edge vector
			final Vector2f ab = b.pureSubtract(a);
			// origin to a
			final Vector2f oa = a;

			// edge normal towards origin
			// final Vector2f n = Vector2f.pureTripleProduct(ab, oa,
			// ab);
			final Vector2f n;
			switch (winding) {
			case CW:
				n = new Vector2f(ab.y, -ab.x);
				break;

			case CCW:
				n = new Vector2f(-ab.y, ab.x);
				break;
			default:
				n = new Vector2f(-ab.y, ab.x);
				Logger.lassert(
					"Error in findClosestEdgeToOriginOnSimplex -- Java is such a bad language that it doesn't understand that all enum values are the domain of the type.");
				break;
			}

			n.normalize();

			// distance from origin to edge (via projection)
			float dist = n.dot(oa);

			if (dist < edge.distance) {
				edge.distance = dist;
				edge.normal = n;
				edge.index = j;
			}
		}


		return edge;
	}

	/**
	 * Generates the stretched polygon from a vector (used for collision
	 * detection without tunneling)
	 * @param p : polygon to stretch
	 * @param d : vector to stretch by
	 * @return stretched polygon
	 */
	private Polygon
	generateStretchedPolygonWithDirectionVector(final Polygon p,
						    final Vector2f d)
	{
		Vector2f[] npts = new Vector2f[p.size() * 2];

		for (int i = 0; i < p.size(); ++i) {
			npts[i] = new Vector2f(p.pts()[i]);
		}

		for (int i = p.size(); i < 2 * p.size(); ++i) {
			npts[i] = (p.pts()[i - p.size()]).pureAdd(d);
		}
		Polygon np = new Polygon();
		np.size = p.size() * 2;
		np.pts = npts;
		return np;
	}


	/**
	 * support function
	 * @param a : collision shape a
	 * @param b : collision shape b
	 * @param d : direction to calcalute support in
	 * @return support vector
	 */
	private Vector2f support(final CollisionShape a, final CollisionShape b)
	{
		return GJK.calculateSupport(a, b, direction);
	}

	/**
	 * calculates the support
	 * @param a : collision shape a
	 * @param b : collision shape b
	 * @param d : direction to calcalute support in
	 * @return support vector
	 */
	public static Vector2f calculateSupport(final CollisionShape a,
						final CollisionShape b,
						Vector2f d)
	{
		final Vector2f pa = a.furthestPointInDirection(d);
		final Vector2f pb = b.furthestPointInDirection(d.pureNegate());
		Vector2f tmp = pa.pureSubtract(pb);
		return tmp;
	}


	/**
	 * evolves the simplex
	 */
	private EvolveResult evolveSimplex()
	{
		switch (verticies.size()) {
		case 0: {
			direction = new Vector2f(1,
						 0); // default search direction
			break;
		}

		case 1: {
			// verticies: [a]
			Vector2f a = verticies.get(0); // point just added
			direction = a.pureNegate();
			break;
		}

		case 2: {
			// verticies: [b, a]
			Vector2f a = verticies.get(1); // point just added
			Vector2f b = verticies.get(0);

			Vector2f ab = b.pureSubtract(a);
			Vector2f ao = a.pureNegate();

			direction = Vector2f.pureTripleProduct(
				ab, ao,
				ab); // perpendicular vector to ab

			break;
		}

		case 3: {

			// verticies: [c, b, a]
			Vector2f a = verticies.get(2);
			Vector2f b = verticies.get(1);
			Vector2f c = verticies.get(0);

			Vector2f ao = a.pureNegate();
			Vector2f ac = c.pureSubtract(a);
			Vector2f ab = b.pureSubtract(a);

			Vector2f acnorm =
				Vector2f.pureTripleProduct(ab, ac, ac);
			Vector2f abnorm =
				Vector2f.pureTripleProduct(ac, ab, ab);


			if (Vector2f.dot(ao, acnorm) > 0
			    && Vector2f.dot(ao, abnorm)
				       > 0) // upper voronoi region
			{
				// removes c, b
				verticies.remove(0);
				verticies.remove(0);

				direction = ao;
			} else if (Vector2f.dot(ao, acnorm) > 0) // outside ac
			{
				verticies.remove(1); // removes b
				direction = acnorm;
			} else if (Vector2f.dot(ao, abnorm) > 0) // outside ab
			{
				verticies.remove(0); // removes c
				direction = abnorm;
			} else {
				return EvolveResult.FOUND_INTERSECTION;
			}
			break;
		}

		default:
			Logger.lassert(
				"ERROR in GJK -- simplexes of size 2 and 3 are only supported -- perhaps you forgot to run the clearVertices function inbetween areColliding");
			break;
		}

		return EvolveResult.STILL_EVOLVING;
	}

	private static final float EPSILON = 0.000001f;
}


/**
 * Edge data type for EPA
 */
class Edge
{

	public float distance;
	public Vector2f normal;
	public int index;

	/**
	 * Constructor
	 */
	public Edge()
	{
	}


	/**
	 * Constructor
	 *
	 * @param  dist distance from origin
	 * @param  n normal vector
	 * @param  ind index on simplex
	 *         specified with db
	 */
	public Edge(final float dist, final Vector2f n, final int ind)
	{

		distance = dist;
		normal = n;
		index = ind;
	}
}


/**
 * Polygon windinw -- clockwise or counter clockwise
 */
enum PolygonWinding { CW, CCW }
