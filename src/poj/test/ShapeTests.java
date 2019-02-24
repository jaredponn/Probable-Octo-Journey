package poj.test;

import static org.junit.Assert.*;
import org.junit.Test;

import poj.linear.*;
import poj.Collisions.*;

public class ShapeTests
{
	@Test public void shifting()
	{
		{
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 1);

			Polygon s = new Polygon(a, b);
			s.setFirstPositionAndShiftAll(3f, 3f);

			Vector2fTest.assertVectorsAreEqual(s.pts()[0], 3f, 3f);
			Vector2fTest.assertVectorsAreEqual(s.pts()[1], 4f, 4f);
		}

		{
			Vector2f a = new Vector2f(-3, -1);
			Vector2f b = new Vector2f(1, 1);

			Polygon s = new Polygon(a, b);
			s.setFirstPositionAndShiftAll(3f, 3f);

			Vector2fTest.assertVectorsAreEqual(s.pts()[0], 3f, 3f);
			Vector2fTest.assertVectorsAreEqual(s.pts()[1], 7f, 5f);
		}

		{
			Vector2f a = new Vector2f(-3, -1);
			Vector2f b = new Vector2f(1, 1);

			Polygon s = new Polygon(a, b);
			s.setFirstPositionAndShiftAll(-6f, -2f);

			Vector2fTest.assertVectorsAreEqual(s.pts()[0], -6f,
							   -2f);
			Vector2fTest.assertVectorsAreEqual(s.pts()[1], -2f, 0f);
		}
	}
	@Test public void furthestPointTest()
	{
		{
			Vector2f d = new Vector2f(1, 0);
			Vector2f a = new Vector2f(-3, -1);
			Vector2f b = new Vector2f(1, 1);

			Polygon s = new Polygon(a, b);

			Vector2fTest.assertVectorsAreEqual(
				s.furthestPointInDirection(d), 1f, 1f);
		}

		{
			Vector2f d = new Vector2f(-1, 0);
			Vector2f a = new Vector2f(-3, -1);
			Vector2f b = new Vector2f(1, 1);

			Polygon s = new Polygon(a, b);

			Vector2fTest.assertVectorsAreEqual(
				s.furthestPointInDirection(d), -3f, -1f);
		}
	}


	@Test public void areaofTriangle()
	{
		{

			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 1);
			Vector2f c = new Vector2f(1, 0);

			assertEquals(0.5f, Polygon.areaOfTriangle(a, b, c),
				     0.000001f);
		}

		{
			Vector2f a = new Vector2f(1, 1);
			Vector2f b = new Vector2f(2, 2);
			Vector2f c = new Vector2f(2, 1);

			assertEquals(0.5f, Polygon.areaOfTriangle(a, b, c),
				     0.000001f);
		}
	}

	@Test public void ispointinTri()
	{
		{

			Vector2f p = new Vector2f(0, 0);
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 1);
			Vector2f c = new Vector2f(1, 0);

			assertTrue(Polygon.isPointInTriangle(p, a, b, c));
		}

		{

			Vector2f p = new Vector2f(10, 0);
			Vector2f a = new Vector2f(0, 0);
			Vector2f b = new Vector2f(1, 1);
			Vector2f c = new Vector2f(1, 0);

			assertFalse(Polygon.isPointInTriangle(p, a, b, c));
		}
	}
}
