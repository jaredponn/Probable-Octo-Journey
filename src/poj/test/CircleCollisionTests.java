package poj.test;

import org.junit.Test;
import static org.junit.Assert.*;

import poj.Collisions.*;

public class CircleCollisionTests
{
	@Test public void collisionTest()
	{

		{
			Circle a = new Circle(0, 0, 1);
			Circle b = new Circle(1, 0, 1);

			assertTrue(CollisionTests.areCirclesColliding(a, b));
		}

		{

			Circle a = new Circle(0, 0, 1);
			Circle b = new Circle(2, 0, 1);

			assertTrue(CollisionTests.areCirclesColliding(a, b));
		}

		{
			Circle a = new Circle(10, 10, 5);
			Circle b = new Circle(-10, 10, 15);

			assertTrue(CollisionTests.areCirclesColliding(a, b));
		}
	}

	@Test public void notCollisionTest()
	{

		{
			Circle a = new Circle(0, 0, 1);
			Circle b = new Circle(2, 0, 0);

			assertFalse(CollisionTests.areCirclesColliding(a, b));
		}

		{

			Circle a = new Circle(0, 0, 1);
			Circle b = new Circle(100, 0, 1);

			assertFalse(CollisionTests.areCirclesColliding(a, b));
		}

		{
			Circle a = new Circle(10, 10, 1);
			Circle b = new Circle(-10, -10, 15);

			assertFalse(CollisionTests.areCirclesColliding(a, b));
		}
	}

	// Vector2fTest.assertVectorsAreEqual();
}

