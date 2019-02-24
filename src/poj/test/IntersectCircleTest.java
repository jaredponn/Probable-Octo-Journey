package poj.test;

import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.*;

import poj.linear.*;
import poj.Pair;
import poj.Collisions.*;

public class IntersectCircleTest
{
	@Test public void segCircle()
	{
		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 0);
			Circle s = new Circle(2, 0, 1);

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentCircle(a, b, s);

			assertEquals(1d, val.get().fst(), 0.0000001d);
			Vector2fTest.assertVectorsAreEqual(val.get().snd(), 1f,
							   0f);
		}

		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 0);
			Circle s = new Circle(3, 0, 1);

			Optional<Pair<Double, Vector2f>> val =
				CollisionTests.intersectSegmentCircle(a, b, s);

			assertFalse(val.isPresent());
		}
	}
}
