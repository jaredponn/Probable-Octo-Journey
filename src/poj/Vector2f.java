package poj;

import java.util.ArrayList;
import java.lang.Math;
public class Vector2f
{
	public float x;
	public float y;
	private static Matrix<Float> rotationMatrix;
	private static Matrix<Float> reflectionMatrix;

	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;

		// TODO the matrix must be float, which is not generic ;(
		ArrayList<Float> tempRotationMatrix = new ArrayList<Float>();
		ArrayList<Float> tempReflectionMatrix = new ArrayList<Float>();
		for (int i = 0; i < 4; ++i) {
			tempRotationMatrix.add(0.f);
			tempReflectionMatrix.add(0.f);
		}
		rotationMatrix = new Matrix<Float>(tempRotationMatrix, 2);
		reflectionMatrix = new Matrix<Float>(tempReflectionMatrix, 2);
	}
	public Vector2f()
	{
		// TODO the matrix must be float, which is not generic ;(
		ArrayList<Float> tempRotationMatrix = new ArrayList<Float>();
		ArrayList<Float> tempReflectionMatrix = new ArrayList<Float>();
		for (int i = 0; i < 4; ++i) {
			tempRotationMatrix.add(0.f);
			tempReflectionMatrix.add(0.f);
		}
		rotationMatrix = new Matrix<Float>(tempRotationMatrix, 2);
		reflectionMatrix = new Matrix<Float>(tempReflectionMatrix, 2);
	}

	public static Vector2f add(final Vector2f a, final Vector2f b)
	{
		return new Vector2f((a.x + b.x), (a.y + b.y));
	}

	public static Vector2f subtract(final Vector2f a, final Vector2f b)
	{
		return new Vector2f((a.x - b.x), (a.y - b.y));
	}

	public static Vector2f scalarProduct(final Vector2f a,
					     final float scalar)
	{
		return new Vector2f((a.x * scalar), (a.y * scalar));
	}

	public static float dotProduct(final Vector2f a, final Vector2f b)
	{
		return a.x * b.x + a.y * b.y;
	}

	public static float scalarValueOfVector(final Vector2f a)
	{
		return (float)Math.sqrt(a.x * a.x + a.y * a.y);
	}

	public static float angleBetweenTwoVector2(final Vector2f a,
						   final Vector2f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dotProduct(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	public static Vector2f normalOfVector2f(final Vector2f a)
	{
		return new Vector2f(a.y, -a.x);
	}

	// assume the matrix type is float??
	public static Vector2f matrixVector2fProduct(final Matrix<Float> a,
						     final Vector2f b)
	{
		// if column of matrix A is not 2 (2d vector)
		if (a.cols != 2 || a.rows != 2) {
			Logger.logMessage(
				"MAJOR ERROR in matrixVectorProduct! dimentions of the matrix and the vector does not match!",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		float xnew, ynew;
		// loop through each row of the matrix
		xnew = a.getDataWithIndex(0) * b.x
		       + a.getDataWithIndex(1) * b.y;
		ynew = a.getDataWithIndex(2) * b.x
		       + a.getDataWithIndex(3) * b.y;
		return new Vector2f(xnew, ynew);
	}

	public static Vector2f rotationOfVectorByRadians(final Vector2f a,
							 float radian)
	{
		rotationMatrix.setWithIndex(0, (float)Math.cos(radian));
		rotationMatrix.setWithIndex(1, (float)-Math.sin(radian));
		rotationMatrix.setWithIndex(2, (float)Math.sin(radian));
		rotationMatrix.setWithIndex(3, (float)Math.cos(radian));
		return matrixVector2fProduct(rotationMatrix, a);
	}
	public static Vector2f reflectionOverLineWithSlope(final Vector2f a,
							   float slope)
	{
		reflectionMatrix.setWithIndex(0, (float)(1 - slope * slope)
							 / (1 + slope * slope));
		reflectionMatrix.setWithIndex(1, (float)(2 * slope)
							 / (1 + slope * slope));
		reflectionMatrix.setWithIndex(2, (float)(2 * slope)
							 / (1 + slope * slope));
		reflectionMatrix.setWithIndex(3, (float)(slope * slope - 1)
							 / (1 + slope * slope));
		return matrixVector2fProduct(reflectionMatrix, a);
	}
}
// add, minus, scalarProduct, MatrixVectorMultiplication??, dot product,
// Crossproduct??
// normalize
// TODO vector transformation 2d
// TODO matrix multiplication
// TODO transformation matrix
