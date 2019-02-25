package poj.Collisions;

import poj.linear.*;
import java.util.ArrayList;
import poj.Logger.*;

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
		ArrayList<Vector2f> simplex = new ArrayList<Vector2f>();

		Vector2f s = GJK.support(cola, colb, new Vector2f(1, 0));

		simplex.add(s); // first boundary added in a
				// random riection

		Vector2f d =
			s.pureNegate(); // change direction to the opposoite

		while (true) {
			Vector2f a = GJK.support(cola, colb, d); // new support

			if (Vector2f.dot(a, d) < 0) // no intersection
			{
			}

			// else if it does cross the origin add it i th simplex
			simplex.add(a);

			if (doSimplex(simplex, d)) // intersection
			{
			}
		}
	}

	// determiens the  closest point to the origin
	public static boolean doSimplex(ArrayList<Vector2f> simplex, Vector2f d)
	{
		switch (simplex.size()) {
		case 2:
			// simplex: [b, a]
			Vector2f a = simplex.get(1); // point just added
			Vector2f b = simplex.get(0);

			Vector2f ab = b.pureSubtract(a);
			Vector2f ao = a.pureNegate();


			Vector2f newsearchdirecion;

			if (Vector2f.dot(ab, ao) > 0) // if ab is the simplex
			{
				// ab is the simplex
				// the new normal perpendicular -- this is the
				// new search direction
				newsearchdirecion =
					Vector2f.pureTripleProduct(ab, ao, ab);
			} else {

				newsearchdirecion = ao;
			}


		case 3:

			// simplex: [c, b, a]

		default:
			Logger.lassert(
				"ERROR in GJK -- simplexes of size 2 and 3 are only supported");
			break;
		}

		return true;
	}
}
