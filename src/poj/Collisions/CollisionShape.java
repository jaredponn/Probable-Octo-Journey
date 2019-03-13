package poj.Collisions;

import poj.linear.Vector2f;

public abstract class CollisionShape
{

	public Vector2f center;

	// furthest point in direction vector.
	abstract public Vector2f furthestPointInDirection(Vector2f d);

	public Vector2f getCenter()
	{
		return center;
	}

	public Vector2f pureGetCenter()
	{
		return new Vector2f(center);
	}

	public void setCenter(Vector2f n)
	{
		this.center = n;
	}

	public void copySetCenter(Vector2f n)
	{
		this.center = new Vector2f(n);
	}
}
