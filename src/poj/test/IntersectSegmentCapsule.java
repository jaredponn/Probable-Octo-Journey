package poj.test;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Optional;

import poj.Collisions.*;
import poj.linear.*;
import poj.Pair;

public class IntersectSegmentCapsule
{
	@Test public void test()
	{
		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 0);

			Vector2f c = new Vector2f(2, 0);
			Vector2f d = new Vector2f(5, 0);
			float r = 1;

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentCapsule(a, b, c,
								       d, r);
			assertEquals(1d, val.get().fst(), 0.000d);
			Vector2fTest.assertVectorsAreEqual(val.get().snd(), 1f,
							   0f);
		}

		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(10, 0);

			Vector2f c = new Vector2f(2, 0);
			Vector2f d = new Vector2f(5, 0);
			float r = 1;

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentCapsule(a, b, c,
								       d, r);
			assertEquals(1d, val.get().fst(), 0.000001d);
			Vector2fTest.assertVectorsAreEqual(val.get().snd(), 1f,
							   0f);
		}
	}
}
