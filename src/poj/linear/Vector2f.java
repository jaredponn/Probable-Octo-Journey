package poj.linear;

import java.lang.Math;
public class Vector2f
{
	public float x;
	public float y;

	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public Vector2f()
	{
	}

	public final static Vector2f add(final Vector2f a, final Vector2f b)
	{
		return new Vector2f((a.x + b.x), (a.y + b.y));
	}

	public final static Vector2f subtract(final Vector2f a,
					      final Vector2f b)
	{
		return new Vector2f((a.x - b.x), (a.y - b.y));
	}

	public final static Vector2f scalarProduct(final Vector2f a,
						   final float scalar)
	{
		return new Vector2f((a.x * scalar), (a.y * scalar));
	}

	public final static float dotProduct(final Vector2f a, final Vector2f b)
	{
		return a.x * b.x + a.y * b.y;
	}

	public final static float scalarValueOfVector(final Vector2f a)
	{
		return (float)Math.sqrt(a.x * a.x + a.y * a.y);
	}

	public final static float angleBetweenTwoVector2(final Vector2f a,
							 final Vector2f b)
	{
		// angle is NOT ABSOLUTE VALUE!! if negative angle then
		// pi/2< theta < pi
		return (float)Math.acos(
			dotProduct(a, b)
			/ (scalarValueOfVector(a) * scalarValueOfVector(b)));
	}

	public final static Vector2f normalOfVector2f(final Vector2f a)
	{
		return new Vector2f(a.y, -a.x);
	}
}
// add, minus, scalarProduct, MatrixVectorMultiplication??, dot product,
// Crossproduct??
// normalize
//  vector transformation 2d
//  matrix multiplication
//  transformation matrix
