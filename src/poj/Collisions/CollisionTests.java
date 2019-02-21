package poj.Collisions;
import java.util.Optional;
import poj.linear.Vector2f;

public class CollisionTests
{
	public static boolean areCirclesColliding(CollisionCircle a,
						  CollisionCircle b)
	{
		float sqdist = sqDistance(a.getCenter(), b.getCenter());
		float squaredRad = (a.r() + b.r()) * (a.r() + b.r());
		return sqdist <= squaredRad;
	}

	public static boolean areAabbsColliding(final CollisionAabb a,
						final CollisionAabb b)
	{
		return CollisionAabb.isColliding(a, b);
	}


	public static Optional<Double>
	intersectionTimeofMovingAabbs(final CollisionAabb a,
				      final CollisionAabb b, final Vector2f va,
				      final Vector2f vb)
	{
		return CollisionAabb.intersectionTimeOfMoving(a, b, va, vb);
	}


	// ----------------------------------
	//  Circle and Aabb
	// ----------------------------------

	public static boolean areCircleAndAabbColliding(final CollisionCircle s,
							final CollisionAabb b)
	{
		float sqDist = sqDistPointAabb(s.c(), b);
		return sqDist <= s.r() * s.r();
	}

	public static boolean areCircleAndAabbColliding(final CollisionAabb b,
							final CollisionCircle s)
	{
		return areCircleAndAabbColliding(s, b);
	}

	public static Vector2f closestPtOnAabbToCircle(final CollisionCircle s,
						       final CollisionAabb b)
	{
		return closestPtPointAabb(s.c(), b);
	}
	// ----------------------------------
	//  Aabb functions
	// ----------------------------------

	// returns the closest point on a Aabb to point p
	public static Vector2f closestPtPointAabb(Vector2f p, CollisionAabb b)
	{
		Vector2f q = new Vector2f(0, 0);

		// for each axis, if it is outside the axis, clamp to
		// the box, otherwise leave it where it is
		for (int i = 0; i < Vector2f.MAX_LENGTH; ++i) {
			float v = p.get(i);
			if (v < b.min().get(i))
				v = b.min().get(i); // v = max(v, b.min[i])
			if (v > b.max().get(i))
				v = b.max().get(i); // v = min(v, b.max[i])
			q.set(i, v);
		}
		return q;
	}

	// returns the closest point on a Aabb to point p
	public static float sqDistPointAabb(final Vector2f p,
					    final CollisionAabb b)
	{
		float sqDist = 0f;
		// for each axis accumlate the excess distance outside the box's
		// edges
		for (int i = 0; i < Vector2f.MAX_LENGTH; ++i) {
			float v = p.get(i);
			if (v < b.min().get(i))
				sqDist += (b.min().get(i) - v)
					  * (b.min().get(i) - v);
			if (v > b.max().get(i))
				sqDist += (v - b.max().get(i))
					  * (v - b.max().get(i));
		}
		return sqDist;
	}


	// ----------------------------------
	//  Utility functions
	// ----------------------------------

	// gets the squared distance between two points a, b
	public static float sqDistance(final Vector2f a, final Vector2f b)
	{
		Vector2f dif = a.pureSubtract(b);
		return dif.x * dif.x + dif.y * dif.y;
	}


	// Given a segment ab from points a and b, and point c: computes the
	// closest point on ab to c.
	public static Vector2f closestPtPointSegment(Vector2f c, Vector2f a,
						     Vector2f b)
	{
		Vector2f ab = b.pureSubtract(a);
		Vector2f ac = c.pureSubtract(a);

		Optional<Float> ko = Vector2f.comp(ab, ac);

		// a and b are the same (dot product is 0) then the closest
		// point is either a or b, where a = b
		if (!ko.isPresent())
			return new Vector2f(a);

		float k = ko.get();

		// if outside of the segment, clamp to the closest endpoint
		if (k < 0.0f)
			k = 0.0f;
		if (k > 1.0f)
			k = 1.0f;

		// a + t *ab
		return a.pureAdd(ab.pureMul(k));
	}


	// returns the square shortest distance between point c, and vector ab
	// (from points a and b)
	public static float sqDistPointSegment(Vector2f a, Vector2f b,
					       Vector2f c)
	{
		Vector2f d = closestPtPointSegment(c, a, b);
		return d.pureSubtract(c).sqMag();
	}

	// returns the distance between point, and vector ab (from points a and
	// b)
	public static float distPointSegment(Vector2f a, Vector2f b, Vector2f c)
	{
		return (float)Math.sqrt(sqDistPointSegment(a, b, c));
	}
}

// OBB:
// https://gamedev.stackexchange.com/questions/25397/obb-vs-obb-collision-detection
