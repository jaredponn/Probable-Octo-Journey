package poj;

import java.util.ArrayList;
import java.lang.Math;
public class Vector3f
{

	public float x;
	public float y;
	public float z;
	private static Matrix<Float> rotationMatrix;
	private static Matrix<Float> reflectionMatrix;
	private static Matrix<Float> translationMatrix;
	private static Matrix<Float> scalingMatrix;

	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;

		// TODO the matrix must be float, which is not generic ;(
		ArrayList<Float> tempRotationMatrix = new ArrayList<Float>();
		ArrayList<Float> tempReflectionMatrix = new ArrayList<Float>();
		ArrayList<Float> tempTranslationMatrix = new ArrayList<Float>();
		ArrayList<Float> tempScalingMatrix = new ArrayList<Float>();
		for (int i = 0; i < 9; ++i) {
			tempRotationMatrix.add(0.f);
			tempReflectionMatrix.add(0.f);
			tempTranslationMatrix.add(0.f);
			tempScalingMatrix.add(0.f);
		}
		rotationMatrix = new Matrix<Float>(tempRotationMatrix, 3);
		reflectionMatrix = new Matrix<Float>(tempReflectionMatrix, 3);
		reflectionMatrix = new Matrix<Float>(tempTranslationMatrix, 3);
		reflectionMatrix = new Matrix<Float>(tempScalingMatrix, 3);
	}
	public Vector3f()
	{
		// TODO the matrix must be float, which is not generic ;(
		ArrayList<Float> tempRotationMatrix = new ArrayList<Float>();
		ArrayList<Float> tempReflectionMatrix = new ArrayList<Float>();
		ArrayList<Float> tempTranslationMatrix = new ArrayList<Float>();
		ArrayList<Float> tempScalingMatrix = new ArrayList<Float>();
		for (int i = 0; i < 9; ++i) {
			tempRotationMatrix.add(0.f);
			tempReflectionMatrix.add(0.f);
			tempTranslationMatrix.add(0.f);
			tempScalingMatrix.add(0.f);
		}
		rotationMatrix = new Matrix<Float>(tempRotationMatrix, 3);
		reflectionMatrix = new Matrix<Float>(tempReflectionMatrix, 3);
		reflectionMatrix = new Matrix<Float>(tempTranslationMatrix, 3);
		reflectionMatrix = new Matrix<Float>(tempScalingMatrix, 3);
	}

	public static Vector3f add(final Vector3f a, final Vector3f b)
	{
		return new Vector3f((a.x + b.x), (a.y + b.y), (a.z + b.z));
	}

	public static Vector3f subtract(final Vector3f a, final Vector3f b)
	{

		return new Vector3f((a.x - b.x), (a.y - b.y), (a.z - b.z));
	}

	public static Vector3f scalarProduct(final Vector3f a,
					     final float scalar)
	{
		return new Vector3f((a.x * scalar), (a.y * scalar),
				    (a.z * scalar));
	}

	public static float dotProduct(final Vector3f a, final Vector3f b)
	{
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	public static float scalarValueOfVector(final Vector3f a)
	{
		return (float)Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
	}

	public static float angleBetweenTwoVector3(final Vector3f a,
						   final Vector3f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dotProduct(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	public static Vector3f normalOfVector3f(final Vector3f a)
	{

		if (a == new Vector3f(0, 0, 0)) {
			Logger.logMessage(
				"MAJOR ERROR in normalOfVector3f!!  the input vectors are 0!!",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		Vector3f tempVec = new Vector3f(1, 0, 0);
		if (a == tempVec) {
			return crossProduct(a, tempVec);
		} else {
			return crossProduct(a, new Vector3f(0, 1, 0));
		}
	}

	public static Vector3f crossProduct(final Vector3f a, final Vector3f b)
	{
		if (a == new Vector3f(0, 0, 0) || b == new Vector3f(0, 0, 0)) {
			Logger.logMessage(
				"MAJOR ERROR in crossProduct!! one of the vectors are 0!!",
				LOG_LEVEL.MAJOR_CRITICAL);
		}
		return new Vector3f(a.y * b.z - a.z * b.y,
				    a.z * b.x - a.x * b.z,
				    a.x * b.y - a.y * b.x);
	}

	// assume the matrix type is float??
	public static Vector3f matrixVector3fProduct(final Matrix<Float> a,
						     final Vector3f b)
	{
		// if column of matrix A is not 2 (2d vector)
		if (a.cols != 3 || a.rows != 3) {
			Logger.logMessage(
				"MAJOR ERROR in matrixVectorProduct! dimentions of the matrix and the vector does not match!",
				LOG_LEVEL.MAJOR_CRITICAL);
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

	// translation on 2D vector with X and Y translation value
	public static Vector2f translationVector2(Vector3f a, float xt,
						  float yt)
	{
		translationMatrix.setWithIndex(0, (float)1);
		translationMatrix.setWithIndex(4, (float)1);
		translationMatrix.setWithIndex(8, (float)1);

		translationMatrix.setWithIndex(1, (float)0);
		translationMatrix.setWithIndex(3, (float)0);
		translationMatrix.setWithIndex(6, (float)0);
		translationMatrix.setWithIndex(7, (float)0);

		translationMatrix.setWithIndex(2, xt);
		translationMatrix.setWithIndex(5, yt);
		Vector3f temp = matrixVector3fProduct(translationMatrix, a);
		return new Vector2f(temp.x, temp.y);
	}

	// scaling on 2D vector with X and Y scaling value
	public static Vector2f scalingVector2(Vector3f a, float xs, float ys)
	{
		scalingMatrix.setWithIndex(8, (float)1);

		scalingMatrix.setWithIndex(1, (float)0);
		scalingMatrix.setWithIndex(2, (float)0);
		scalingMatrix.setWithIndex(3, (float)0);
		scalingMatrix.setWithIndex(5, (float)0);
		scalingMatrix.setWithIndex(6, (float)0);
		scalingMatrix.setWithIndex(7, (float)0);

		scalingMatrix.setWithIndex(0, xs);
		scalingMatrix.setWithIndex(4, ys);
		Vector3f temp = matrixVector3fProduct(scalingMatrix, a);
		return new Vector2f(temp.x, temp.y);
	}
}
