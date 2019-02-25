package poj.Collisions;

import poj.linear.*;


public class Circle implements CollisionShape
{
	private Vector2f center;
	private float radius;

	/**
	 * Constructor
	 * Constructs circle from an x and y coordinate with a radius
	 *
	 * @param x  x coord
	 * @param y  y coord
	 * @param r  radius
	 */
	public Circle(float x, float y, float r)
	{
		center = new Vector2f(x, y);
		radius = r;
	}

	/**
	 * Constructor
	 * Constructs circle from an x and y coordinate with a radius
	 *
	 * @param p  (x, y) coordinates
	 * @param r  radius
	 */
	public Circle(Vector2f p, float r)
	{
		center = p;
		radius = r;
	}


	/**
	 * Copy Constructor
	 */
	public Circle(Circle n)
	{
		center = new Vector2f(n.c());
		radius = n.r();
	}


	/**
	 * gets the furthest point in a direction.
	 * @param d  direction vector. Does not need to be a unit vector
	 */
	public Vector2f furthestPointInDirection(Vector2f d)
	{
		return center.pureAdd(d.pureNormalize().pureMul(this.r()));
	}


	/**
	 * sets the center
	 * @param n  new center point
	 */
	public void setCenter(Vector2f n)
	{
		center = n;
	}

	/**
	 * Returns a pointer to the center of the circle
	 * @return      center
	 */
	public Vector2f getCenter()
	{
		return center;
	}


	/**
	 * Returns a pointer to the center of the circle
	 * @return      center
	 */
	public Vector2f c()
	{
		return center;
	}


	/**
	 * Returns a new copy of the center
	 * @return      center
	 */
	public Vector2f pureGetCenter()
	{
		return new Vector2f(center);
	}


	/**
	 * Returns the radius
	 * @return      radius
	 */
	public float getRadius()
	{
		return radius;
	}


	/**
	 * Returns the radius
	 * @return      radius
	 */
	public float r()
	{
		return getRadius();
	}


	/**
	 * sets the radius
	 * @param n  the new radius
	 */
	public void setRadius(float n)
	{
		radius = n;
	}

	/**
	 * toStrig method
	 * @return      pretty printed string of the circle
	 */
	public String toString()
	{
		return "Circle: center = " + center.toString()
			+ " radius = " + radius;
	}
}
