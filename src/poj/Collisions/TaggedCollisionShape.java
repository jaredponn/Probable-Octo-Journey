package poj.Collisions;
/**
 * Tagged collision shape -- tagged collision shape to hold the entity that the
 * collision shape is related to. Honestly, this was an awful idea and I
 * should've considered using a min binary heap and 2 arrays instead
 *
 * date March 10, 2019
 * @author Jared Pon
 * @version 1.0
 */

import poj.linear.*;

public class TaggedCollisionShape implements CollisionShape
{
	// references to the data
	CollisionShape collisionShape;
	int e;

	/**
	 *  constructor
	 *  @param cs : collisoin shape
	 *  @param e : entity index
	 */
	public TaggedCollisionShape(CollisionShape cs, int e)
	{
		this.collisionShape = cs;
		this.e = e;
	}

	/**
	 *  furthest point in direction
	 *  @param d : direction
	 *  @return pointin direction
	 */
	public Vector2f furthestPointInDirection(Vector2f d)
	{
		return collisionShape.furthestPointInDirection(d);
	}

	/**
	 *  calculates bouding rectangle
	 *  @return rectangle
	 */
	public Rectangle calculateBoundingRectangle()
	{
		return collisionShape.calculateBoundingRectangle();
	}


	/**
	 *  gets the height
	 *  @return height
	 */
	public float getHeight()
	{
		return collisionShape.getHeight();
	}

	/**
	 *  gets the width
	 *  @return width
	 */
	public float getWidth()
	{
		return collisionShape.getWidth();
	}

	/**
	 *  gets the bounding rectangle
	 *  @return rectangle
	 */
	public Rectangle getBoundingRectangle()
	{
		return collisionShape.getBoundingRectangle();
	}

	/**
	 *  pure gets the bounding rectangle
	 *  @return rectangle
	 */
	public Rectangle pureGetBoundingRectangle()
	{
		return collisionShape.pureGetBoundingRectangle();
	}

	/**
	 *  gets the entity
	 *  @return entity
	 */
	public int getEntity()
	{
		return e;
	}
}
