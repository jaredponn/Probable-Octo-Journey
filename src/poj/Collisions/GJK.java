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

	public GJK()
	{
		this.clearVerticies();
	}

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

		this.clearVerticies();

		return this.areColliding(cola, p);
	}


	/**
	 * Calcluates the time of polygon collision. If you are just trying to
	 * calculate if the collision occured over a distance, use the
	 * overloaded "areColliding" function with the third parameter "db".
	 * That will be significantly faster.
	 *
	 * @param  cola first static (stationary) collision box
	 * @param  colb second collision box that is moving with the delta
	 *         specified with db
	 * @param  db delta of the second collision box
	 * @return      Optional of the time of collision
	 */
	private static int TIME_OF_COLLISION_RESOLUTION = 15;
	private Polygon LAST_STRETCHED_POLY_B_MEMO;
	public Optional<Double>
	upperBoundTimeOfPolygonCollision(final Polygon cola, final Polygon colb,
					 final Vector2f db)
	{
		float mint = 0.0000001f; // min t
		float t = 0.5f;		 // middle t
		float maxt = 1f;	 // max t

		// first case where we go the entire distance of the
		// deltaof b
		if (!this.areColliding(cola, colb, db)) {
			return Optional.empty();
		}

		for (int i = 0; i < TIME_OF_COLLISION_RESOLUTION; ++i) {
			t = (mint + maxt) / 2f;
			final Vector2f d = db.pureMul(t);

			final Polygon p =
				generateStretchedPolygonWithDirectionVector(
					colb, d);

			this.clearVerticies();
			if (this.areColliding(cola, p)) {
				maxt = t;

			} else {
				mint = t;
			}

			// memoization for determinCollisionBodyBVector
			LAST_STRETCHED_POLY_B_MEMO = p;
		}

		return Optional.of((t + maxt) / 2d);
	}


	public Vector2f determineCollisionBodyBVector(final Polygon cola,
						      final Polygon colb,
						      final Vector2f db)
	{
		Optional<Double> optt =
			upperBoundTimeOfPolygonCollision(cola, colb, db);

		// if there is no collision just return the original vector
		if (!optt.isPresent()) {
			return db;
		}

		Vector2f ndb = new Vector2f(0, 0); // new vector for db

		final double t = optt.get(); // time of collision
		final double s = 1d - t;     // extra time of t

		this.clearVerticies();
		this.areColliding(cola,
				  LAST_STRETCHED_POLY_B_MEMO); // generates the
							       // simplex
		Vector2f n = calculatePenetrationVector(
			cola,
			LAST_STRETCHED_POLY_B_MEMO); // collision normal

		Vector2f pn = Vector2f.pureTripleProduct(
			n, db,
			n); // perpendicular to normal in the direction of db

		Vector2f tmp;
		final float pnsqmag = pn.sqMag();
		// projection of vb * s onto pn
		if (Math.abs(pnsqmag) == 0) {
			tmp = new Vector2f(0, 0);
		} else {
			final float k = pn.dot(db.pureMul((float)s)) / pnsqmag;
			tmp = pn.pureMul(k);
		}


		// building the new collision vector
		ndb.add(db.pureMul((float)t));
		ndb.add(tmp);

		return ndb;
	}


	// alias for the upper bound except it calcluates and gives the lower
	// bound. TODO clean up this code
	public Optional<Double>
	lowerBoundTimeOfPolygonCollision(final Polygon cola, final Polygon colb,
					 final Vector2f db)
	{
		float mint = 0.0000001f; // min t
		float t = 0.5f;		 // middle t
		float maxt = 1f;	 // max t

		// first case where we go the entire distance of the
		// deltaof b
		if (!this.areColliding(cola, colb, db)) {
			return Optional.empty();
		}

		for (int i = 0; i < TIME_OF_COLLISION_RESOLUTION; ++i) {
			t = (mint + maxt) / 2f;
			final Vector2f d = db.pureMul(t);

			final Polygon p =
				generateStretchedPolygonWithDirectionVector(
					colb, d);

			this.clearVerticies();
			if (this.areColliding(cola, p)) {
				maxt = t;

			} else {
				mint = t;
			}
		}


		return Optional.of((t + mint) / 2d);
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


	private Vector2f support(final CollisionShape a, final CollisionShape b)
	{
		return GJK.calculateSupport(a, b, direction);
	}

	public static Vector2f calculateSupport(final CollisionShape a,
						final CollisionShape b,
						Vector2f d)
	{
		final Vector2f pa = a.furthestPointInDirection(d);
		final Vector2f pb = b.furthestPointInDirection(d.pureNegate());
		Vector2f tmp = pa.pureSubtract(pb);
		return tmp;
	}

	// Evolves the simplex to enclose the origin
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

	// returns all points in the minkowski difference
	public static ArrayList<Vector2f> minkowskiDiff(Polygon a, Polygon b)
	{
		ArrayList<Vector2f> arr = new ArrayList<Vector2f>();

		Vector2f[] apts = a.pts();
		Vector2f[] bpts = b.pts();

		for (int i = 0; i < a.size(); ++i) {
			for (int j = i; j < b.size(); ++j) {
				arr.add(apts[i].pureSubtract(bpts[j]));
			}
		}
		return arr;
	}

	private static final float EPSILON = 0.000001f;
}

class Edge
{
	public float distance;
	public Vector2f normal;
	public int index;

	public Edge()
	{
	}

	public Edge(final float dist, final Vector2f n, final int ind)
	{

		distance = dist;
		normal = n;
		index = ind;
	}
}

enum PolygonWinding { CW, CCW }
