package poj.test;

import poj.linear.Vector2f;
import poj.Collisions.*;
import poj.Pair;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntersectCircleCapsuleTest
{
	@Test public void test()
	{
		{
			Circle s = new Circle(0, 0, 1);
			Vector2f d = new Vector2f(1, 0);
			CollisionAabb b = new CollisionAabb(2, 0, 1, 1);
			assertEquals(1d,
				     CollisionTests
					     .intersectMovingCircleAabb(s, d, b)
					     .get(),
				     0.00001d);
		}

		{
			Circle s = new Circle(1, 0, 1);
			Vector2f d = new Vector2f(1, 0);
			CollisionAabb b = new CollisionAabb(2, 0, 1, 1);
			assertEquals(0d,
				     CollisionTests
					     .intersectMovingCircleAabb(s, d, b)
					     .get(),
				     0.00001d);
		}

		{
			Circle s = new Circle(-100, -100, 1);
			Vector2f d = new Vector2f(1, 0);
			CollisionAabb b = new CollisionAabb(2, 0, 1, 1);
			assertFalse(CollisionTests
					    .intersectMovingCircleAabb(s, d, b)
					    .isPresent());
		}
	}
}
