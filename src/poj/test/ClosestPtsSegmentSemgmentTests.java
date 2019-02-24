package poj.test;

import poj.linear.Vector2f;
import poj.Collisions.*;
import poj.Pair;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClosestPtsSegmentSemgmentTests
{
	@Test public void test()
	{
		Vector2f p1 = new Vector2f(0, 0);
		Vector2f q1 = new Vector2f(1, 0);

		Vector2f p2 = new Vector2f(1, 1);
		Vector2f q2 = new Vector2f(2, 1);

		Pair<Vector2f, Vector2f> n =
			CollisionTests.closestPtsSegmentSegment(p1, q1, p2, q2);

		Vector2fTest.assertVectorsAreEqual(n.fst(), 1, 0);
		Vector2fTest.assertVectorsAreEqual(n.snd(), 1, 1);
	}
}
