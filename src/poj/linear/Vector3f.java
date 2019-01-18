package poj.linear;

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


	public final void matrixVector3fProduct(final Matrix<Float> a)
	{

		float xnew, ynew, znew;
		// loop through each row of the matrix
		xnew = a.getDataWithIndex(0) * this.x
		       + a.getDataWithIndex(1) * this.y
		       + a.getDataWithIndex(2) * this.z;
		ynew = a.getDataWithIndex(3) * this.x
		       + a.getDataWithIndex(4) * this.y
		       + a.getDataWithIndex(5) * this.z;
		znew = a.getDataWithIndex(6) * this.x
		       + a.getDataWithIndex(7) * this.y
		       + a.getDataWithIndex(8) * this.z;
		this.x = xnew;
		this.y = ynew;
		this.z = znew;
	}

	public final Vector3f add(final Vector3f a, final Vector3f b)
	{
		return new Vector3f((a.x + b.x), (a.y + b.y), (a.z + b.z));
	}

	public final Vector3f subtract(final Vector3f a, final Vector3f b)
	{

		return new Vector3f((a.x - b.x), (a.y - b.y), (a.z - b.z));
	}

	public final void add(final Vector3f a)
	{
		this.x += a.x;
		this.y += a.y;
		this.z += a.z;
	}

	public final void subtract(final Vector3f a)
	{

		this.x -= a.x;
		this.y -= a.y;
		this.z -= a.z;
	}
	public final Vector3f scalarProduct(final Vector3f a,
					    final float scalar)
	{
		return new Vector3f((a.x * scalar), (a.y * scalar),
				    (a.z * scalar));
	}

	public final float dotProduct(final Vector3f a, final Vector3f b)
	{
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	public final float scalarValueOfVector(final Vector3f a)
	{
		return (float)Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
	}

	public final float angleBetweenTwoVector3(final Vector3f a,
						  final Vector3f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dotProduct(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	public final Vector3f normalOfVector3f(final Vector3f a)
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

	public final Vector3f crossProduct(final Vector3f a, final Vector3f b)
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
}
