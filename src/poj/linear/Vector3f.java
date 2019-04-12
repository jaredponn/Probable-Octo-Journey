package poj.linear;
/**
 * Vector3f
 *
 * date March 10, 2019
 * @author Haiyang He
 * @version 1.0
 */

import java.lang.Math;

import poj.Logger.Logger;
public class Vector3f
{

	private static float EPSILON = 0.00000001f;
	public float x;
	public float y;
	public float z;

	/**
	 * Constructs the vector3f object with x, y and z values
	 * @param  x	float, the x value of the vector
	 * @param  y	float, the y value of the vector
	 * @param  z	float, the z value of the vector
	 */
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * Copy constructor
	 * @param  newVector	Vector3f, the vector that want to be deep copied
	 */
	public Vector3f(Vector3f newVector)
	{
		this.x = newVector.getX();
		this.y = newVector.getY();
		this.z = newVector.getZ();
	}
	public Vector3f()
	{
	}


	/**
	 * add this vector3f with another vector and return this pointer
	 * @param  a	Vector3f, the vector that will be added
	 *   @return     pointer to this Vector3f
	 */
	public Vector3f add(final Vector3f a)
	{
		this.x += a.x;
		this.y += a.y;
		this.z += a.z;

		return this;
	}


	/**
	 * add a scalar value to every value of this vector3f and returns this
	 * pointer
	 * @param  n	float, the scalar value that will be added
	 *   @return      pointer to this Vector3f
	 */
	public Vector3f add(float n)
	{
		this.x += n;
		this.y += n;
		this.z += n;

		return this;
	}

	/**
	 * add a scalar value to every value of this vector3f and returns the
	 * vector
	 * @param  a	Vector3f, the vector that will be added
	 *   @return      Vector3f
	 */
	final public Vector3f pureAdd(final Vector3f a)
	{
		return new Vector3f(this.x + a.getX(), this.y + a.getY(),
				    this.z + a.getZ());
	}


	/**
	 * multiply each value in this vector with another vector
	 * @param  a	Vector3 the vector whose terms will be multiplied
	 *   @return      pointer to this Vector3f
	 */
	public Vector3f elemMul(final Vector3f a)
	{
		this.x *= a.x;
		this.y *= a.y;
		this.z *= a.z;

		return this;
	}

	/**
	 * multiply each value in this vector with a float
	 * @param  a	float, the vector whose terms will be multiplied
	 *   @return      pointer to this Vector3f
	 */
	public Vector3f elemMul(final float a)
	{
		this.x *= a;
		this.y *= a;
		this.z *= a;

		return this;
	}

	/**
	 * multiply each value in this vector with a float and returns the
	 * vector
	 * @param  a	float, the vector whose terms will be multiplied
	 *   @return      Vector3f
	 */
	public Vector3f pureElemMul(final float a)
	{
		return new Vector3f(this.x * a, this.y * a, this.z * a);
	}


	/**
	 * subtract this vector3f with another vector
	 * @param  a	Vector3f, the vector that will be subtracted
	 *   @return      void
	 */
	public final void subtract(final Vector3f a)
	{

		this.x -= a.x;
		this.y -= a.y;
		this.z -= a.z;
	}

	/**
	 * multiply this vector with anothe matrix
	 * @param  a	Matrix<Float>, the matrix being multiplied
	 * @return      void
	 */
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

	/**
	 * calculate and return the scalar product of another vectors with this
	 * vector
	 * @param  a	Vector3f, first vector
	 * @param  scalar	float, the scalar value
	 * @return      Vector3f
	 */
	public final Vector3f scalarProduct(final Vector3f a,
					    final float scalar)
	{
		return new Vector3f((a.x * scalar), (a.y * scalar),
				    (a.z * scalar));
	}

	/**
	 * calculate and return the dot product of 2 vectors
	 * @param  a	Vector3f, first vector
	 * @param  b	Vector3f, second vector
	 * @return      float
	 */
	public final float dotProduct(final Vector3f a, final Vector3f b)
	{
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	/**
	 * calculate and return the scalar value of a vector
	 * * @param  a	Vector3f, the first vector
	 * @return      float
	 */
	static public final float scalarValueOfVector(final Vector3f a)
	{
		return (float)Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
	}

	/**
	 * calculate and return the absolute value of a vector
	 * * @param  a	Vector3f, the first vector
	 * @return      float
	 */
	static public final float abs(final Vector3f a)
	{
		return scalarValueOfVector(a);
	}

	/**
	 * deep copies and return the unit vector of a vector
	 * * @param  a	Vector3f, the first vector
	 * @return      Vector3f
	 */
	static public Vector3f getUnitVectorByCopy(Vector3f a)
	{
		float abs = Vector3f.abs(a);
		return new Vector3f(a.getX() / abs, a.getY() / abs,
				    a.getZ() / abs);
	}

	/**
	 * calculate and return the angle between 2 vectors
	 * * @param  a	Vector3f, the first vector
	 * * @param  b	Vector3f, the second  vector
	 * @return      float, the angle betwwen them
	 */
	public final float angleBetweenTwoVector3(final Vector3f a,
						  final Vector3f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dotProduct(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	/**
	 * get the normal of a vector and returns it
	 * @param  a	Vector3f, a vector
	 *    @return      Vector3f
	 */
	public final Vector3f normalOfVector3f(final Vector3f a)
	{

		Vector3f tempVec = new Vector3f(1, 0, 0);
		if (a.equals(tempVec)) {
			return crossProduct(a, tempVec);
		} else {
			return crossProduct(a, new Vector3f(0, 1, 0));
		}
	}

	/**
	 * get the cross product of 2 vectors and returns it
	 * @param  a	Vector3f, a vector
	 * @param  b	Vector3f, another vector
	 *    @return      Vector3f
	 */
	public final static Vector3f crossProduct(final Vector3f a,
						  final Vector3f b)
	{
		return new Vector3f(a.y * b.z - a.z * b.y,
				    a.z * b.x - a.x * b.z,
				    a.x * b.y - a.y * b.x);
	}

	/**
	 * get the cross product of a vector and this vector and returns it
	 * @param  a	Vector3f, a vector
	 *    @return      Vector3f
	 */
	public final Vector3f pureCross(final Vector3f b)
	{
		return new Vector3f(this.y * b.z - this.z * b.y,
				    this.z * b.x - this.x * b.z,
				    this.x * b.y - this.y * b.x);
	}

	/**
	 * will determine if another vector is equal to this vector
	 * * @param  a	Vector3f, another vector
	 *  @return      boolean, true if they are equal and false otherwise
	 */
	public boolean equals(final Vector3f n)
	{

		return Math.abs(this.x - n.x) < EPSILON
			&& Math.abs(this.y - n.y) < EPSILON
			&& Math.abs(this.z - n.z) < EPSILON;
	}

	/**
	 * converting a 2d vector to 3d vector
	 * * @param  a	Vector2f, the 2d vector
	 *  @return      Vector3f
	 */
	public static final Vector3f convertVec2ToVec3(Vector2f a)
	{
		return new Vector3f(a.x, a.y, 0);
	}

	/**
	 * getting the x value of this vector
	 *  @return      float, x value
	 */

	public final float getX()
	{
		return this.x;
	}

	/**
	 * setting the x value of this vector
	 * * @param  newx	float, new x value
	 *  @return      void
	 */
	public final void setX(float newx)
	{
		this.x = newx;
	}

	/**
	 * getting the y value of this vector
	 *  @return      float, y value
	 */
	public final float getY()
	{
		return this.y;
	}

	/**
	 * setting the y value of this vector
	 * * @param  newy	float, new y value
	 *  @return      void
	 */
	public final void setY(float newy)
	{
		this.y = newy;
	}
	/**
	 * getting the z value of this vector
	 *  @return      float, z value
	 */
	public final float getZ()
	{
		return this.z;
	}

	/**
	 * setting the z value of this vector
	 * * @param  newz	float, new z value
	 *  @return      void
	 */
	public final void setZ(float newz)
	{
		this.z = newz;
	}
	/**
	 * deep copies a vector3
	 * * @param  n	Vector3f, the vector to be deep copied
	 *  @return      Vector3f
	 */
	static public final Vector3f deepCopyVector3f(Vector3f n)
	{
		return new Vector3f(n.getX(), n.getY(), n.getZ());
	}
}
