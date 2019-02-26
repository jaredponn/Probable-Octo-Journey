package poj.Collisions;

import poj.linear.*;
import java.util.ArrayList;
import poj.Logger.*;

// Algorthim from various authors:
// https://caseymuratori.com/blog_0003
// http://www.dyn4j.org/2010/04/gjk-gilbert-johnson-keerthi/
// https://blog.hamaluik.ca/posts/building-a-collision-engine-part-1-2d-gjk-collision-detection/
// https://github.com/hamaluik/headbutt/blob/3985a0a39c77a9539fad2383c84f5d448b4e87ae/src/headbutt/twod/Headbutt.hx

public class GJK
{

	public ArrayList<Vector2f> verticies = new ArrayList<Vector2f>(3);
	public Vector2f direction;

	public GJK()
	{
	}

	public boolean areColliding(CollisionShape cola, CollisionShape colb)
	{
		verticies.clear();

		EvolveResult evl = EvolveResult.STILL_EVOLVING;

		int i = 0;

		while (evl == EvolveResult.STILL_EVOLVING
		       && i < MAX_EVOLUTION) {

			evl = evolveSimplex();
			verticies.add(support(cola, colb));
			++i;
		}

		return evl == EvolveResult.FOUND_INTERSECTION;
	}
	private Vector2f support(CollisionShape a, CollisionShape b)
	{
		final Vector2f pa = a.furthestPointInDirection(direction);
		final Vector2f pb =
			b.furthestPointInDirection(direction.pureNegate());
		Vector2f tmp = pa.pureSubtract(pb);
		return tmp;
	}

	private final int MAX_EVOLUTION = 20;
	private final float EPSILON = 0.000001f;


	// determiens the  closest point to the origin
	private EvolveResult evolveSimplex()
	{
		switch (verticies.size()) {
		case 0: {
			direction = new Vector2f(1,
						 0); // default search direction
			break;
		}

		case 1: {
			// verticies: [a]
			Vector2f a = verticies.get(0); // point just added
			direction = a.pureNegate();
			break;
		}

		case 2: {

			// verticies: [b, a]
			Vector2f a = verticies.get(1); // point just added
			Vector2f b = verticies.get(0);

			if (Vector2f.dot(a,
					 direction)
			    < 0) // no intersection
			{
				return EvolveResult.NO_INTERSECTION;
			}

			Vector2f ab = b.pureSubtract(a);
			Vector2f ao = a.pureNegate();

			direction = Vector2f.pureTripleProduct(
				ab, ao, ab); // perpendicular vector to ab

			/*
			if (Math.abs(direction.sqMag() - 0f)
			    <= EPSILON) { // if lines are parallell
				if (ab.sqMag()
				    >= ao.sqMag()) // if the originis contained
						   // within the points
					return EvolveResult.FOUND_INTERSECTION;
				else
					return EvolveResult.NO_INTERSECTION;
			}*/

			break;
		}

		case 3: {

			// verticies: [c, b, a]
			Vector2f a = verticies.get(2);
			Vector2f b = verticies.get(1);
			Vector2f c = verticies.get(0);

			if (Vector2f.dot(a,
					 direction)
			    < 0) // no intersection
			{
				return EvolveResult.NO_INTERSECTION;
			}

			Vector2f ao = a.pureNegate();
			Vector2f ac = c.pureSubtract(a);
			Vector2f ab = b.pureSubtract(a);

			Vector2f acnorm =
				Vector2f.pureTripleProduct(ab, ac, ac);
			Vector2f abnorm =
				Vector2f.pureTripleProduct(ac, ab, ab);

			if (Vector2f.dot(ao, acnorm) < 0) // outside ac
			{
				verticies.remove(0);
				direction = acnorm;
			} else if (Vector2f.dot(ao, abnorm) < 0) // outside ac
			{
				verticies.remove(0);
				direction = abnorm;
			} else {
				return EvolveResult.FOUND_INTERSECTION;
			}
			break;
		}

		default:
			Logger.lassert(
				"ERROR in GJK -- simplexes of size 2 and 3 are only supported");
			break;
		}

		return EvolveResult.STILL_EVOLVING;
	}
}
