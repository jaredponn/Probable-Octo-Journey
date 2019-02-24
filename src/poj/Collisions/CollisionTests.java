package poj.Collisions;
import java.util.Optional;
import poj.linear.*;
import poj.Pair;

public class CollisionTests
{
	private static float EPSILON = 0.0000001f;

	public static boolean areCirclesColliding(Circle a,
						  Circle b)
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

	public static boolean areCircleAndAabbColliding(final Circle s,
							final CollisionAabb b)
	{
		float sqDist = sqDistPointAabb(s.c(), b);
		return sqDist <= s.r() * s.r();
	}

	public static Vector2f circleAabbNormal(final Circle s,
						final CollisionAabb b)
	{
		Vector2f p = new Vector2f(clamp(s.c().x, b.min().x, b.max().x),
					  clamp(s.c().y, b.min().y, b.max().y));
		// outside delta
		Vector2f pc = s.c().pureSubtract(p);

		Vector2f cr = s.c().pureAdd(s.r(), 0).pureSubtract(s.c());
		Vector2f n = pc.pureAdd(cr);

		{
			Vector2f pr = s.c().pureAdd(-s.r(), 0)
					      .pureSubtract(s.c())
					      .pureAdd(pc);
			if (n.sqMag() > pr.sqMag()) {
				n = pr;
			}
		}
		{
			Vector2f pr = s.c().pureAdd(0, s.r())
					      .pureSubtract(s.c())
					      .pureAdd(pc);
			if (n.sqMag() > pr.sqMag()) {
				n = pr;
			}
		}
		{
			Vector2f pr = s.c().pureAdd(0, -s.r())
					      .pureSubtract(s.c())
					      .pureAdd(pc);
			if (n.sqMag() > pr.sqMag()) {
				n = pr;
			}
		}
		n.log();
		return n;
	}


	public static boolean areCircleAndAabbColliding(final CollisionAabb b,
							final Circle s)
	{
		return areCircleAndAabbColliding(s, b);
	}

	public static Vector2f closestPtOnAabbToCircle(final Circle s,
						       final CollisionAabb b)
	{
		return closestPtPointAabb(s.c(), b);
	}

	// reutnrs the  collision time (0,1), and collision point p
	public static Optional<Double>
	intersectMovingCircleAabb(final Circle s, final Vector2f d,
				  final CollisionAabb b)
	{

		double t;
		// computes Aabb from expanding b the  circle radius r
		CollisionAabb e = new CollisionAabb(b);
		e.min().x -= s.r();
		e.min().y -= s.r();
		e.max().x += s.r();
		e.max().y += s.r();

		Optional<Pair<Double, Vector2f>> tmp =
			intersectRayAabb(s.c(), d, e);

		// intersect ray against expanded aabb e. Exit with no
		// intersection fi ray misses e. Otherwise get intesersciton
		// point p and time t
		if (!tmp.isPresent())
			return Optional.empty();


		System.out.println("---------------------- lets go");
		System.out.println(s.toString());
		d.log("d log");
		System.out.println(b.toString());

		Vector2f p = tmp.get().snd();
		t = tmp.get().fst();

		// Compute which of the min and max faces of b the interesction
		// point p lies outside of ( calcluating voronai regions).
		// Notice that u and v cannot have the same bit sets and they
		// must have at least one bit set amongst them (XOR)
		int u = 0, v = 0;
		if (p.x() < b.min().x())
			u |= 1;
		if (p.x() > b.max().x())
			v |= 1;
		if (p.y() < b.min().y())
			u |= 2;
		if (p.y() > b.max().y())
			v |= 2;

		// or bits together (u | v)
		int m = u + v;

		// define line seg  [c, c+d] for the circle movement
		Vector2f sega = s.c();
		Vector2f segb = s.c().pureAdd(d);

		// if all 3 bits set, are set, p is in a vertex region
		if (m == 7) {
			// therefore, must now intersect segment [c,c+d] against
			// capsules of the three edges meeting at the vertex and
			// return the best time if one r more hit.
			System.out.println("m ==7");
			double tmin = Double.MAX_VALUE;

			Optional<Pair<Double, Vector2f>> col0 =
				intersectSegmentCapsule(
					sega, segb, corner(b, v),
					corner(b, v ^ 1), s.r());
			if (col0.isPresent()) {
				t = col0.get().fst();
				tmin = Math.min(t, tmin);
			}

			Optional<Pair<Double, Vector2f>> col1 =
				intersectSegmentCapsule(
					sega, segb, corner(b, v),
					corner(b, v ^ 2), s.r());
			if (col1.isPresent()) {
				t = col1.get().fst();
				tmin = Math.min(t, tmin);
			}

			Optional<Pair<Double, Vector2f>> col2 =
				intersectSegmentCapsule(
					sega, segb, corner(b, v),
					corner(b, v ^ 4), s.r());
			if (col2.isPresent()) {
				t = col2.get().fst();
				tmin = Math.min(t, tmin);
			}

			if (tmin == Float.MAX_VALUE) // no intersection
				return Optional.empty();
			t = tmin;
			System.out.println(t + " finaltwhenm=7");
			return Optional.of(t);
		}

		// if onlyy 1 bit is set to m, then p is a face region
		if ((m & (m - 1)) == 0) {
			System.out.println(t + " face");
			return Optional.of(t);
		}


		// p is an edge region -- intersect against capsulte at the edge
		Optional<Pair<Double, Vector2f>> ttmp = intersectSegmentCapsule(
			sega, segb, corner(b, u ^ 7), corner(b, v), s.r());
		if (ttmp.isPresent()) {
			System.out.println(ttmp.get().fst + " edgeregion");
		} else {
			System.out.println(ttmp + " edgeregion no match");
		}
		return ttmp.isPresent() ? Optional.of(ttmp.get().fst())
					: Optional.empty();
	}

	// returns the Aabb vertex with index n
	private static Vector2f corner(CollisionAabb b, int n)
	{
		Vector2f p = new Vector2f();
		p.x = ((n & 1) == 1) ? b.max().x : b.min().x;
		p.y = ((n & 1) == 1) ? b.max().y : b.min().y;
		return p;
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

	// tests if a cricle and a capsulere colliding
	public static boolean circleCapsuleTest(final Circle c,
						Capsule cap)
	{
		float sqdist = sqDistPointSegment(cap.a(), cap.b(), c.c());
		float radius = c.r() + cap.r();
		return sqdist <= radius * radius;
	}

	public static boolean capsuleCapsuleTest(Capsule cap1, Capsule cap2)
	{
		float sqdist = sqDistSegmentSegment(cap1.a(), cap1.b(),
						    cap2.a(), cap2.b());
		float radius = cap1.r() + cap2.r();
		return sqdist <= radius * radius;
	}

	// returns the mintime [0,1] and point of intersection from a ray and an
	// aabb. If no such collision exists, returns Optional.empty().
	// Intersects ray R(t) = p + t*d against Aabb a
	public static Optional<Pair<Double, Vector2f>>
	intersectRayAabb(Vector2f p, Vector2f d, CollisionAabb a)
	{

		double tmin = 0d;
		double tmax = Float.MAX_VALUE;

		// for all slabs
		for (int i = 0; i < Vector2f.MAX_LENGTH; ++i) {
			// if the ray isn't being cast in this direction, just
			// ignore
			if (Math.abs(d.get(i)) < EPSILON) {

				// ray is parallel, not hit if origin is not
				// within slab
				if (p.get(i) < a.min().get(i)
				    || p.get(i) > a.max().get(i))
					return Optional.empty();
			} else {
				// compute intersection value t of the ray for
				// near and far plane of the slab
				float ood = 1f / d.get(i);
				float t1 = (a.min().get(i) - p.get(i)) * ood;
				float t2 = (a.max().get(i) - p.get(i)) * ood;

				// make t1 be intesrection with near plane and
				// t2 with the far plane (swap)
				if (t1 > t2) {
					float tmp = t1;
					t1 = t2;
					t2 = tmp;
				}
				// computer intersection of slab intersection
				// intervals
				if (t1 > tmin)
					tmin = t1;
				if (t2 < tmax)
					tmax = t2;

				if (tmin > tmax)
					return Optional.empty();
			}
		}
		Vector2f q = p.pureAdd((d.pureMul((float)tmin)));

		return Optional.of(new Pair<Double, Vector2f>(tmin, q));
	}

	public static Optional<Pair<Double, Vector2f>>
	intersectSegmentAabb(Vector2f a, Vector2f b, CollisionAabb aabb)
	{
		System.out.println("intersectSegmenAabb -------");
		a.log("a");
		b.log("b");
		System.out.println(aabb.toString());

		Vector2f p = a;
		Vector2f ab = b.pureSubtract(a);
		float abmag = ab.mag();
		Vector2f d = ab.pureNormalize();
		d.log("d");

		double tmin = -Float.MAX_VALUE;
		double tmax = Float.MAX_VALUE;

		// for all slabs
		for (int i = 0; i < Vector2f.MAX_LENGTH; ++i) {
			// ray is parallel, not hit if origin is not
			// within slab
			if (Math.abs(d.get(i)) < EPSILON) {

				if (p.get(i) < aabb.min().get(i)
				    || p.get(i) > aabb.max().get(i))
					return Optional.empty();
			} else {
				// compute intersection value t of the ray for
				// near and far plane of the slab
				float ood = 1f / d.get(i);
				float t1 = (aabb.min().get(i) - p.get(i)) * ood;
				System.out.println(t1 + " t1");
				float t2 = (aabb.max().get(i) - p.get(i)) * ood;
				System.out.println(t2 + " t2");

				// make t1 be intesrection with near plane and
				// t2 with the far plane (swap)
				if (t1 > t2) {
					float tmp = t1;
					t1 = t2;
					t2 = tmp;
				}

				if (t1 > tmin)
					tmin = t1; //  want largest tmin
				if (t2 < tmax)
					tmax = t2; // want smallest tmax

				if (tmin > tmax)
					return Optional.empty(); // box missed


				if (tmax < 0)
					return Optional
						.empty(); // box behind ray

				if (!(tmin <= abmag))
					return Optional
						.empty(); // tests if it is
							  // witihn the segment
			}
		}
		Vector2f q = p.pureAdd((d.pureMul((float)tmin)));

		return Optional.of(new Pair<Double, Vector2f>(tmin, q));
	}

	// Intersects R(t) = p + td, |d| = 1, with circle s. Returns <t val of
	// intersection, q point of intersection>
	public static Optional<Pair<Double, Vector2f>>
	intersectRayCircle(Vector2f p, Vector2f d, Circle s)
	{
		Vector2f m = p.pureSubtract(s.c());
		float b = Vector2f.dot(m, d);
		float c = Vector2f.dot(m, m) - s.r() * s.r();

		// exit if r's origin is outside s (c > 0) and r pointing away
		// from s (b > 0)
		if (c > 0f && b > 0f)
			return Optional.empty();

		float discr = b * b - c;

		// negative discri corresponds to ray missing sphere
		if (discr < 0f)
			return Optional.empty();

		// ray is found to intersect with sphere
		// computing smallest t val of intersection
		double t = -b - Math.sqrt(discr);

		// if t is negative, ray started inside the sphere -- clamp to 0
		if (t < 0d)
			t = 0d;

		Vector2f q = p.pureAdd((d.pureMul((float)t)));

		return Optional.of(new Pair<Double, Vector2f>(t, q));
	}

	// Intersects segment AB, with circle s. Returns <t val of
	// intersection along d vector AB, q point of intersection>
	public static Optional<Pair<Double, Vector2f>>
	intersectSegmentCircle(Vector2f a, Vector2f b, Circle s)
	{
		Vector2f p = a;
		Vector2f ab = b.pureSubtract(a);
		float abmag = ab.mag();
		Vector2f d = ab.pureMul(1f / abmag);

		Vector2f m = p.pureSubtract(s.c());
		float bq = Vector2f.dot(m, d);
		float cq = Vector2f.dot(m, m) - s.r() * s.r();

		// exit if r's origin is outside s (c > 0) and r pointing away
		// from s (b > 0)
		if (cq > 0f && bq > 0f)
			return Optional.empty();

		float discr = bq * bq - cq;

		// negative discri corresponds to ray missing sphere
		if (discr < 0f)
			return Optional.empty();

		// ray is found to intersect with sphere
		// computing smallest t val of intersection
		double t = -bq - Math.sqrt(discr);

		// outside of the segment
		if (!(t <= abmag))
			return Optional.empty();

		// if t is negative, ray started inside the sphere -- clamp to 0
		if (t < 0d)
			t = 0d;

		Vector2f q = p.pureAdd((d.pureMul((float)t)));

		return Optional.of(new Pair<Double, Vector2f>(t, q));
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

		Optional<Float> ko = Vector2f.compt(ab, ac);

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

	// clamps value between two values
	public static float clamp(float n, float min, float max)
	{
		if (n < min)
			return min;
		if (n > min)
			return max;
		return n;
	}


	// returns the parameters (s,t ) of S1(s)=P1+s*(Q1-P1) and
	// S2(t)=P2+t*(Q2-P2) so that they are closest together
	public static Pair<Float, Float>
	closestParametersSegmentSegment(Vector2f p1, Vector2f q1, Vector2f p2,
					Vector2f q2)
	{
		float s, t; // parameters

		Vector2f d1 =
			q1.pureSubtract(p1); // direction vector of segment S1
		Vector2f d2 = q2.pureSubtract(p2); // direction vector of S2
		Vector2f r = p1.pureSubtract(p2);

		float a = Vector2f.dot(d1, d1); // squared length of segment S1
		float e = Vector2f.dot(d2, d2); // squared length of segment S2
		float f = Vector2f.dot(d2, r);

		// if the segments are small enough to be points
		if (a <= EPSILON && e <= EPSILON) {
			s = t = 0f;
			return new Pair<Float, Float>(s, t);
		}

		// first segment degenerates into a point
		if (a <= EPSILON) {
			s = 0f;
			t = f / e; //  s = 0 => t = (b*s + f) / e = f/e
		} else {
			float c = Vector2f.dot(d1, r);
			// second segment degenerates into a point
			if (e <= EPSILON) {
				t = 0f;
				s = clamp(
					-c / a, 0f,
					1f); // t = 0 => s = (b*t -c) /a = -c/a
			} else {
				// non degenerate case
				float b = Vector2f.dot(d1, d2);
				float denom = a * e - b * b;

				// if segments are not parallel compute the
				// closest points on L1 to L2 and clamp to the
				// segment S1. Otherwise, just pick an arbitrary
				// s (0)
				if (denom != 0f) {
					s = clamp((b * f - c * e) / denom, 0f,
						  1f);
				} else {
					s = 0f;
				}

				// t = dot((P1 + D1*s) -P2, D2 ) / dot(D2,D2) =
				// (b*s + f)/e
				t = (b * s + f) / e;

				// if t in [0,1] it's done. Otherweise clamp t,
				// recompute s for the new val of t using s
				if (t < 0f) {
					t = 0f;
					s = clamp(-c / a, 0f, 1f);
				} else if (t > 1f) {
					t = 1f;
					s = clamp((b - c) / a, 0f, 1f);
				}
			}
		}
		return new Pair<Float, Float>(s, t);
	}

	// returns the closest points (C1, C2) of S1(s)=P1+s*(Q1-P1) and
	// S2(t)=P2+t*(Q2-P2)
	public static Pair<Vector2f, Vector2f>
	closestPtsSegmentSegment(Vector2f p1, Vector2f q1, Vector2f p2,
				 Vector2f q2)
	{


		Pair<Float, Float> n =
			closestParametersSegmentSegment(p1, q1, p2, q2);

		float s = n.fst();
		float t = n.snd();

		Vector2f d1 =
			q1.pureSubtract(p1); // direction vector of segment S1
		Vector2f d2 = q2.pureSubtract(p2); // direction vector of S2

		Vector2f c1 = p1.pureAdd(d1.pureMul(s));
		Vector2f c2 = p2.pureAdd(d2.pureMul(t));

		return new Pair<Vector2f, Vector2f>(c1, c2);
	}


	// returns the square distance between two segments of
	// S1(s)=P1+s*(Q1-P1) and S2(t)=P2+t*(Q2-P2)
	public static float sqDistSegmentSegment(Vector2f p1, Vector2f q1,
						 Vector2f p2, Vector2f q2)
	{
		Pair<Vector2f, Vector2f> n =
			closestPtsSegmentSegment(p1, q1, p2, q2);
		Vector2f dist = n.fst().pureSubtract(n.snd());
		return Vector2f.dot(dist, dist);
	}


	private static int MAX_CAPSULE_COLLISIONS_SEGMENT_TO_CAPSULE = 3;
	// returns the t and the q (position) of intersection
	// tests if segment AB intersects with capsule CD with radius r
	public static Optional<Pair<Double, Vector2f>>
	intersectSegmentCapsule(Vector2f a, Vector2f b, Vector2f c, Vector2f d,
				float r)
	{
		@SuppressWarnings("unchecked")
		Optional<Pair<Double, Vector2f>> collisions[] =
			(Optional<Pair<Double, Vector2f>>[]) new Optional
				[MAX_CAPSULE_COLLISIONS_SEGMENT_TO_CAPSULE];

		System.out.println("Segmentcapsule --------------------------");

		collisions[0] =
			intersectSegmentCircle(a, b, new Circle(c, r));
		if (collisions[0].isPresent()) {
			System.out.println(collisions[0].get().fst()
					   + "collision 0");
		} else {
			System.out.println(collisions[0] + "collision 0");
		}

		Vector2f colaabbtopleft = new Vector2f(c.x, c.y + r);
		c.log("c log");
		d.log("d log");
		System.out.println(r + "  radius");
		float width = Math.abs(d.x - c.x);
		float height = r + r;
		collisions[1] = intersectSegmentAabb(
			a, b, new CollisionAabb(colaabbtopleft, width, height));

		if (collisions[1].isPresent()) {
			System.out.println(collisions[1].get().fst()
					   + "segment 1 test");
		} else {
			System.out.println(collisions[1]);
		}

		collisions[2] =
			intersectSegmentCircle(a, b, new Circle(d, r));

		if (collisions[2].isPresent()) {
			System.out.println(collisions[2].get().fst());
		} else {
			System.out.println(collisions[2]);
		}

		System.out.println("fuckitall-------------------------");

		boolean areCollisions = false;
		Optional<Pair<Double, Vector2f>> closest =
			Optional.of(new Pair<Double, Vector2f>(Double.MAX_VALUE,
							       new Vector2f()));

		for (int i = 0; i < MAX_CAPSULE_COLLISIONS_SEGMENT_TO_CAPSULE;
		     ++i) {
			if (collisions[i].isPresent()) {
				areCollisions = true;
				if (collisions[i].get().fst()
				    < closest.get().fst()) {
					closest = collisions[i];
				}
			}
		}

		if (!areCollisions)
			return Optional.empty();

		return closest;
	}
}

// OBB:
// https://gamedev.stackexchange.com/questions/25397/obb-vs-obb-collision-detection
