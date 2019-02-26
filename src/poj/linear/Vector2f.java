package poj.linear;

import java.lang.Math;
import poj.Logger.*;
import java.util.Optional;

public class Vector2f
{

	private static float EPSILON = 0.00000001f;
	public static int MAX_LENGTH = 2;

	public float x;
	public float y;

	/**
	 * Constructs the vector2f object with x and y values to be
	 * Float.MAX_VALUE
	 */
	public Vector2f()
	{
		this.x = Float.MAX_VALUE;
		this.y = Float.MAX_VALUE;
	}

	/**
	 * Constructs the vector2f object with x and y values
	 * @param  x	float, the x value of the vector
	 * @param  y	float, the y value of the vector
	 */
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Copy constructor
	 * @param  n	Vector2f, the vector that needed to be deep copied
	 */
	public Vector2f(Vector2f n)
	{
		this(n.x, n.y);
	}
	/**
	 * alias for floor()
	 *   @return      void
	 */
	public void floorOfValues()
	{
		floor();
	}

	/**
	 * add this vector2f with another vector
	 * @param  a	Vector2f, the vector that will be added
	 *   @return      void
	 */
	public void add(final Vector2f a)
	{
		add(a.x, a.y);
	}

	/**
	 * add this vector2f with another vector, where the other vector is
	 * represented into floats x and y
	 * @param  x	float, the x value of another vector that will be added
	 * @param  y	float, the y value of another vector that will be added
	 *   @return      void
	 */
	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	/**
	 * add and returns this vector2f with another vector
	 * @param  a	Vector2f, the vector that will be added
	 *   @return      Vector2f
	 */
	public Vector2f addAndReturnVector(final Vector2f a)
	{
		return addAndReturnVector(a.x, a.y);
	}
	/**
	 * add and returns this vector2f with another vector, where the other
	 vector is
	 * represented into floats x and y

	 * @param  x	float, the x value of the vector that will be added
	 * @param  y	float, the y value of the vector that will be added
	 *   @return      Vector2f
	 */
	public Vector2f addAndReturnVector(float x, float y)
	{
		return new Vector2f(this.x + x, this.y + y);
	}

	/**
	 * alias for addAndReturnVector
	 * @param  a	Vector2f, the vector that will be added
	 *   @return      Vector2f
	 */
	public Vector2f pureAdd(final Vector2f a)
	{
		return addAndReturnVector(a);
	}

	/**
	 * alias for addAndReturnVector
	 * @param  x	float, the x value of the vector that will be added
	 * @param  y	float, the y value of the vector that will be added
	 *   @return      Vector2f
	 */
	public Vector2f pureAdd(float x, float y)
	{
		return addAndReturnVector(x, y);
	}

	/**
	 * subtract this vector2f with another vector
	 * @param  a	Vector2f, the vector that will be subtracted
	 *   @return      void
	 */
	public void subtract(final Vector2f a)
	{
		subtract(a.x, a.y);
	}
	/**
	 * subtract this vector2f with another vector, where the other vector is
	 * represented into floats x and y
	 * @param  x	float, the x value of another vector that will be
	 *         subtracted
	 * @param  y	float, the y value of another vector that will be
	 *         subtracted
	 *   @return      void
	 */
	public void subtract(float x, float y)
	{
		this.x -= x;
		this.y -= y;
	}
	/**
	 * alias for pureSubtract
	 * @param  a	Vector2f, the vector that will be subtracted
	 *   @return      Vector2f
	 */
	public Vector2f subtractAndReturnVector(final Vector2f a)
	{
		return pureSubtract(a);
	}
	/**
	 * alias for pureSubtract
	 * @param  x	float, the x value of another vector that will be
	 *         subtracted
	 * @param  y	float, the y value of another vector that will be
	 *         subtracted
	 *   @return      Vector2f
	 */
	public Vector2f subtractAndReturnVector(float x, float y)
	{
		return pureSubtract(x, y);
	}

	/**
	 * make the values inside the vector negative
	 *   @return      void
	 */
	public void negate()
	{
		this.x = -this.x;
		this.y = -this.y;
	}

	/**
	 * make the values inside the vector negative and returns that vector
	 *   @return      Vector2f, the negated vector
	 */
	public Vector2f pureNegate()
	{
		Vector2f tmp = new Vector2f(this);
		tmp.negate();
		return tmp;
	}

	/**
	 * takes the floor of all the values inside this vector2f
	 *   @return      void
	 */
	public void floor()
	{
		this.x = (float)Math.floor(this.x);
		this.y = (float)Math.floor(this.y);
	}

	/**
	 * subtract and returns the added vector of this vector2f with another
	 * vector
	 * @param  a	Vector2f, the vector that will be subtracted
	 *   @return      Vector2f
	 */
	public Vector2f pureSubtract(final Vector2f a)
	{
		Vector2f tmp = new Vector2f(this);
		tmp.subtract(a);
		return tmp;
	}

	/**
	 * subtract and returns the subtracted vector with this vector2f with
	 * another vector, where the other vector is represented into floats x
	 * and y
	 * @param  x	float, the x value of another vector that will be
	 *         subtracted
	 * @param  y	float, the y value of another vector that will be
	 *         subtracted
	 *   @return      Vector2f
	 */
	public Vector2f pureSubtract(float x, float y)
	{
		return pureSubtract(new Vector2f(x, y));
	}

	/**
	 * multiply the vector with a scalar value
	 * @param  n	float, the scalar value that will be multiplied
	 *   @return      voi
	 */
	public void mul(float n)
	{
		this.x *= n;
		this.y *= n;
	}

	/**
	 * multiply and return the vector multiplied with a scalar value
	 * @param  n	float, the scalar value that will be multiplied
	 *   @return      Vector2f
	 */
	public Vector2f pureMul(float n)
	{
		Vector2f tmp = new Vector2f(this);
		tmp.x *= n;
		tmp.y *= n;
		return tmp;
	}

	/**
	 * get the normal of this vector and returns it
	 *   @return      Vector2f
	 */
	public Vector2f pureNormalize()
	{
		final float mag = scalarValueOfVector(this);
		Vector2f v = new Vector2f(this);
		v.setX(v.getX() / mag);
		v.setY(v.getY() / mag);
		return v;
	}

	/**
	 * get the safe normal of this vector (will return Float.MAX_VALUE if
	 * its magnitude is 0) and returns it
	 *   @return      Vector2f
	 */
	public Vector2f safePureNormalize()
	{
		Vector2f v = new Vector2f(this);
		final float mag = scalarValueOfVector(this);
		if (!(Math.abs(mag - 0f) <= EPSILON)) {
			v.setX(v.getX() / mag);
			v.setY(v.getY() / mag);
		}
		return v;
	}


	/**
	 * multiply this vector with anothe matrix
	 * @param  A	Matrix<Float>, the matrix being multiplied
	 * @return      void
	 */
	public final void multiplyWithMatrix(final Matrix<Float> A)
	{
		Vector3f tempVector3 = new Vector3f(this.x, this.y, 1);
		tempVector3.matrixVector3fProduct(A);
		this.x = tempVector3.x;
		this.y = tempVector3.y;
	}

	/**
	 * swaps the x and y values in this vector
	 * @return      void
	 */
	public void swapXandY()
	{
		float tmp = this.x;
		this.x = y;
		this.y = tmp;
	}

	/**
	 * alias for multiplyWithMatrix
	 * @param  A	Matrix<Float>, the matrix being multiplied
	 * @return      void
	 */
	// aliasfor multiplyWithMatrix
	public final void matrixMultiply(final Matrix<Float> A)
	{
		this.multiplyWithMatrix(A);
	}

	/**
	 * multiply this vector with anothe matrix and returns the new vector2f
	 * @param  A	Matrix<Float>, the matrix being multiplied
	 * @return      Vector2f
	 */
	public final Vector2f pureMatrixMultiply(final Matrix<Float> A)
	{
		Vector2f tmp = new Vector2f(this);
		tmp.multiplyWithMatrix(A);
		return tmp;
	}


	/**
	 * calculate and return the scalar product of 2 vectors
	 * @param  a	Vector2f, first vector
	 * @param  scalar	float, the scalar value
	 * @return      Vector2f
	 */
	public final static Vector2f scalarProduct(final Vector2f a,
						   final float scalar)
	{
		return new Vector2f((a.x * scalar), (a.y * scalar));
	}

	/**
	 * calculate and return the dot product of 2 vectors
	 * @param  a	Vector2f, first vector
	 * @param  b	Vector2f, second vector
	 * @return      float
	 */
	public static final float dot(final Vector2f a, final Vector2f b)
	{
		return a.x * b.x + a.y * b.y;
	}

	/**
	 * calculate and return the dot product of 1 vector with this vector
	 * @param  a	Vector2f, first vector
	 * @return      float
	 */
	public float dot(final Vector2f a)
	{
		return Vector2f.dot(this, a);
	}

	/**
	 * calculate and return the scalar value of a vector
	 * * @param  a	Vector2f, the first vector
	 * @return      float
	 */
	public final static float scalarValueOfVector(final Vector2f a)
	{
		return (float)Math.sqrt(sqMag(a));
	}

	/**
	 * calculate and return the square root of the magnitude
	 * of a vector of a vector
	 * * @param  a	Vector2f, the first vector
	 * @return      float
	 */
	public static final float sqMag(final Vector2f a)
	{
		return a.x * a.x + a.y * a.y;
	}
	/**
	 * calculate and return the square root of the magnitude
	 * of this vector of a vector
	 * @return      float
	 */

	public final float sqMag()
	{
		return sqMag(this);
	}

	/**
	 * calculate and return the magnitude of this vector
	 * of this vector of a vector
	 * @return      float
	 */
	public final float mag()
	{
		return scalarValueOfVector(this);
	}


	/**
	 * calculate and return the absolute value of a vector
	 * * @param  a	Vector2f, the first vector
	 * @return      float
	 */
	public final float abs(final Vector2f a)
	{
		return scalarValueOfVector(a);
	}

	/**
	 * calculate and return the angle between 2 vectors
	 * * @param  a	Vector2f, the first vector
	 * * @param  b	Vector2f, the second  vector
	 * @return      float, the angle betwwen them
	 */
	public final float angleBetweenTwoVector2(final Vector2f a,
						  final Vector2f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dot(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	/**
	 * calculate and return the cloclwise perpendicular vector of this
	 * vector
	 * @return      Vector2f
	 */
	public final Vector2f pureGetCCPerpendicularVector()
	{
		return new Vector2f(y, -x);
	}

	/**
	 * calculate and return the counter cloclwise perpendicular vector of
	 * this vector
	 * @return      Vector2f
	 */
	public final Vector2f pureGetCCWPerpendicularVector()
	{
		return new Vector2f(-y, x);
	}

	/**
	 *  return the x value of this vector
	 * @return      float, x value
	 */
	public final float getX()
	{
		return this.x;
	}

	/**
	 * alias for getX()
	 * @return      float, x value
	 */
	public final float x()
	{
		return getX();
	}


	/**
	 *  return the y value of this vector
	 * @return      float, y value
	 */
	public final float getY()
	{
		return this.y;
	}

	/**
	 * alias for getY()
	 * @return      float, y value
	 */
	public final float y()
	{
		return getY();
	}

	/**
	 * getting the index 0 (x value) or the index 1 (y value) of this vector
	 * (will log the error if the index is out of bounds, and if it is out
	 * of bounds it will return the y value of the vector)
	 * * @param  i	float, index of vector
	 * @return      float, x or y value
	 */
	public final float get(int i)
	{
		if (i == 0) {
			return x;
		} else if (i == 1) {
			return y;
		} else {
			Logger.logMessage(
				"Error in Vector2f -- accessing an element out of bounds. Returning the y value.");
			return y;
		}
	}

	/**
	 * setting the index 0 (x value) or the index 1 (y value) of this vector
	 * (will log the error if the index is out of bounds)
	 * * @param  i	float, index of vector
	 * * @param  n	float, the value wanted to be set to the vector
	 * @return      void
	 */
	public final void set(int i, float n)
	{
		if (i == 0)
			this.x = n;
		else if (i == 1)
			this.y = n;
		else
			Logger.logMessage(
				"Error in Vector2f -- setting an element out of bounds. Doing nothing .");
	}

	/**
	 * setting the x value and the y value of this vector
	 * * @param  x	float, new x value of the vector
	 * * @param  y	float, new y value of the vector
	 * @return      void
	 */
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * setting the x value  of this vector
	 * * @param  n	float, new x value of the vector
	 * @return      void
	 */
	public void setX(float n)
	{
		this.x = n;
	}

	/**
	 * setting the y value  of this vector
	 * * @param  n	float, new y value of the vector
	 * @return      void
	 */
	public void setY(float n)
	{
		this.y = n;
	}

	public void log()
	{
		log("");
	}

	/**
	 * will print this vector with a message
	 * * @param  s	String, message
	 *  @return      void
	 */
	public void log(String s)
	{
		Logger.logMessage(LogLevels.VERBOSE, this.toString() + " " + s);
	}

	/**
	 * will determine if another vector is equal to this vector
	 * * @param  n	Vector2f, another vector
	 *  @return      boolean, true if they are equal and false otherwise
	 */
	public boolean equals(final Vector2f n)
	{

		return Math.abs(this.x - n.x) < EPSILON
			&& Math.abs(this.y - n.y) < EPSILON;
	}

	/**
	 * will determine if both values of this vector is equal to a number
	 * * @param  n	float, number to be compared to
	 *  @return      boolean, true if they are equal and false otherwise
	 */
	public boolean equals(float n)
	{
		return Math.abs(x - n) < EPSILON && Math.abs(y - n) < EPSILON;
	}

	/**
	 * will determine if this vector is less than another vector
	 * * @param  n	Vector2f, another vector
	 *  @return      boolean, true if this vector is less than another
	 * vector, and false otherwise
	 */
	public boolean lessThan(final Vector2f n)
	{
		return x < n.x && y < n.y;
	}

	/**
	 * will determine if this vector is greater than another vector
	 * * @param  n	Vector2f, another vector
	 *  @return      boolean, true if this vector is greater than another
	 * vector, and false otherwise
	 */
	public boolean greaterThan(final Vector2f n)
	{
		return x > n.x && y > n.y;
	}

	/**
	 * will print the x and y values of the vector
	 *  @return      String, x and y values of the vector
	 */
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	/**
	 * calculate and return triple cross product of 3 vectors
	 * * @param  a2	Vector2f, first vector
	 * * @param  b2	Vector2f, second vector
	 * * @param  c2	Vector2f, third vector
	 *  @return      Vector2f, their triple cross product, in 2d
	 */
	public static Vector2f pureTripleProduct(Vector2f a2, Vector2f b2,
						 Vector2f c2)
	{
		Vector3f a = Vector3f.convertVec2ToVec3(a2);
		Vector3f b = Vector3f.convertVec2ToVec3(b2);
		Vector3f c = Vector3f.convertVec2ToVec3(c2);

		Vector3f p = a.pureCross(b);
		Vector3f q = p.pureCross(c);

		return new Vector2f(q.x, q.y);
	}


	/*
	 *       C
	 *      /|
	 *     / '
	 *    /  |
	 *  A---------------B
	 *       ^--- compt(AB,AC)* AB + A
	 *  |----|
	 *     |
	 *  proj(AB, AC)
	 */

	/**
	 * calculate and return the compt (returns the ratio of AB so that it is
	 * the size of the projection of AC on AB) of AB with AC.
	 * * @param  ab	Vector2f, AB vector
	 * * @param  bc	Vector2f, AC vector
	 *  @return      Optional<Float>, the ratio of AB if the magnitude
	 * squared of the vector AB is not 0, and empty otherwise
	 */
	// returns the parameter t that is the ratio of AB so that it is the
	// size of the projection of AC on AB
	public static Optional<Float> compt(Vector2f ab, Vector2f ac)
	{
		float absqmag = ab.sqMag();
		if (Math.abs(absqmag - 0.0f) <= EPSILON)
			return Optional.empty();
		else
			return Optional.of(ab.dot(ac) / absqmag);
	}

	/**
	 * calculate and return the vector projection of AB with AC.
	 * * @param  ab	Vector2f, AB vector
	 * * @param  bc	Vector2f, AC vector
	 *  @return      Optional<Vector2f>, the vector projection of AB if the
	 * magnitude squared of the vector AB is not 0, and empty otherwise
	 */
	public static Optional<Vector2f> proj(Vector2f ab, Vector2f ac)
	{
		float absqmag = ab.sqMag();
		if (Math.abs(absqmag - 0.0f) <= EPSILON)
			return Optional.empty();
		else {
			float n = ab.dot(ac) / absqmag;
			return Optional.of(ab.pureMul(n));
		}
	}
}
