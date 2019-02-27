package poj.Collisions;

import poj.linear.*;

import java.util.ArrayList;

// Algorthims from various authors
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

	public Polygon(Polygon p)
	{
		p.pts = this.purePts();
		p.size = this.size;
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

	public int indexOfFurthestPointInDirection(Vector2f dir)
	{
		Vector2f d = dir;
		int max = 0;
		float maxdist = -Float.MAX_VALUE;

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

	public String toString()
	{

		String str = "Polygon: size = " + size + ", pts: ";

		for (Vector2f i : pts) {
			str += i.toString() + ", ";
		}

		return str;
	}
}
