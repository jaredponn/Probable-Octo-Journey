package poj.Collisions;

import poj.linear.*;

import java.util.ArrayList;

// https://blog.hamaluik.ca/posts/swept-aabb-collision-using-minkowski-difference/
// http://www.dyn4j.org/2010/04/gjk-gilbert-johnson-keerthi/
// https://github.com/kroitor/gjk.c
// http://entropyinteractive.com/2011/04/gjk-algorithm/
// https://www.haroldserrano.com/blog/visualizing-the-gjk-collision-algorithm

public class Polygon implements CollisionShape
{
	public Vector2f pts[];
	public int size;

	public Polygon(Vector2f... pts)
	{
		this.size = pts.length;
		this.pts = new Vector2f[this.size];

		int i = 0;
		for (Vector2f p : pts) {
			this.pts[i] = new Vector2f(p);
			++i;
		}
	}


	public void shiftAllPoints(float x, float y)
	{

		for (int i = 0; i < size; ++i) {
			pts[i].add(x, y);
		}
	}

	public void shiftAllPoints(Vector2f n)
	{
		shiftAllPoints(n.x(), n.y());
	}


	public Vector2f[] pts()
	{
		return pts;
	}

	public Vector2f[] purePts()
	{
		Vector2f tmp[] = new Vector2f[size];
		for (int i = 0; i < size; ++i) {
			tmp[i] = new Vector2f(pts[i]);
		}

		return tmp;
	}

	public int getSize()
	{
		return size;
	}

	public int size()
	{
		return getSize();
	}

	// set of the first point and shifts all the other points accordingly
	public void setFirstPositionAndShiftAll(Vector2f n)
	{
		Vector2f d = n.pureSubtract(pts[0]);
		shiftAllPoints(d);
	}

	public void setFirstPositionAndShiftAll(float x, float y)
	{
		setFirstPositionAndShiftAll(new Vector2f(x, y));
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

	public int indexOfFurthestPointInDirection(Vector2f dir)
	{
		Vector2f d = dir;
		int max = 0;
		// scalar projectio upon d
		float maxdist = Float.MIN_VALUE;

		for (int i = 0; i < size; ++i) {
			// scalar projection on d
			final float tmp = Vector2f.dot(pts[i], d);

			if (tmp > maxdist) {
				max = i;
				maxdist = tmp;
			}
		}

		return max;
	}
	// returns the furthest point in shape in UNIT direction vector d.
	public Vector2f furthestPointInDirection(Vector2f d)
	{
		return pts[indexOfFurthestPointInDirection(d)];
	}


	// gjksupport function to build help build the simplex for gjk
	// returns the distance from the
	// origin of the boundary point
	public static Vector2f gjkSupport(Polygon a, Polygon b, Vector2f d)
	{
		Vector2f pa = a.furthestPointInDirection(d);
		Vector2f pb = b.furthestPointInDirection(d.pureNegate());

		// minkowski diff
		Vector2f c = pa.pureSubtract(pb);

		return c;
	}

	// retursn true if point p is in triangle a,b,c
	public static boolean isPointInTriangle(Vector2f p, Vector2f a,
						Vector2f b, Vector2f c)
	{
		// abc area
		float abc = areaOfTriangle(a, b, c);

		// pbc area
		float pbc = areaOfTriangle(p, b, c);

		// pac area
		float pac = areaOfTriangle(p, a, c);

		// pab area
		float pab = areaOfTriangle(p, a, b);

		/// (pab + pac + pab) == abc;
		return Math.abs((abc - (pbc + pac + pab))) <= EPSILON;
	}

	public static float areaOfTriangle(Vector2f t0, Vector2f t1,
					   Vector2f t2)
	{
		return Math.abs((t0.x * (t1.y - t2.y) + t1.x * (t2.y - t0.y)
				 + t2.x * (t0.y - t1.y))
				/ 2.0f);
	}

	final public static float EPSILON = 0.000001f;
}
