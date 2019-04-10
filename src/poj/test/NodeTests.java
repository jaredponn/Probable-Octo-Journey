package poj.test;

import poj.linear.Vector2f;
import poj.Collisions.*;
import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.*;

public class NodeTests
{

	@Test public void trueRectTests()
	{
		Node n = new Node();

		Polygon r1 =
			new Polygon(new Vector2f(0, 1), new Vector2f(0, 0),
				    new Vector2f(1, 1), new Vector2f(1, 0));
		Polygon r2 =
			new Polygon(new Vector2f(1, 2), new Vector2f(1, 1),
				    new Vector2f(2, 2), new Vector2f(2, 1));

		Polygon r3 =
			new Polygon(new Vector2f(-1, -2), new Vector2f(-1, -1),
				    new Vector2f(-2, -2), new Vector2f(-2, -1));

		Polygon r4 =
			new Polygon(new Vector2f(-4, -5), new Vector2f(-4, -4),
				    new Vector2f(-5, -5), new Vector2f(-5, -4));

		n.insert(r1);
		assertTrue(areNodesbounds(n, 0, 0, 1, 1));
		n.insert(r2);
		assertTrue(areNodesbounds(n, 0, 0, 2, 2));

		assertTrue(n.leaf.leftLeaf == r2);
		assertTrue(n.leaf.rightLeaf == r1);

		// System.out.println("PREINSERTION:");
		// n.print();

		n.insert(r3);
		n.insert(r4);
		System.out.println("root:");
		n.print();
		System.out.println("left:");
		n.left.print();
		System.out.println("right:");
		n.right.print();
	}

	public static boolean areNodesbounds(Node n, float minx, float miny,
					     float maxx, float maxy)
	{
		return fEquals(n.bounds.getMin().x, minx)
			&& fEquals(n.bounds.getMin().y, miny)
			&& fEquals(n.bounds.getMax().x, maxx)
			&& fEquals(n.bounds.getMax().y, maxy);
	}


	public static boolean fEquals(float a, float b)
	{
		return Math.abs(a - b) <= 0.0001;
	}
}
