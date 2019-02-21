package poj.test;

import org.junit.Test;
import static org.junit.Assert.*;

import poj.Collisions.*;
import poj.linear.*;

public class CollisionTestUtilTests
{

	@Test public void closestPtPointSegment()
	{
		Vector2f a = new Vector2f(0, 0);
		Vector2f b = new Vector2f(2, 0);
		Vector2f c = new Vector2f(1, 1);

		Vector2fTest.assertVectorsAreEqual(
			CollisionTests.closestPtPointSegment(c, a, b), 1f, 0f);
	}


	@Test public void distPointSegment()
	{

		Vector2f a = new Vector2f(0, 0);
		Vector2f b = new Vector2f(2, 0);
		Vector2f c = new Vector2f(1, 1);

		assertEquals(CollisionTests.distPointSegment(a, b, c), 1f,
			     0.00001f);
	}
}
