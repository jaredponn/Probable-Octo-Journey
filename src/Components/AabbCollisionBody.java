package Components;

import poj.Component.*;
import poj.Collisions.*;
import poj.linear.*;

public class AabbCollisionBody implements Component
{

	private CollisionAabb collisionAabb;
	private Vector2f positionDelta;

	public AabbCollisionBody(CollisionAabb c)
	{
		collisionAabb = c;
		positionDelta = new Vector2f(0, 0);
	}

	public AabbCollisionBody(CollisionAabb c, Vector2f v)
	{
		collisionAabb = c;
		positionDelta = v;
	}

	public AabbCollisionBody(AabbCollisionBody n)
	{
		collisionAabb = new CollisionAabb(n.getCollisionAabb());
		positionDelta = new Vector2f(n.getPositionDelta());
	}

	public CollisionAabb getCollisionAabb()
	{
		return collisionAabb;
	}

	public void setCollisionAabbTopLeft(Vector2f v)
	{
		Vector2f tmp = v.pureAdd(positionDelta);
		collisionAabb.setTopLeftAndUpdateAllPoints(tmp);
	}
	public Vector2f getPositionDelta()
	{
		return positionDelta;
	}

	public void print()
	{
		System.out.println(
			"CircleCollisionBody Component: collisionAabb { center = "
			+ collisionAabb.toString()
			+ "}, positionDelta =  " + positionDelta.toString());
	}
}
