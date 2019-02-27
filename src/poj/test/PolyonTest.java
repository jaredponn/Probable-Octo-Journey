package poj.test;

import static org.junit.Assert.*;
import org.junit.Test;

import poj.Collisions.Polygon;
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
}
