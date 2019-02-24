package poj.test;

import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.*;

import poj.linear.*;
import poj.Pair;
import poj.Collisions.*;

public class IntersectAabbTest
{
	@Test public void segAabb()
	{
		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 0);
			CollisionAabb aabb = new CollisionAabb(1, 0, 0, 0);

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentAabb(a, b, aabb);

			assertEquals(1d, val.get().fst(), 0.0000001d);
			Vector2fTest.assertVectorsAreEqual(val.get().snd(), 1f,
							   0f);
		}

		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 0);
			CollisionAabb aabb = new CollisionAabb(2, 0, 0, 0);

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentAabb(a, b, aabb);

			assertFalse(val.isPresent());
		}

		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(-0.70710f, -0.70710f);
			CollisionAabb aabb = new CollisionAabb(
				-0.70710f, 0.04289f, 5.08f, 1.5f);

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentAabb(a, b, aabb);

			System.out.println((val.get().fst()));
		}

		{
			Vector2f a = new Vector2f(1.8155929f, 5.8476357f);
			Vector2f b = new Vector2f(0.8155929f, 5.8476357f);
			CollisionAabb aabb =
				new CollisionAabb(4.375f, 6.75f, 0, 1.5f);

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentAabb(a, b, aabb);

			assertFalse(val.isPresent());
		}

		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(2, 0);
			CollisionAabb aabb = new CollisionAabb(2.1f, 0, 1, 1);

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentAabb(a, b, aabb);

			// assertEquals(1, val.get().fst, 0.00001d);
		}
	}
}
