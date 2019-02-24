package poj.Collisions;

import poj.linear.*;

public class Circle implements CollisionShape
{
	private Vector2f center;
	private float radius;

	public Circle(float x, float y, float r)
	{
		center = new Vector2f(x, y);
		radius = r;
	}

	public Circle(Vector2f p, float r)
	{
		center = p;
		radius = r;
	}

	public Circle(Circle n)
	{
		center = new Vector2f(n.c());
		radius = n.r();
	}

	public Vector2f furthestPointInDirection(Vector2f d)
	{
		return center.pureAdd(d.pureNormalize().pureMul(this.r()));
	}


	// getter and setters
	public void setCenter(Vector2f n)
	{
		center = n;
	}

	public Vector2f getCenter()
	{
		return center;
	}

	public Vector2f c()
	{
		return center;
	}

	public Vector2f pureGetCenter()
	{
		return new Vector2f(center);
	}

	public float getRadius()
	{
		return radius;
	}

	public float r()
	{
		return getRadius();
	}

	public void setRadius(float n)
	{
		radius = n;
	}
	public String toString()
	{
		return "Circle: center = " + center.toString()
			+ " radius = " + radius;
	}
}
