package poj.test;

import org.junit.Test;
import static org.junit.Assert.*;

import poj.Collisions.*;

public class CircleCollisionTests
{
	@Test public void collisionTest()
	{

		{
			CollisionCircle a = new CollisionCircle(0, 0, 1);
			CollisionCircle b = new CollisionCircle(1, 0, 1);

			assertTrue(CollisionTests.areCirclesColliding(a, b));
		}

		{

			CollisionCircle a = new CollisionCircle(0, 0, 1);
			CollisionCircle b = new CollisionCircle(2, 0, 1);

			assertTrue(CollisionTests.areCirclesColliding(a, b));
		}

		{
			CollisionCircle a = new CollisionCircle(10, 10, 5);
			CollisionCircle b = new CollisionCircle(-10, 10, 15);

			assertTrue(CollisionTests.areCirclesColliding(a, b));
		}
	}

	@Test public void notCollisionTest()
	{

		{
			CollisionCircle a = new CollisionCircle(0, 0, 1);
			CollisionCircle b = new CollisionCircle(2, 0, 0);

			assertFalse(CollisionTests.areCirclesColliding(a, b));
		}

		{

			CollisionCircle a = new CollisionCircle(0, 0, 1);
			CollisionCircle b = new CollisionCircle(100, 0, 1);

			assertFalse(CollisionTests.areCirclesColliding(a, b));
		}

		{
			CollisionCircle a = new CollisionCircle(10, 10, 1);
			CollisionCircle b = new CollisionCircle(-10, -10, 15);

			assertFalse(CollisionTests.areCirclesColliding(a, b));
		}
	}

	// Vector2fTest.assertVectorsAreEqual();
}

