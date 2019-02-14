package poj.test;

import poj.linear.Vector2MatrixTransform;
import org.junit.Test;
import static org.junit.Assert.*;

public class Vector2MatrixTransformTest
{
	@Test public void detIDTest()
	{
		Vector2MatrixTransform d = new Vector2MatrixTransform();
		Vector2MatrixTransform test = new Vector2MatrixTransform();

		Vector2MatrixTransformTest.areMatricesEqual(d, test);
	}

	@Test public void inverseTest0()
	{
		Vector2MatrixTransform d = new Vector2MatrixTransform();
		d.setWithIndex(0, -0.00272790462825741978f);
		d.setWithIndex(1, 0.00039344778292174324f);
		d.setWithIndex(2, 0.0019147792102191504151f);
		d.setWithIndex(3, 0.12337735576860024393f);
		d.setWithIndex(4, -0.027410195543548112107f);
		d.setWithIndex(5, -0.000062951645267478917758f);
		d.setWithIndex(6, -0.053834148644572387818f);
		d.setWithIndex(7, 0.12314915605450563286f);
		d.setWithIndex(8, -0.00067410720140592007767f);

		Vector2MatrixTransform test = new Vector2MatrixTransform();
		test.setWithIndex(0, 1.f);
		test.setWithIndex(1, 9f);
		test.setWithIndex(2, 2f);
		test.setWithIndex(3, 3.3f);
		test.setWithIndex(4, 4f);
		test.setWithIndex(5, 9f);
		test.setWithIndex(6, 523f);
		test.setWithIndex(7, 12f);
		test.setWithIndex(8, 1f);

		assertEquals(38124.499999999999997f, (float)test.det(),
			     Vector2MatrixTransform.EPSILON);


		Vector2MatrixTransformTest.areMatricesEqual(
			d, test.unsafePureInverse());
	}

	public static void areMatricesEqual(Vector2MatrixTransform a,
					    Vector2MatrixTransform b)
	{
		for (int i = 0;
		     i < Vector2MatrixTransform.DIAGONAL_LENGTH
				 * Vector2MatrixTransform.DIAGONAL_LENGTH;
		     ++i) {
			assertEquals(a.getDataWithIndex(i),
				     b.getDataWithIndex(i),
				     Vector2MatrixTransform.EPSILON);
		}
	}
}
