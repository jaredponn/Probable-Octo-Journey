package poj.Collisions;

import poj.linear.*;
import java.util.ArrayList;
import poj.Logger.*;
import java.util.Optional;

// Algorthim from various authors:
// https://caseymuratori.com/blog_0003
// http://www.dyn4j.org/2010/04/gjk-gilbert-johnson-keerthi/
// https://blog.hamaluik.ca/posts/building-a-collision-engine-part-1-2d-gjk-collision-detection/
// https://github.com/hamaluik/headbutt/blob/3985a0a39c77a9539fad2383c84f5d448b4e87ae/src/headbutt/twod/Headbutt.hx

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

	/*
	public Optional<Double> timeOfPolygonCollision(final Polygon cola,
						       final Vector2f dcola,
						       final Polygon colb,
						       final Vector2f dcolb)
	{
		// from the relative persepctive of a
		Vector2f dv = dcolb.pureSubtract(dcola);

		float ncolbpts[] = new float[cola.getSize() * 2];
	}
	*/

	public boolean areColliding(final Polygon cola, final Polygon colb,
				    final Vector2f db)
	{
		final Polygon p =
			generateStretchedPolygonWithDirectionVector(colb, db);

		this.clearVerticies();

		return this.areColliding(cola, p);
	}

	// where a is stationary
	private static int TIME_OF_COLLISION_RESOLUTION = 15;
	public Optional<Double> timeOfPolygonCollision(final Polygon cola,
						       final Polygon colb,
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

	public Vector2f determineEdge(final CollisionShape cola,
				      final CollisionShape colb)
	{
		// TODO
		return new Vector2f(0, 0);
	}

	private Vector2f support(final CollisionShape a, final CollisionShape b)
	{
		final Vector2f pa = a.furthestPointInDirection(direction);
		final Vector2f pb =
			b.furthestPointInDirection(direction.pureNegate());
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
				verticies.remove(0);
				direction = ao;
			} else if (Vector2f.dot(ao, acnorm) > 0) // outside ac
			{
				verticies.remove(0);
				direction = acnorm;
			} else if (Vector2f.dot(ao, abnorm) > 0) // outside ac
			{
				verticies.remove(0);
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
}
