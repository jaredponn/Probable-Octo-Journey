package poj.Collisions;
/**
 * Rectangle -- rectangle for collisions
 *
 * date March 10, 2019
 * @author Jared Pon
 * @version 1.0
 */

import poj.linear.*;
import poj.Logger.*;
import java.util.ArrayList;

public class Rectangle
{
	/*
	 *   y
	 *   ^
	 *   |                   max
	 *   |        0---------1
	 *   |        |         |
	 *   |        |         |
	 *   |        2________ 3
	 *   |      min
	 *   |
	 * --+------------------------> x
	 *   |
	 */
	Vector2f min;
	Vector2f max;

	private static int MAX_RECT_PTS = 4;

	/**
	 *  constructor
	 *
	 * @param min -- min vector -- see ascii diagram
	 * @param max -- max vector -- see ascii diagram
	 *
	 */
	public Rectangle(Vector2f min, Vector2f max)
	{
		this(min.x, min.y, max.x, max.y);
	}


	/**
	 *  constructor
	 *
	 * @param minx -- min x
	 * @param miny -- min y
	 * @param maxx -- max x
	 * @param maxy -- max y
	 *
	 */
	public Rectangle(float minx, float miny, float maxx, float maxy)
	{
		if (minx > maxx)
			Logger.logMessage(
				"Error in Rectangle -- minx should be greater than maxx. Minx is: "
				+ minx + ", and maxx: " + maxx);

		if (miny > maxy)
			Logger.logMessage(
				"Error in Rectangle -- miny should be greater than maxy. Miny is: "
				+ miny + ", and maxy: " + maxy);

		this.min = new Vector2f(minx, miny);
		this.max = new Vector2f(maxx, maxy);
	}


	/**
	 *  copy constructor
	 *
	 * @param n -- rectangle to copy
	 *
	 */
	public Rectangle(Rectangle n)
	{
		this(n.min.x, n.min.y, n.max.x, n.max.y);
	}


	/**
	 *  checks if a point is contained within the rectangle
	 *
	 * @param p -- point
	 * @return true if the point is contained
	 *
	 */
	public boolean isPointContained(Vector2f p)
	{
		final boolean xcontained = min.x <= p.x && p.x <= max.x;
		final boolean ycontained = min.y <= p.y && p.y <= max.y;
		return xcontained && ycontained;
	}


	/**
	 *  checks if the rectangle is colliding with
	 *
	 * @param b -- rectangle to be checked if colliding with
	 * @return true if they are colliding
	 *
	 */
	public boolean isCollidingWith(Rectangle b)
	{
		return this.isPointContained(b.getMin())
			|| this.isPointContained(b.getMax());
	}

	/**
	 *  shifts the rectangle by n
	 *
	 * @param n -- shift rectangle by
	 */
	public void shiftRectangleBy(Vector2f n)
	{
		min.add(n);
		max.add(n);
	}


	/**
	 *  gets points as indexed by the ascii diagram
	 *
	 * @param i index to get
	 * @return point corrosponding to the index
	 */
	public Vector2f getPoint(int i)
	{
		switch (i) {
		case 0:
			return new Vector2f(getMinX(), getMaxY());

		case 1:
			return new Vector2f(getMaxX(), getMaxY());

		case 2:
			return new Vector2f(getMinX(), getMinY());

		case 3:
			return new Vector2f(getMaxX(), getMinY());

		default:
			Logger.logMessage(
				"Error in Rectangle - get point can only be applied with numbers 0-3 inclusive");
			return null;
		}
	}

	/**
	 *  gets the width
	 *
	 * @return width
	 */
	public float getWidth()
	{
		return max.x - min.x;
	}

	/**
	 *  gets the height
	 *
	 * @return height
	 */
	public float getHeight()
	{
		return max.y - min.y;
	}


	/**
	 *  gets the area
	 *
	 * @return area
	 */
	public float getArea()
	{
		return getHeight() * getWidth();
	}

	/**
	 *  gets the min x
	 *
	 * @return min x
	 */
	public float getMinX()
	{
		return min.x;
	}

	/**
	 *  gets the min y
	 *
	 * @return min y
	 */
	public float getMinY()
	{
		return min.y;
	}


	/**
	 *  gets the min point
	 *
	 * @return min point
	 */
	public Vector2f getMin()
	{
		return min;
	}

	/**
	 *  gets the max point
	 *
	 * @return max point
	 */
	public Vector2f getMax()
	{
		return max;
	}
	/**
	 *  pure gets the min point
	 *
	 * @return min point
	 */
	public Vector2f pureGetMin()
	{
		return new Vector2f(min);
	}


	/**
	 *  pure gets the max point
	 *
	 * @return max point
	 */
	public Vector2f pureGetMax()
	{
		return new Vector2f(max);
	}

	/**
	 *  gets the max x
	 *
	 * @return max x
	 */
	public float getMaxX()
	{
		return max.x;
	}

	/**
	 *  gets the max y
	 *
	 * @return max y
	 */
	public float getMaxY()
	{
		return max.y;
	}

	/**
	 *  gets the area of the area of two bounding rects
	 *  @param a : rectangle a
	 *  @param b : rectangle b
	 *
	 * @return area
	 */
	public static float maxAreaOfBoundingRects(Rectangle a, Rectangle b)
	{
		return calculateArea(getBoundingRectOfRects(a, b));
	}

	/**
	 *  gets the bounding rectangle of a collision shape
	 *  @param cs : collision shape
	 *
	 * @return rectangle
	 */
	public static Rectangle getBoundingRectangle(CollisionShape cs)
	{
		if (cs == null)
			return null;
		else
			return cs.getBoundingRectangle();
	}

	/**
	 *  gets the bounding rectangle of a collision shape
	 *  @param a : rectangle a
	 *  @param b : rectanlge b
	 *
	 * @return rectangle that encloses them both
	 */
	public static Rectangle getBoundingRectOfRects(Rectangle a, Rectangle b)
	{

		if (a == null && b == null) {
			return null;
		}


		if (b == null)
			return new Rectangle(a);

		if (a == null)
			return new Rectangle(b);

		if (b == null)
			return new Rectangle(a);

		float minx = Math.min(a.getMinX(), b.getMinX());
		float miny = Math.min(a.getMinY(), b.getMinY());

		float maxx = Math.max(a.getMaxX(), b.getMaxX());
		float maxy = Math.max(a.getMaxY(), b.getMaxY());

		return new Rectangle(minx, miny, maxx, maxy);
	}

	/**
	 *  calculates the area of a bounding rect
	 *  @param n : rectanlge n
	 *
	 * @return area
	 */
	public static float calculateArea(Rectangle n)
	{
		if (n == null)
			return 0f;
		return n.getArea();
	}


	/**
	 *  toString
	 * @return String
	 */
	public String toString()
	{
		return "Rectangle(" + super.toString() + "): min = "
			+ min.toString() + ", max = " + max.toString();
	}
}
