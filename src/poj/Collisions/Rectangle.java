package poj.Collisions;

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

	public Rectangle(Vector2f min, Vector2f max)
	{
		this(min.x, min.y, max.x, max.y);
	}

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

	public Rectangle(Rectangle n)
	{
		this(n.min.x, n.min.y, n.max.x, n.max.y);
	}

	public boolean isPointContained(Vector2f p)
	{
		final boolean xcontained = min.x <= p.x && p.x <= max.x;
		final boolean ycontained = min.y <= p.y && p.y <= max.y;
		return xcontained && ycontained;
	}

	public boolean isCollidingWith(Rectangle b)
	{
		return this.isPointContained(b.getMin())
			|| this.isPointContained(b.getMax());
	}

	public void shiftRectangleBy(Vector2f n)
	{
		min.add(n);
		max.add(n);
	}

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

	public float getWidth()
	{
		return max.x - min.x;
	}

	public float getHeight()
	{
		return max.y - min.y;
	}

	public float getArea()
	{
		return getHeight() * getWidth();
	}

	public float getMinX()
	{
		return min.x;
	}

	public float getMinY()
	{
		return min.y;
	}


	public Vector2f getMin()
	{
		return min;
	}

	public Vector2f getMax()
	{
		return max;
	}

	public Vector2f pureGetMin()
	{
		return new Vector2f(min);
	}

	public Vector2f pureGetMax()
	{
		return new Vector2f(max);
	}

	public float getMaxX()
	{
		return max.x;
	}

	public float getMaxY()
	{
		return max.y;
	}

	public static float maxAreaOfBoundingRects(Rectangle a, Rectangle b)
	{
		return calculateArea(getBoundingRectOfRects(a, b));
	}

	public static Rectangle getBoundingRectangle(CollisionShape cs)
	{
		if (cs == null)
			return null;
		else
			return cs.getBoundingRectangle();
	}

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

	public static float calculateArea(Rectangle n)
	{
		if (n == null)
			return 0f;
		return n.getArea();
	}

	public String toString()
	{
		return "Rectangle(" + super.toString() + "): min = "
			+ min.toString() + ", max = " + max.toString();
	}
}
