package poj.Collisions;

import poj.linear.*;

// Algorthims from various authors
// https://blog.hamaluik.ca/posts/swept-aabb-collision-using-minkowski-difference/
// http://www.dyn4j.org/2010/04/gjk-gilbert-johnson-keerthi/
// https://github.com/kroitor/gjk.c
// http://entropyinteractive.com/2011/04/gjk-algorithm/
// https://www.haroldserrano.com/blog/visualizing-the-gjk-collision-algorithm

public class Polygon implements CollisionShape
{
	public Vector2f pts[];
	public Rectangle bounds;
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
		bounds = this.getBoundingRectangle();
	}

	public Polygon(Polygon p)
	{
		this.pts = p.purePts();
		this.size = p.size;
		this.bounds = new Rectangle(p.bounds);
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

	public Vector2f pureGetAPointInPolygon(int index)
	{
		return new Vector2f(pts[index]);
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

	/**
	 * Sets the first position point and shifts the rest of the points
	 * accordingly. Returns the vector that it shifted all the points by
	 *
	 * @param  n the new point
	 * @return   vector all points shifted by
	 */
	public Vector2f setFirstPositionAndShiftAll(Vector2f n)
	{
		Vector2f d = n.pureSubtract(pts[0]);
		shiftAllPoints(d);
		bounds.shiftRectangleBy(d);
		return d;
	}

	public Vector2f setFirstPositionAndShiftAll(float x, float y)
	{
		return setFirstPositionAndShiftAll(new Vector2f(x, y));
	}

	public Polygon pureSetFirstPositionAndShiftAll(float x, float y)
	{
		Polygon p = new Polygon(this);
		p.setFirstPositionAndShiftAll(x, y);
		return p;
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

	public int indexOfClosestPointInDirection(Vector2f dir)
	{
		Vector2f d = dir;
		int min = 0;
		float mindist = Float.MAX_VALUE;

		for (int i = 0; i < size; ++i) {
			// scalar projection on d
			final float tmp = Vector2f.dot(pts[i], d);

			if (tmp < mindist) {
				min = i;
				mindist = tmp;
			}
		}
		return min;
	}

	// returns the farthest point in shape in direction vector d.
	public Vector2f furthestPointInDirection(Vector2f d)
	{
		return pts[indexOfFurthestPointInDirection(d)];
	}

	// returns the closestPointInDirection
	public Vector2f closestPointInDirection(Vector2f d)
	{
		return pts[indexOfClosestPointInDirection(d)];
	}

	public String toString()
	{

		String str = "Polygon(" + super.toString() + "): size = " + size
			     + ", pts: ";

		for (Vector2f i : pts) {
			str += i.toString() + ", ";
		}

		return str;
	}

	public Rectangle getBoundingRectangle()
	{
		if (bounds == null) {
			return calculateBoundingRectangle();
		} else
			return bounds;
	}

	public Rectangle pureGetBoundingRectangle()
	{
		return new Rectangle(getBoundingRectangle());
	}

	public float getHeight()
	{
		return bounds.getHeight();
	}

	public float getWidth()
	{
		return bounds.getWidth();
	}
}
