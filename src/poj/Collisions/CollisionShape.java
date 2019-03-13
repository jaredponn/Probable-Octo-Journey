package poj.Collisions;

import poj.linear.Vector2f;

public interface CollisionShape {

	// furthest point in direction vector.
	public Vector2f furthestPointInDirection(Vector2f d);
}
