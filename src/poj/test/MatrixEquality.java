package poj.test;
/**
 * Matrix equality test
 *
 * date March 10, 2019
 * @author Haiyang
 * @version 1.0
 */

import poj.linear.MatrixTransformations;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import poj.linear.Matrix;
import poj.linear.Vector2f;
import poj.linear.Vector3f;
public class MatrixEquality
{
	/*
	 * tests
	 */
	@Test public void trivialTest()
	{
		ArrayList<Float> tempMatrixA = new ArrayList<Float>();
		for (int i = 0; i < 4; ++i) {
			tempMatrixA.add((float)i);
		}
		ArrayList<Float> tempMatrixB = new ArrayList<Float>();

		for (int i = 0; i < 4; ++i) {
			tempMatrixB.add((float)(2 * i / 2));
		}


		System.out.println(Arrays.deepToString(tempMatrixA.toArray()));
		System.out.println(Arrays.deepToString(tempMatrixB.toArray()));
		System.out.println();
		/*
		Matrix<Float> matrixA = new Matrix<Float>(tempMatrixA, 3);
		Matrix<Float> matrixB = new Matrix<Float>(tempMatrixB, 3);
		System.out.println(
			Arrays.deepToString(identity.m_matrix.toArray()));
		System.out.println(
			Arrays.deepToString(identity.m_matrix.toArray()));
		Matrix<Float> matrixC =
			MatrixTransformations.matrixMultiplication(matrixA,
								   matrixB);
								   */
	}
}
