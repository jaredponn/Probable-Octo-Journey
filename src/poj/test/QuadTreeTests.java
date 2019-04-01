package poj.test;

import org.junit.Test;
import static org.junit.Assert.*;
import poj.Collisions.*;
import poj.linear.*;

public class QuadTreeTests
{

	public static void printQObjects(QuadTree qt)
	{
		System.out.println("printing qt obj");
		System.out.println(qt.objects.size());
		for (CollisionShape c : qt.objects) {
			System.out.println(c.toString());
		}
		System.out.println("end printing qt obj");
	}


	@Test public void simpleTest()
	{
		Polygon p = new Polygon(new Vector2f(0, 0), new Vector2f(1, 1),
					new Vector2f(1, 0), new Vector2f(0, 1));

		QuadTree qt =
			new QuadTree(0, new Rectangle(0, 0, 10000, 10000));
		for (int i = 0; i < 20; ++i) {
			qt.insert(p.pureSetFirstPositionAndShiftAll(i, i));
		}

		for (int i = 0; i < 4; ++i) {
			assertTrue(qt.nodes[i] == null);
		}
		assertEquals(20, qt.objects.size());

		qt.insert(p.pureSetFirstPositionAndShiftAll(30, 30));
		printQObjects(qt);
		// printQObjects(qt.nodes[0]);
		// printQObjects(qt.nodes[1]); printQObjects(qt.nodes[2]);
		// printQObjects(qt.nodes[3]);
	}
}
