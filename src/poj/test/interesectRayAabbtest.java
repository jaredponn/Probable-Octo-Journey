package poj.test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Optional;
import poj.linear.*;
import poj.Collisions.*;
import poj.Pair;

public class interesectRayAabbtest
{
	@Test public void test()
	{
		{
			Vector2f p = new Vector2f(0, 0);
			Vector2f d = new Vector2f(1, 0);
			CollisionAabb aabb = new CollisionAabb(0, 0, 1, 1);

			Optional<Pair<Double, Vector2f>> tmp =
				CollisionTests.intersectRayAabb(p, d, aabb);

			assertEquals(tmp.get().fst, 0d, 0.00000d);
			Vector2fTest.assertVectorsAreEqual(tmp.get().snd, 0, 0);
		}

		{
			Vector2f p = new Vector2f(0, 0);
			Vector2f d = new Vector2f(1, 0);
			CollisionAabb aabb = new CollisionAabb(2, 0, 1, 1);

			Optional<Pair<Double, Vector2f>> tmp =
				CollisionTests.intersectRayAabb(p, d, aabb);

			assertEquals(tmp.get().fst, 2d, 0.00000d);
			Vector2fTest.assertVectorsAreEqual(tmp.get().snd, 2, 0);
		}
		{

			Vector2f p = new Vector2f(4.380751f, -0.04112135f);
			Vector2f d =
				new Vector2f(0f, 0.0021213202f).pureNormalize();
			CollisionAabb aabb =
				new CollisionAabb(4.375f, 8.0f, 0.75f, 2.0f);

			Optional<Pair<Double, Vector2f>> tmp =
				CollisionTests.intersectRayAabb(p, d, aabb);

			assertEquals(6.04d, tmp.get().fst, 0.01d);
		}

		{

			Vector2f p = new Vector2f(0f, 0f);
			Vector2f d = new Vector2f(0f, 0f);
			CollisionAabb aabb =
				new CollisionAabb(4.375f, 8.0f, 0.75f, 2.0f);

			Optional<Pair<Double, Vector2f>> tmp =
				CollisionTests.intersectRayAabb(p, d, aabb);

			assertFalse(tmp.isPresent());
		}

		{

			Vector2f p = new Vector2f(1.766f, 6.2419f);
			Vector2f d = new Vector2f(-0.7070f, +0.7070f);
			CollisionAabb aabb =
				new CollisionAabb(4.375f, 8.0f, 0.75f, 2.0f);

			Optional<Pair<Double, Vector2f>> tmp =
				CollisionTests.intersectRayAabb(p, d, aabb);
			System.out.println(aabb.toString());
			System.out.println(tmp.get().fst);
		}
	}
}

/*
intesrectRayAabb
Vector2f: x =  4.3854046, y = -0.06007915 p
Vector2f: x =  0.0, y = 0.0021213202 d
Collision Body: min = (3.625, 5.25), max = (5.875, 8.75), width = 0.75, height
= 2.0 2503.1953125 face
*/
