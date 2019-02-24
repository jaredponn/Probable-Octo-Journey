package poj.Collisions;

import poj.linear.*;
import java.util.ArrayList;

public class GJK
{

	public static Vector2f support(CollisionShape a, CollisionShape b,
				       Vector2f d)
	{
		final Vector2f pa = a.furthestPointInDirection(d);
		final Vector2f pb = b.furthestPointInDirection(d.pureNegate());
		return pa.pureSubtract(pb);
	}


	public static boolean areColliding(CollisionShape cola,
					   CollisionShape colb)
	{
		ArrayList<Vector2f> supportSet = new ArrayList();

		Vector2f d = new Vector2f(1, 0); // intial serach direction

		supportSet.add(
			GJK.support(cola, colb, d)); // first boundary added

		d.negate(); // change direction

		while (true) {
			Vector2f a = GJK.support(cola, colb, d); // new support

			if (Vector2f.dot(a, d) < 0) // no intersection
			{
			}

			// else if it does cross the origin
			supportSet.add(a);

			if (doSimplex(supportSet, d)) // intersection
			{
			}
		}
	}

	public static boolean doSimplex(ArrayList<Vector2f> s, Vector2f d)
	{
		return true;
	}
}
