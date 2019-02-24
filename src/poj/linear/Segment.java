package poj.linear;

public class Segment
{
	private Vector2f a;
	private Vector2f b;

	public Segment(Vector2f a, Vector2f b)
	{
		this.a = a;
		this.b = b;
	}


	public void shift(Vector2f n)
	{
		a.add(n);
		b.add(n);
	}

	public Vector2f a()
	{
		return a;
	}

	public Vector2f b()
	{
		return b;
	}

	public Vector2f pureA()
	{
		return new Vector2f(a);
	}

	public Vector2f pureB()
	{
		return new Vector2f(b);
	}
}
