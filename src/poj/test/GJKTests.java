package poj.test;

import poj.linear.Vector2f;
import poj.Collisions.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class GJKTests
{

	@Test public void trueRectTests()
	{
		{

			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}


		{ // x direction shift +
			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(1, 1), new Vector2f(1, 0),
				new Vector2f(2, 1), new Vector2f(2, 0));

			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}

		{ // y directionshift +

			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(0, 2), new Vector2f(0, 1),
				new Vector2f(1, 2), new Vector2f(1, 1));

			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}

		{ // xy directionshift+

			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(1, 2), new Vector2f(1, 1),
				new Vector2f(2, 2), new Vector2f(2, 1));

			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}

		{ // x direction shift -
			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(-1, 1), new Vector2f(-1, 0),
				new Vector2f(0, 1), new Vector2f(0, 0));


			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}

		{ // y directionshift -

			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(0, 0), new Vector2f(0, -1),
				new Vector2f(1, 0), new Vector2f(1, -1));


			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}

		{ // xy directionshift-

			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(-1, 0), new Vector2f(-1, -1),
				new Vector2f(0, 0), new Vector2f(0, -1));


			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}
	}

	@Test public void falseRectTests()
	{
		{
			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(2, 1), new Vector2f(2, 0),
				new Vector2f(3, 1), new Vector2f(3, 0));

			GJK gjk = new GJK();

			assertFalse(gjk.areColliding(r1, r2));
			assertFalse(gjk.areColliding(r2, r1));
		}

		{ // diagnoal down left
			Polygon r1 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			Polygon r2 = new Polygon(
				new Vector2f(-3, -2), new Vector2f(-3, -3),
				new Vector2f(-2, -2), new Vector2f(-2, -3));

			GJK gjk = new GJK();

			assertFalse(gjk.areColliding(r1, r2));
			assertFalse(gjk.areColliding(r2, r1));
		}
	}
	@Test public void fancyShape()
	{


		{
			Polygon r1 = new Polygon(
				new Vector2f(0, 0), new Vector2f(0, 1),
				new Vector2f(1, 2), new Vector2f(2, 1),
				new Vector2f(2, 0));

			Polygon r2 = new Polygon(new Vector2f(1.5f, 1.5f),
						 new Vector2f(1.5f, 3),
						 new Vector2f(4, 4));

			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}

		{
			Polygon r1 = new Polygon(
				new Vector2f(0, 0), new Vector2f(0, 1),
				new Vector2f(1, 2), new Vector2f(2, 1),
				new Vector2f(2, 0));

			Polygon r2 = new Polygon(new Vector2f(1.6f, 1.6f),
						 new Vector2f(1.6f, 3),
						 new Vector2f(4, 4));

			GJK gjk = new GJK();

			assertFalse(gjk.areColliding(r1, r2));
			assertFalse(gjk.areColliding(r2, r1));
		}

		{
			Polygon r1 = new Polygon(new Vector2f(0, -1),
						 new Vector2f(1, 3),
						 new Vector2f(3, 1));

			Polygon r2 = new Polygon(
				new Vector2f(1, 1), new Vector2f(4, 3),
				new Vector2f(2, -2), new Vector2f(5, -1));

			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r1, r2));
			assertTrue(gjk.areColliding(r2, r1));
		}
	}

	@Test public void circleAgainstPolygon()
	{

		{
			Circle r1 = new Circle(new Vector2f(0, 0), 1f);

			Polygon r2 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			GJK gjk = new GJK();

			assertTrue(gjk.areColliding(r2, r1));

			// TODO FIXME
			assertTrue(gjk.areColliding(r1, r2));
		}

		{
			Circle r1 = new Circle(new Vector2f(20, 20), 1f);

			Polygon r2 = new Polygon(
				new Vector2f(0, 1), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(1, 0));

			GJK gjk = new GJK();

			assertFalse(gjk.areColliding(r2, r1));
			assertFalse(gjk.areColliding(r1, r2));
		}
	}
}
