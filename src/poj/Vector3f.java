package poj;

import poj.Logger;
import poj.LogLevels;
import java.lang.Math;
public class Vector3f
{

	public float x;
	public float y;
	public float z;

	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3f()
	{
	}

	public final static Vector3f add(final Vector3f a, final Vector3f b)
	{
		return new Vector3f((a.x + b.x), (a.y + b.y), (a.z + b.z));
	}

	public final static Vector3f subtract(final Vector3f a,
					      final Vector3f b)
	{

		return new Vector3f((a.x - b.x), (a.y - b.y), (a.z - b.z));
	}

	public final static Vector3f scalarProduct(final Vector3f a,
						   final float scalar)
	{
		return new Vector3f((a.x * scalar), (a.y * scalar),
				    (a.z * scalar));
	}

	public final static float dotProduct(final Vector3f a, final Vector3f b)
	{
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	public final static float scalarValueOfVector(final Vector3f a)
	{
		return (float)Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
	}

	public final static float angleBetweenTwoVector3(final Vector3f a,
							 final Vector3f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dotProduct(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	public final static Vector3f normalOfVector3f(final Vector3f a)
	{

		if (a == new Vector3f(0, 0, 0)) {
			Logger.logMessage(
				"MAJOR ERROR in normalOfVector3f!!  the input vectors are 0!!",
				LogLevels.MAJOR_CRITICAL);
		}
		Vector3f tempVec = new Vector3f(1, 0, 0);
		if (a == tempVec) {
			return crossProduct(a, tempVec);
		} else {
			return crossProduct(a, new Vector3f(0, 1, 0));
		}
	}

	public final static Vector3f crossProduct(final Vector3f a,
						  final Vector3f b)
	{
		if (a == new Vector3f(0, 0, 0) || b == new Vector3f(0, 0, 0)) {
			Logger.logMessage(
				"MAJOR ERROR in crossProduct!! one of the vectors are 0!!",
				LogLevels.MAJOR_CRITICAL);
		}
		return new Vector3f(a.y * b.z - a.z * b.y,
				    a.z * b.x - a.x * b.z,
				    a.x * b.y - a.y * b.x);
	}

	// assume the matrix type is float??
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
}
