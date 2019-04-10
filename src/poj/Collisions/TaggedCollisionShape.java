package poj.Collisions;

import poj.linear.*;

public class TaggedCollisionShape implements CollisionShape
{
	// references to the data
	CollisionShape collisionShape;
	int e;

	public TaggedCollisionShape(CollisionShape cs, int e)
	{
		this.collisionShape = cs;
		this.e = e;
	}

	// furthest point in direction vector.
	public Vector2f furthestPointInDirection(Vector2f d)
	{
		return collisionShape.furthestPointInDirection(d);
	}

	public Rectangle calculateBoundingRectangle()
	{
		return collisionShape.calculateBoundingRectangle();
	}

	public float getHeight()
	{
		return collisionShape.getHeight();
	}

	public float getWidth()
	{
		return collisionShape.getWidth();
	}


	public Rectangle getBoundingRectangle()
	{
		return collisionShape.getBoundingRectangle();
	}

	public Rectangle pureGetBoundingRectangle()
	{
		return collisionShape.pureGetBoundingRectangle();
	}

	public int getEntity()
	{
		return e;
	}
}
