package poj.linear;

import java.lang.Math;
import poj.Logger.*;

public class Vector2f
{
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

	public void add(final Vector2f a)
	{
		this.x += a.x;
		this.y += a.y;
	}

	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	public void subtract(final Vector2f a)
	{
		this.x -= a.x;
		this.y -= a.y;
	}

	public void mul(float n)
	{
		this.x *= n;
		this.y *= n;
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


	public final Vector2f scalarProduct(final Vector2f a,
					    final float scalar)
	{
		return new Vector2f((a.x * scalar), (a.y * scalar));
	}

	public final float dotProduct(final Vector2f a, final Vector2f b)
	{
		return a.x * b.x + a.y * b.y;
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

	public final Vector2f normalOfVector2f(final Vector2f a)
	{
		return new Vector2f(a.y, -a.x);
	}

	public final float getX()
	{
		return this.x;
	}

	public final float getY()
	{
		return this.y;
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
}
// add, minus, scalarProduct, MatrixVectorMultiplication??, dot product,
// Crossproduct??
// normalize
//  vector transformation 2d
//  matrix multiplication
//  transformation matrix
