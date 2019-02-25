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

	public Vector2f()
	{
		this.x = Float.MAX_VALUE;
		this.y = Float.MAX_VALUE;
	}

	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector2f(Vector2f n)
	{
		this(n.x, n.y);
	}
	public void floorOfValues()
	{
		floor();
	}

	public void add(final Vector2f a)
	{
		add(a.x, a.y);
	}

	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	public Vector2f addAndReturnVector(final Vector2f a)
	{
		return addAndReturnVector(a.x, a.y);
	}
	public Vector2f addAndReturnVector(float x, float y)
	{
		return new Vector2f(this.x + x, this.y + y);
	}

	public Vector2f pureAdd(final Vector2f a)
	{
		return addAndReturnVector(a);
	}

	public Vector2f pureAdd(float x, float y)
	{
		return addAndReturnVector(x, y);
	}

	public void subtract(final Vector2f a)
	{
		subtract(a.x, a.y);
	}
	public void subtract(float x, float y)
	{
		this.x -= x;
		this.y -= y;
	}
	public Vector2f subtractAndReturnVector(final Vector2f a)
	{
		return subtractAndReturnVector(a.x, a.y);
	}
	public Vector2f subtractAndReturnVector(float x, float y)
	{
		return new Vector2f(this.x - x, this.y - y);
	}

	public void negate()
	{
		this.x = -this.x;
		this.y = -this.y;
	}

	public Vector2f pureNegate()
	{
		Vector2f tmp = new Vector2f(this);
		tmp.negate();
		return tmp;
	}

	public void floor()
	{
		this.x = (float)Math.floor(this.x);
		this.y = (float)Math.floor(this.y);
	}

	public Vector2f pureSubtract(final Vector2f a)
	{
		Vector2f tmp = new Vector2f(this);
		tmp.subtract(a);
		return tmp;
	}

	public Vector2f pureSubtract(float x, float y)
	{
		return pureSubtract(new Vector2f(x, y));
	}

	public void mul(float n)
	{
		this.x *= n;
		this.y *= n;
	}

	public Vector2f pureMul(float n)
	{
		Vector2f tmp = new Vector2f(this);
		tmp.x *= n;
		tmp.y *= n;
		return tmp;
	}

	public Vector2f pureNormalize()
	{
		final float mag = scalarValueOfVector(this);
		Vector2f v = new Vector2f(this);
		v.setX(v.getX() / mag);
		v.setY(v.getY() / mag);
		return v;
	}

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


	public final void multiplyWithMatrix(final Matrix<Float> A)
	{
		Vector3f tempVector3 = new Vector3f(this.x, this.y, 1);
		tempVector3.matrixVector3fProduct(A);
		this.x = tempVector3.x;
		this.y = tempVector3.y;
	}

	public void swapXandY()
	{
		float tmp = this.x;
		this.x = y;
		this.y = tmp;
	}

	// aliasfor multiplyWithMatrix
	public final void matrixMultiply(final Matrix<Float> A)
	{
		this.multiplyWithMatrix(A);
	}

	public final Vector2f pureMatrixMultiply(final Matrix<Float> A)
	{
		Vector2f tmp = new Vector2f(this);
		tmp.multiplyWithMatrix(A);
		return tmp;
	}


	public final static Vector2f scalarProduct(final Vector2f a,
						   final float scalar)
	{
		return new Vector2f((a.x * scalar), (a.y * scalar));
	}

	public static final float dot(final Vector2f a, final Vector2f b)
	{
		return a.x * b.x + a.y * b.y;
	}

	public float dot(final Vector2f a)
	{
		return Vector2f.dot(this, a);
	}

	public final static float scalarValueOfVector(final Vector2f a)
	{
		return (float)Math.sqrt(sqMag(a));
	}

	public static final float sqMag(final Vector2f a)
	{
		return a.x * a.x + a.y * a.y;
	}

	public final float sqMag()
	{
		return sqMag(this);
	}

	public final float mag()
	{
		return scalarValueOfVector(this);
	}


	public final float abs(final Vector2f a)
	{
		return scalarValueOfVector(a);
	}

	public final float angleBetweenTwoVector2(final Vector2f a,
						  final Vector2f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dot(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	public final Vector2f pureGetCCPerpendicularVector()
	{
		return new Vector2f(y, -x);
	}

	public final Vector2f pureGetCCWPerpendicularVector()
	{
		return new Vector2f(-y, x);
	}

	public final float getX()
	{
		return this.x;
	}

	public final float x()
	{
		return getX();
	}


	public final float getY()
	{
		return this.y;
	}

	public final float y()
	{
		return getY();
	}

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

	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void setX(float n)
	{
		this.x = n;
	}


	public void setY(float n)
	{
		this.y = n;
	}

	public void log()
	{
		log("");
	}

	public void log(String s)
	{
		Logger.logMessage(LogLevels.VERBOSE, this.toString() + " " + s);
	}

	public boolean equals(final Vector2f n)
	{

		return Math.abs(this.x - n.x) < EPSILON
			&& Math.abs(this.y - n.y) < EPSILON;
	}

	public boolean equals(float n)
	{
		return Math.abs(x - n) < EPSILON && Math.abs(y - n) < EPSILON;
	}

	public boolean lessThan(final Vector2f n)
	{
		return x < n.x && y < n.y;
	}

	public boolean greaterThan(final Vector2f n)
	{
		return x > n.x && y > n.y;
	}

	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

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
