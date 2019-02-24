package Components;

import poj.Component.*;
import poj.Collisions.*;
import poj.linear.*;

public class CircleCollisionBody implements Component
{

	private Circle collisonCircle;
	private Vector2f positionDelta;

	public CircleCollisionBody(Circle c)
	{
		collisonCircle = c;
		positionDelta = new Vector2f(0, 0);
	}

	public CircleCollisionBody(Circle c, Vector2f v)
	{
		collisonCircle = c;
		positionDelta = v;
	}

	public CircleCollisionBody(CircleCollisionBody n)
	{
		collisonCircle = new Circle(n.getCollisionCircle());
		positionDelta = new Vector2f(n.getPositionDelta());
	}

	public Circle getCollisionCircle()
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
