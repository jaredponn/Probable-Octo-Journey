package poj.linear;

import java.lang.Math;
import poj.Logger.*;

public class Vector2f
{

	private float EPSILON = 0.00000001f;
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


	public final Vector2f scalarProduct(final Vector2f a,
					    final float scalar)
	{
		return new Vector2f((a.x * scalar), (a.y * scalar));
	}

	public static final float dotProduct(final Vector2f a, final Vector2f b)
	{
		return a.x * b.x + a.y * b.y;
	}

	public float dotProduct(final Vector2f a)
	{
		return Vector2f.dotProduct(this, a);
	}

	public final float scalarValueOfVector(final Vector2f a)
	{
		return (float)Math.sqrt(a.x * a.x + a.y * a.y);
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
			dotProduct(a, b)
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

	public final float getY()
	{
		return this.y;
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
		Logger.logMessage(LogLevels.VERBOSE, "Vector2f: x =  " + this.x
							     + ", y = " + this.y
							     + " " + s);
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
}
