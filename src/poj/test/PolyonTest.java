package poj.test;

import static org.junit.Assert.*;
import org.junit.Test;

import poj.Collisions.Polygon;
import poj.Collisions.Rectangle;
import poj.linear.Vector2f;


public class PolyonTest
{
	@Test public void setAllPoints()
	{

		{
			Polygon p = new Polygon(new Vector2f(0, 0),
						new Vector2f(1, 1));

			p.setFirstPositionAndShiftAll(3, 3);

			Vector2fTest.assertVectorsAreEqual(p.pts()[0], 3, 3);
			Vector2fTest.assertVectorsAreEqual(p.pts()[1], 4, 4);
		}

		{
			Polygon p = new Polygon(new Vector2f(4, 4),
						new Vector2f(1, 1));

			p.setFirstPositionAndShiftAll(3, 3);

			Vector2fTest.assertVectorsAreEqual(p.pts()[0], 3, 3);
			Vector2fTest.assertVectorsAreEqual(p.pts()[1], 0, 0);
		}

		{
			Polygon p = new Polygon(new Vector2f(-4, -4),
						new Vector2f(1, 1));

			p.setFirstPositionAndShiftAll(3, 3);

			Vector2fTest.assertVectorsAreEqual(p.pts()[0], 3, 3);
			Vector2fTest.assertVectorsAreEqual(p.pts()[1], 8, 8);
		}
	}

	@Test public void getBounndingBoxTests()
	{
		{

			Polygon p = new Polygon(
				new Vector2f(0, 0), new Vector2f(0, 0),
				new Vector2f(1, 1), new Vector2f(0, 1),
				new Vector2f(1, 0), new Vector2f(0, 0));

			assertEquals(0,
				     p.calculateBoundingRectangle().getMinX(),
				     0.0001f);
			assertEquals(0,
				     p.calculateBoundingRectangle().getMinY(),
				     0.0001f);

			assertEquals(1,
				     p.calculateBoundingRectangle().getMaxX(),
				     0.0001f);
			assertEquals(1,
				     p.calculateBoundingRectangle().getMaxY(),
				     0.0001f);
		}

		{
			// clang-format off
			Polygon p = new Polygon(
				new Vector2f(0.25f,   1    ), new Vector2f(0.75f , 1    ),
				new Vector2f(0    ,   0.75f), new Vector2f(1     , 0.75f),
				new Vector2f(0    , 0.25f  ), new Vector2f(0.25f , 0    ),
				new Vector2f(0.75f, 0      ), new Vector2f(1     , 0.25f));

			assertEquals(0,
				     p.calculateBoundingRectangle().getMinX(),
				     0.0001f);
			assertEquals(0,
				     p.calculateBoundingRectangle().getMinY(),
				     0.0001f);

			assertEquals(1f,
				     p.calculateBoundingRectangle().getMaxX(),
				     0.0001f);
			assertEquals(1f,
				     p.calculateBoundingRectangle().getMaxY(),
				     0.0001f);
		 }
	}
}
