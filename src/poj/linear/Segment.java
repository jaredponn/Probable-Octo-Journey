package poj.linear;

public class Segment
{
	private Vector2f a;
	private Vector2f b;

	/**
	 * Constructs the Segment object with two vector2f repersenting two
	 * points
	 * @param  a	Vector2f, point A
	 * @param  b	Vector2f, point B
	 */
	public Segment(Vector2f a, Vector2f b)
	{
		this.a = a;
		this.b = b;
	}


	/**
	 * shifts the segment by a vector2f
	 * @param  n	Vector2f, shift vector
	 *  @return      void
	 */
	public void shift(Vector2f n)
	{
		a.add(n);
		b.add(n);
	}

	/**
	 * returns the point A of the segment
	 *  @return      Vector2f, point A
	 */
	public Vector2f a()
	{
		return a;
	}

	/**
	 * returns the point B of the segment
	 *  @return      Vector2f, point B
	 */
	public Vector2f b()
	{
		return b;
	}

	/**
	 * deep copies and returns the point A of the segment
	 *  @return      Vector2f, point A
	 */
	public Vector2f pureA()
	{
		return new Vector2f(a);
	}
	/**
	 * deep copies and returns the point B of the segment
	 *  @return      Vector2f, point B
	 */

	public Vector2f pureB()
	{
		return new Vector2f(b);
	}
}
