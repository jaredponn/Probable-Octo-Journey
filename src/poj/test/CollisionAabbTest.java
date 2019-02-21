package poj.test;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import poj.Collisions.*;
import poj.linear.Vector2f;

public class CollisionAabbTest
{

	@Test public void sameIsColliding()
	{
		CollisionAabb a = new CollisionAabb(0, 0, 10, 10);
		CollisionAabb b = new CollisionAabb(0, 0, 10, 10);

		assertTrue(a.isColliding(b));
		assertTrue(b.isColliding(a));

		assertEquals(1, 1);
	}

	@Test public void isColliding0()
	{
		{
			CollisionAabb a = new CollisionAabb(0, 0, 10, 10);
			CollisionAabb b = new CollisionAabb(10, 10, 1, 1);

			assertTrue(a.isColliding(b));
			assertTrue(b.isColliding(a));
		}

		{
			CollisionAabb a = new CollisionAabb(0, 0, 10, 10);
			CollisionAabb b = new CollisionAabb(-10, -10, 10, 10);

			assertTrue(a.isColliding(b));
			assertTrue(b.isColliding(a));
		}
	}

	@Test public void isColliding1()
	{
		CollisionAabb a = new CollisionAabb(0, 0, 10, 10);
		CollisionAabb b = new CollisionAabb(11, 11, 1, 1);
		assertFalse(a.isColliding(b));
		assertFalse(b.isColliding(a));
	}


	@Test public void settingTopLeftAndUpdate()
	{

		{

			CollisionAabb a = new CollisionAabb(3, 3, 1, 1);
			a.setTopLeftAndUpdateAllPoints(5, 5);

			Vector2f[] pts = a.pureGetPoints();

			Vector2fTest.assertVectorsAreEqual(pts[0], 5, 5);
			Vector2fTest.assertVectorsAreEqual(pts[1], 6, 5);
			Vector2fTest.assertVectorsAreEqual(pts[2], 5, 6);
			Vector2fTest.assertVectorsAreEqual(pts[3], 6, 6);
		}

		{

			CollisionAabb a = new CollisionAabb(3, 3, 2, 1);
			a.setTopLeftAndUpdateAllPoints(7, 5);

			Vector2f[] pts = a.pureGetPoints();

			Vector2fTest.assertVectorsAreEqual(pts[0], 7, 5);
			Vector2fTest.assertVectorsAreEqual(pts[1], 9, 5);
			Vector2fTest.assertVectorsAreEqual(pts[2], 7, 6);
			Vector2fTest.assertVectorsAreEqual(pts[3], 9, 6);
		}
	}

	@Test public void stationaryMovingIntersection()
	{
		{
			CollisionAabb a = new CollisionAabb(0, 0, 1, 1);
			CollisionAabb b = new CollisionAabb(0, 0, 1, 1);

			assertTrue(CollisionAabb
					   .intersectionTimeOfMoving(
						   a, b, new Vector2f(0, 0),
						   new Vector2f(0, 0))
					   .isPresent());
		}

		{
			CollisionAabb a = new CollisionAabb(0.5f, 0.5f, 1, 1);
			CollisionAabb b = new CollisionAabb(0, 0, 1, 1);

			assertTrue(CollisionAabb
					   .intersectionTimeOfMoving(
						   a, b, new Vector2f(0, 0),
						   new Vector2f(0, 0))
					   .isPresent());
		}

		{
			CollisionAabb a = new CollisionAabb(1, 1, 1, 1);
			CollisionAabb b = new CollisionAabb(0, 0, 1, 1);

			assertTrue(CollisionAabb
					   .intersectionTimeOfMoving(
						   a, b, new Vector2f(0, 0),
						   new Vector2f(0, 0))
					   .isPresent());
		}
	}


	@Test public void oneMovingXIntersection()
	{
		CollisionAabb a = new CollisionAabb(-2, 0, 1, 1);
		CollisionAabb b = new CollisionAabb(0, 0, 1, 1);

		Optional<Double> val = CollisionAabb.intersectionTimeOfMoving(
			a, b, new Vector2f(2, 0), new Vector2f(0, 0));

		assertTrue(val.isPresent());
		assertEquals(val.get(), 0.5f, 0.00001);
	}

	@Test public void oneMovingYIntersection()
	{
		CollisionAabb a = new CollisionAabb(0, 2, 1, 1);
		CollisionAabb b = new CollisionAabb(0, 0, 1, 1);

		Optional<Double> val = CollisionAabb.intersectionTimeOfMoving(
			a, b, new Vector2f(0, -1), new Vector2f(0, 0));

		assertTrue(val.isPresent());
		assertEquals(val.get(), 1f, 0.00001);
	}

	@Test public void oneMovingXYIntersection()
	{
		CollisionAabb a = new CollisionAabb(0, 1.5f, 1, 1);
		CollisionAabb b = new CollisionAabb(0, 0, 1, 1);

		Optional<Double> val = CollisionAabb.intersectionTimeOfMoving(
			a, b, new Vector2f(1f, -1), new Vector2f(0, 0));

		assertTrue(val.isPresent());
		System.out.println(" diagnoal final time: " + val.get());
		// assertEquals(val.get(), 1f, 1f);
	}


	@Test public void isNotColliding1()
	{
		CollisionAabb a = new CollisionAabb(0, 0, 10, 10);
		CollisionAabb b = new CollisionAabb(11, 11, 1, 1);
		assertFalse(CollisionAabb
				    .intersectionTimeOfMoving(
					    a, b, new Vector2f(0, 0),
					    new Vector2f(0, 0))
				    .isPresent());
	}

	@Test public void pointToAabbTest()
	{
		{
			CollisionAabb a = new CollisionAabb(0, 0, 1, 1);
			Vector2f p = new Vector2f(0.5f, -1f);
			Vector2fTest.assertVectorsAreEqual(
				CollisionTests.closestPtPointAabb(p, a), 0.5f,
				-1f);
		}

		{

			CollisionAabb a = new CollisionAabb(0, 0, 1, 1);
			Vector2f p = new Vector2f(0.5f, -300f);
			Vector2fTest.assertVectorsAreEqual(
				CollisionTests.closestPtPointAabb(p, a), 0.5f,
				-1f);
		}
	}
}
