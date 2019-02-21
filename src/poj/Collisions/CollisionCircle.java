package poj.Collisions;

import poj.linear.*;

public class CollisionCircle
{
	private Vector2f center;
	private float radius;

	public CollisionCircle(float x, float y, float r)
	{
		center = new Vector2f(x, y);
		radius = r;
	}

	public CollisionCircle(Vector2f p, float r)
	{
		center = p;
		radius = r;
	}

	public CollisionCircle(CollisionCircle n)
	{
		center = new Vector2f(n.c());
		radius = n.r();
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
}
