package poj.test;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import poj.Collisions.*;
import poj.linear.Vector2f;

public class CollisionTest
{

	private static float EPS = 0.00001f;
	@Test public void sameIsColliding()
	{
		CollisionBox a = new CollisionBox(0, 0, 10, 10);
		CollisionBox b = new CollisionBox(0, 0, 10, 10);

		assertTrue(a.isColliding(b));
		assertTrue(b.isColliding(a));

		assertEquals(1, 1);
	}

	@Test public void isColliding0()
	{
		{
			CollisionBox a = new CollisionBox(0, 0, 10, 10);
			CollisionBox b = new CollisionBox(10, 10, 1, 1);

			assertTrue(a.isColliding(b));
			assertTrue(b.isColliding(a));
		}

		{
			CollisionBox a = new CollisionBox(0, 0, 10, 10);
			CollisionBox b = new CollisionBox(-10, -10, 10, 10);

			assertTrue(a.isColliding(b));
			assertTrue(b.isColliding(a));
		}
	}

	@Test public void isColliding1()
	{
		CollisionBox a = new CollisionBox(0, 0, 10, 10);
		CollisionBox b = new CollisionBox(11, 11, 1, 1);

		assertFalse(a.isColliding(b));
		assertFalse(b.isColliding(a));
	}

	@Test public void settingTopLeftAndUpdate()
	{

		{

			CollisionBox a = new CollisionBox(3, 3, 1, 1);
			a.setTopLeftAndUpdateAllPoints(5, 5);

			Vector2f[] pts = a.pureGetPoints();

			assertVectorsAreEqual(pts[0], 5, 5);
			assertVectorsAreEqual(pts[1], 6, 5);
			assertVectorsAreEqual(pts[2], 5, 6);
			assertVectorsAreEqual(pts[3], 6, 6);
		}

		{

			CollisionBox a = new CollisionBox(3, 3, 2, 1);
			a.setTopLeftAndUpdateAllPoints(7, 5);

			Vector2f[] pts = a.pureGetPoints();

			assertVectorsAreEqual(pts[0], 7, 5);
			assertVectorsAreEqual(pts[1], 9, 5);
			assertVectorsAreEqual(pts[2], 7, 6);
			assertVectorsAreEqual(pts[3], 9, 6);
		}
	}

	@Test public void stationaryMovingIntersection()
	{
		{
			CollisionBox a = new CollisionBox(0, 0, 1, 1);
			CollisionBox b = new CollisionBox(0, 0, 1, 1);

			assertTrue(CollisionBox
					   .intersectionTimeOfMoving(
						   a, b, new Vector2f(0, 0),
						   new Vector2f(0, 0))
					   .isPresent());
		}

		{
			CollisionBox a = new CollisionBox(0.5f, 0.5f, 1, 1);
			CollisionBox b = new CollisionBox(0, 0, 1, 1);

			assertTrue(CollisionBox
					   .intersectionTimeOfMoving(
						   a, b, new Vector2f(0, 0),
						   new Vector2f(0, 0))
					   .isPresent());
		}

		{
			CollisionBox a = new CollisionBox(1, 1, 1, 1);
			CollisionBox b = new CollisionBox(0, 0, 1, 1);

			assertTrue(CollisionBox
					   .intersectionTimeOfMoving(
						   a, b, new Vector2f(0, 0),
						   new Vector2f(0, 0))
					   .isPresent());
		}
	}


	@Test public void oneMovingXIntersection()
	{
		CollisionBox a = new CollisionBox(-2, 0, 1, 1);
		CollisionBox b = new CollisionBox(0, 0, 1, 1);

		Optional<Double> val = CollisionBox.intersectionTimeOfMoving(
			a, b, new Vector2f(2, 0), new Vector2f(0, 0));

		assertTrue(val.isPresent());
		assertEquals(val.get(), 0.5f, 0.00001);
	}

	@Test public void oneMovingYIntersection()
	{
		CollisionBox a = new CollisionBox(0, 2, 1, 1);
		CollisionBox b = new CollisionBox(0, 0, 1, 1);

		Optional<Double> val = CollisionBox.intersectionTimeOfMoving(
			a, b, new Vector2f(0, -1), new Vector2f(0, 0));

		assertTrue(val.isPresent());
		assertEquals(val.get(), 1f, 0.00001);
	}

	@Test public void oneMovingXYIntersection()
	{
		CollisionBox a = new CollisionBox(0, 1.5f, 1, 1);
		CollisionBox b = new CollisionBox(0, 0, 1, 1);

		Optional<Double> val = CollisionBox.intersectionTimeOfMoving(
			a, b, new Vector2f(1f, -1), new Vector2f(0, 0));

		assertTrue(val.isPresent());
		System.out.println(" diagnoal final time: " + val.get());
		// assertEquals(val.get(), 1f, 1f);
	}

	private void assertVectorsAreEqual(Vector2f a, Vector2f b)
	{
		assertEquals(a.x, b.x, EPS);
		assertEquals(a.y, b.y, EPS);
	}

	private void assertVectorsAreEqual(Vector2f a, float x, float y)
	{
		assertVectorsAreEqual(a, new Vector2f(x, y));
	}
}
