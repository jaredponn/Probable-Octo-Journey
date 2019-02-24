package poj.linear;

public class Capsule extends Segment
{
	private float radius;

	public Capsule(Vector2f a, Vector2f b, float r)
	{
		super(a, b);
		this.radius = r;
	}

	public float r()
	{
		return radius;
	}
}
