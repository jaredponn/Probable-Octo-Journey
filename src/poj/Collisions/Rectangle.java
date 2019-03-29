package poj.Collisions;

import poj.linear.*;
import poj.Logger.*;
import java.util.ArrayList;

class Rectangle
{
	/*
	 *             max
	 *  -----------
	 *  |         |
	 *  |         |
	 *  |________ |
	 *min
	 */
	Vector2f min;
	Vector2f max;

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

	public boolean isPointContained(Vector2f p)
	{
		final boolean xcontained = min.x <= p.x && p.x <= max.x;
		final boolean ycontained = min.y <= p.y && p.y <= max.y;
		return xcontained && ycontained;
	}

	public float getWidth()
	{
		return max.x - min.x;
	}

	public float getHeight()
	{
		return max.y - min.y;
	}

	public float getMinX()
	{
		return min.x;
	}

	public float getMinY()
	{
		return min.y;
	}
}
