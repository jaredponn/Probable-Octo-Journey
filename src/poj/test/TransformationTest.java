package poj.test;

import poj.MatrixTransformations;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import poj.Matrix;
public class TransformationTest
{

	@Test public void trivialTest()
	{

		ArrayList<Float> tempMatrixA = new ArrayList<Float>();
		for (int i = 0; i < 9; ++i) {
			tempMatrixA.add((float)i);
		}
		ArrayList<Float> tempMatrixB = new ArrayList<Float>();

		for (int i = 0; i < 6; ++i) {
			tempMatrixB.add((float)i * 2);
		}

		System.out.println(Arrays.deepToString(tempMatrixA.toArray()));
		System.out.println(Arrays.deepToString(tempMatrixB.toArray()));
		Matrix<Float> matrixA = new Matrix<Float>(tempMatrixA, 3);
		Matrix<Float> matrixB = new Matrix<Float>(tempMatrixB, 3);
		Matrix<Float> matrixC =
			MatrixTransformations.matrixMultiplication(matrixA,
								   matrixB);
		System.out.println(
			Arrays.deepToString(matrixC.m_matrix.toArray()));
	}
}
