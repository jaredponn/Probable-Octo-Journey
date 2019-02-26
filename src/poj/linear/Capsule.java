package poj.linear;

public class Capsule extends Segment
{
	private float radius;

	/**
	 * Constructs the Capsule object with 2 vectors and a radius
	 *
	 * @param  a	Vector A
	 * @param  b	Vector B
	 * @param  r	float, radius
	 */
	public Capsule(Vector2f a, Vector2f b, float r)
	{
		super(a, b);
		this.radius = r;
	}

	/**
	 * get the radius of Capsule
	 *
	 * @return      float, the radius of the capsule
	 */
	public float r()
	{
		return radius;
	}
}
