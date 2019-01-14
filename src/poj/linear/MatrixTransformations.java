package poj.linear;

import poj.Logger;
import poj.LogLevels;
import java.util.ArrayList;
import java.util.Collections;
public class MatrixTransformations
{

	public final static Matrix<Float>
	matrixMultiplication(Matrix<Float> matrixA, Matrix<Float> matrixB)
	{
		if (matrixA.cols != matrixB.rows) {
			Logger.lassert(
				true,
				"MAJOR ERROR in matrixMultiplicationWithSquaredMatrices! A's col number does not match with B's row number");
		}

		ArrayList<Float> tempArrayList = new ArrayList<Float>(
			Collections.nCopies(matrixA.rows * matrixB.cols, 0.f));
		Matrix<Float> matrixC = new Matrix<Float>(
			tempArrayList, matrixA.rows, matrixB.cols);

		for (int i = 0; i < matrixA.rows; ++i) {
			for (int j = 0; j < matrixB.cols; ++j) {
				float tempValue = 0;
				for (int m = 0; m < matrixA.cols; ++m) {
					tempValue +=
						matrixA.getDataWithMatrixCord(
							new MatrixCord(i, m))
						* matrixB.getDataWithMatrixCord(
							  new MatrixCord(m, j));
				}
				matrixC.setWithMatrixCord(new MatrixCord(i, j),
							  tempValue);
			}
		}
		return matrixC;
	}

	public final static Matrix<Float>
	createRotationMatrixVector2(float radian)
	{
		ArrayList<Float> tempArrayList = new ArrayList<Float>();

		tempArrayList.add((float)Math.cos(radian));
		tempArrayList.add((float)-Math.sin(radian));
		tempArrayList.add((float)Math.sin(radian));
		tempArrayList.add((float)Math.cos(radian));
		return new Matrix<Float>(tempArrayList, 2);
	}

	public final static Matrix<Float>
	createReflectoinMatrixVector2(float slope)
	{
		ArrayList<Float> tempArrayList = new ArrayList<Float>();

		tempArrayList.add((float)(1 - slope * slope)
				  / (1 + slope * slope));
		tempArrayList.add((float)(2 * slope) / (1 + slope * slope));
		tempArrayList.add((float)(2 * slope) / (1 + slope * slope));
		tempArrayList.add((float)(slope * slope - 1)
				  / (1 + slope * slope));
		return new Matrix<Float>(tempArrayList, 2);
	}

	public final static Vector2f
	matrixVector2fProduct(final Matrix<Float> a, final Vector2f b)
	{
		// if column of matrix A is not 2 (2d vector)
		if (a.cols != 2 || a.rows != 2) {
			Logger.logMessage(
				"MAJOR ERROR in matrixVectorProduct! dimentions of the matrix and the vector does not match!",
				LogLevels.MAJOR_CRITICAL);
		}
		float xnew, ynew;
		// loop through each row of the matrix
		xnew = a.getDataWithIndex(0) * b.x
		       + a.getDataWithIndex(1) * b.y;
		ynew = a.getDataWithIndex(2) * b.x
		       + a.getDataWithIndex(3) * b.y;
		return new Vector2f(xnew, ynew);
	}


	public final static Vector3f
	matrixVector3fProduct(final Matrix<Float> a, final Vector3f b)
	{
		// if column of matrix A is not 2 (2d vector)
		if (a.cols != 3 || a.rows != 3) {
			Logger.logMessage(
				"MAJOR ERROR in matrixVectorProduct! dimentions of the matrix and the vector does not match!",
				LogLevels.MAJOR_CRITICAL);
		}
		float xnew, ynew, znew;
		// loop through each row of the matrix
		xnew = a.getDataWithIndex(0) * b.x + a.getDataWithIndex(1) * b.y
		       + a.getDataWithIndex(2) * b.z;
		ynew = a.getDataWithIndex(3) * b.x + a.getDataWithIndex(4) * b.y
		       + a.getDataWithIndex(5) * b.z;
		znew = a.getDataWithIndex(6) * b.x + a.getDataWithIndex(7) * b.y
		       + a.getDataWithIndex(8) * b.z;
		;
		return new Vector3f(xnew, ynew, znew);
	}

	public final static Matrix<Float>
	createTranslationMatrixVector3(float xt, float yt)
	{
		ArrayList<Float> tempArrayList = new ArrayList<Float>();
		tempArrayList.add((float)1);
		tempArrayList.add((float)0);
		tempArrayList.add(xt);
		tempArrayList.add((float)0);
		tempArrayList.add((float)1);
		tempArrayList.add(yt);
		tempArrayList.add((float)0);
		tempArrayList.add((float)0);
		tempArrayList.add((float)1);
		return new Matrix<Float>(tempArrayList, 3);
	}


	public final static Matrix<Float> createScalingMatrixVector3(float xs,
								     float ys)
	{
		ArrayList<Float> tempArrayList = new ArrayList<Float>();


		tempArrayList.add(xs);
		tempArrayList.add((float)0);
		tempArrayList.add((float)0);
		tempArrayList.add((float)0);
		tempArrayList.add(ys);
		tempArrayList.add((float)0);
		tempArrayList.add((float)0);
		tempArrayList.add((float)0);
		tempArrayList.add((float)1);

		return new Matrix<Float>(tempArrayList, 3);
	}

	// translation on 2D vector with X and Y translation value
	public final static Vector2f
	translationVector2(final Matrix<Float> translationMatrix,
			   final Vector3f a)
	{
		Vector3f temp = matrixVector3fProduct(translationMatrix, a);
		return new Vector2f(temp.x, temp.y);
	}

	// scaling on 2D vector with X and Y scaling value
	public final static Vector2f
	scalingVector2(final Matrix<Float> scalingMatrix, final Vector3f a,
		       float xs, float ys)
	{
		Vector3f temp = matrixVector3fProduct(scalingMatrix, a);
		return new Vector2f(temp.x, temp.y);
	}
}
