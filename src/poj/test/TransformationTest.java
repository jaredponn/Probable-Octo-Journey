package poj.test;

import poj.linear.MatrixTransformations;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import poj.linear.Matrix;
import poj.linear.Vector2f;
import poj.linear.Vector3f;

public class TransformationTest
{

	@Test public void translationTest()
	{

		/*
	ArrayList<Float> tempMatrixA = new ArrayList<Float>();
	for (int i = 1; i <= 9; ++i) {
		tempMatrixA.add((float)(i + i));
	}
	ArrayList<Float> tempMatrixB = new ArrayList<Float>();

	for (int i = 0; i < 6; ++i) {
		tempMatrixB.add((float)i * 2);
	}


	 System.out.println(Arrays.deepToString(tempMatrixA.toArray()));
	 System.out.println(Arrays.deepToString(tempMatrixB.toArray()));
	 Matrix<Float> matrixA = new Matrix<Float>(tempMatrixA, 3);
	 Matrix<Float> matrixB = new Matrix<Float>(tempMatrixB, 3);
	 identity.matrixMultiplication(matrixA);
	 System.out.println(
	 Arrays.deepToString(identity.m_matrix.toArray()));
	 System.out.println(
	 Arrays.deepToString(identity.m_matrix.toArray()));
	 Matrix<Float> matrixC =
	 MatrixTransformations.matrixMultiplication(matrixA,
	 matrixB);
	 */

		Vector2f myVect = new Vector2f(23, 30);

		MatrixTransformations identity = new MatrixTransformations(3);
		identity.setTranslationForVector2(6, 13);
		System.out.println(
			Arrays.deepToString(identity.m_matrix.toArray()));
		System.out.println("identity.rows = " + identity.rows
				   + "identity.cols = " + identity.cols);

		myVect.multiplyWithMatrix(identity);
		System.out.println("myVect.x = " + myVect.x
				   + "myVec.y = " + myVect.y);
	}
	@Test public void scaleTest()
	{
		Vector2f myVect = new Vector2f(23, 30);

		MatrixTransformations identity = new MatrixTransformations(3);
		identity.setScalingForVector2(6, 13);
		System.out.println(
			Arrays.deepToString(identity.m_matrix.toArray()));
		System.out.println("identity.rows = " + identity.rows
				   + "identity.cols = " + identity.cols);

		myVect.multiplyWithMatrix(identity);
		System.out.println("myVect.x = " + myVect.x
				   + "myVec.y = " + myVect.y);
	}

	// TODO do we want it to multiply with the transformation everytime?
	// will we even be doing complicated transformations???
	@Test public void scaleAndTranslateAndScaleTest()
	{
		Vector2f myVect = new Vector2f(23, 30);

		MatrixTransformations identity = new MatrixTransformations(3);
		identity.setScalingForVector2(6, 13);
		identity.setTranslationForVector2(6, 13);
		identity.setScalingForVector2(15, 30);
		System.out.println(
			Arrays.deepToString(identity.m_matrix.toArray()));
		System.out.println("identity.rows = " + identity.rows
				   + "identity.cols = " + identity.cols);

		myVect.multiplyWithMatrix(identity);
		System.out.println("myVect.x = " + myVect.x
				   + "myVec.y = " + myVect.y);
	}


	@Test public void rotationTest()
	{
		Vector2f myVect = new Vector2f(23, 30);

		MatrixTransformations identity = new MatrixTransformations(3);
		identity.setRotationForVector2Xaxis((float)1.3);
		System.out.println(
			Arrays.deepToString(identity.m_matrix.toArray()));
		myVect.multiplyWithMatrix(identity);

		System.out.println("myVect.x = " + myVect.x
				   + "myVec.y = " + myVect.y);
	}
}
