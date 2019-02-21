package Components;

import poj.Component.*;
import poj.Collisions.*;
import poj.linear.*;

public class CircleCollisionBody implements Component
{

	private CollisionCircle collisonCircle;
	private Vector2f positionDelta;

	public CircleCollisionBody(CollisionCircle c)
	{
		collisonCircle = c;
		positionDelta = new Vector2f(0, 0);
	}

	public CircleCollisionBody(CollisionCircle c, Vector2f v)
	{
		collisonCircle = c;
		positionDelta = v;
	}

	public CircleCollisionBody(CircleCollisionBody n)
	{
		collisonCircle = new CollisionCircle(n.getCollisionCircle());
		positionDelta = new Vector2f(n.getPositionDelta());
	}

	public CollisionCircle getCollisionCircle()
	{
		return collisonCircle;
	}

	protected Vector2f getPositionDelta()
	{
		return positionDelta;
	}

	public void setCollisionCircleCenter(Vector2f v)
	{
		Vector2f tmp = v.pureAdd(positionDelta);
		collisonCircle.setCenter(tmp);
	}

	public void print()
	{
		System.out.println(
			"CircleCollisionBody Component: collisionCircle { center = "
			+ collisonCircle.c()
			+ ", radius =  " + collisonCircle.r()
			+ "}, positionDelta =  " + positionDelta.toString());
	}
}
