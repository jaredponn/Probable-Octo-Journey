package poj.Collisions;

import java.util.Optional;
import poj.linear.Vector2f;

public class CollisionAabb
{

	/*
	 * a ------- b
	 * |         |
	 * |         |
	 * c --------d
	 *
	 * points = [a, b, c, d]
	 */
	private Vector2f points[];
	private float width;
	private float height;

	final public static int NUM_POINTS = 4;

	public CollisionAabb(float topleftx, float toplefty, float w, float h)
	{
		points = new Vector2f[NUM_POINTS];
		for (int i = 0; i < NUM_POINTS; ++i) {
			points[i] = new Vector2f(0, 0);
		}

		width = w;
		height = h;

		setTopLeftAndUpdateAllPoints(topleftx, toplefty);
	}

	public CollisionAabb(float w, float h)
	{
		this(0, 0, w, h);
	}

	public CollisionAabb(Vector2f topleftv, float w, float h)
	{
		this(topleftv.x, topleftv.y, w, h);
	}

	public CollisionAabb(CollisionAabb c)
	{
		this(c.pureGetTopLeftPoint(), c.getWidth(), c.getHeight());
	}


	// functions
	// https://jaredponn.github.io/posts/2018-06-07-Write-Me-A-FlappyBird-In-Haskell.html
	// xmin0 <= xmax1 && xmax0 >= xmin1) &&
	// (ymin0 <= ymax1 && ymax0 >= ymin1)
	public static boolean isColliding(final CollisionAabb a,
					  final CollisionAabb b)
	{
		return a.min().x <= b.max().x && a.max().x >= b.min().x
			&& a.min().y <= b.max().y && a.max().y >= b.min().y;
	}

	public boolean isColliding(CollisionAabb c)
	{
		return CollisionAabb.isColliding(this, c);
	}

	// algorithim from page 232 of Real-Time Collision Detection by Christer
	// Ericson
	//
	// a -- collision box a
	// b -- collision box b
	// va -- velocity of collision box a
	// vb -- velocity of collision box b
	// returns Optional of the time in the range of 0 <= t <= 1
	public static Optional<Double>
	intersectionTimeOfMoving(final CollisionAabb a, final CollisionAabb b,
				 final Vector2f va, final Vector2f vb)
	{
		System.out.println("START--------------------------------");
		va.log("VA");
		System.out.println(a.toString());
		vb.log("VB");
		System.out.println(b.toString());
		// first and last contact times
		double ti = 0d;
		double tf = 1d;

		// check if initially interesecting
		if (CollisionAabb.isColliding(a, b)) {
			System.out.println("initially colliding");
			return Optional.of(0d);
		}

		// relative velocity where a is not moving and b is moving
		Vector2f rv = vb.pureSubtract(va);
		rv.log("relative vector");

		// iterating through each axis of the vector and determining the
		// first and last contact times
		for (int i = 0; i < Vector2f.MAX_LENGTH; ++i) {
			if (rv.get(i) < 0f) {
				// clang-format off
				// non intersecting and moving apart
				if (b.max().get(i) < a.min().get(i))
					return Optional.empty();

				// moving together
				if (a.max().get(i) < b.min().get(i))
					ti = Math.max((a.max().get(i) - b.min().get(i)) / rv.get(i), ti);

				// moving apart
				if (b.max().get(i) > a.min().get(i))
					tf = Math.min((a.min().get(i) - b.max().get(i)) / rv.get(i), tf);
				// clang-format on
			}

			else if (rv.get(i) > 0f) {
				// clang-format off
				// non intersecting and moving apart
				if (b.min().get(i) > a.max().get(i))
					return Optional.empty();

				// moving together
				if (b.max().get(i) < a.min().get(i))
					ti = Math.max((a.min().get(i) - b.max().get(i)) / rv.get(i), ti);

				// moving apart
				if (a.max().get(i) > b.min().get(i))
					tf = Math.min((a.max().get(i) - b.min().get(i)) / rv.get(i), tf);
				// clang-format on
			}
		}

		// no overlap possible if the initial contanct time happens
		// after the last
		if (ti > tf) {
			System.out.println(
				"initail contact before final contact");
			return Optional.empty();
		}


		// if they were't going to touch at all
		if (ti == 0) {
			System.out.println("ti was 0 ");
			return Optional.empty();
		}
		System.out.println("tifinal: " + ti);

		return Optional.of(ti);
	}


	// setters
	public void setTopLeftAndUpdateAllPoints(Vector2f topleftv)
	{
		setTopLeftAndUpdateAllPoints(topleftv.x, topleftv.y);
	}

	public void setTopLeftAndUpdateAllPoints(final float topleftx,
						 final float toplefty)
	{
		points[0].set(topleftx, toplefty);
		points[1].set(topleftx + width, toplefty);
		points[2].set(topleftx, toplefty - height);
		points[3].set(topleftx + width, toplefty - height);
	}

	public void addToTopLeftAndUpdateAllPoints(float topleftxshift,
						   float topleftyshift)
	{
		Vector2f topleft = getTopLeftPoint();
		setTopLeftAndUpdateAllPoints(topleft.x + topleftxshift,
					     topleft.y + topleftyshift);
	}

	public void setMinAndUpdateAllPoints(Vector2f n)
	{
		setTopLeftAndUpdateAllPoints(n);
	}

	public void setMinAndUpdateAllPoints(float x, float y)
	{
		setTopLeftAndUpdateAllPoints(x, y);
	}


	// getters
	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public Vector2f getTopLeftPoint()
	{
		return points[0];
	}

	public Vector2f min()
	{

		return points[2];
	}

	public Vector2f pureGetMin()
	{
		return new Vector2f(min());
	}

	public Vector2f getBottomRightPoint()
	{
		return points[3];
	}

	public Vector2f max()
	{
		return points[1];
	}


	public Vector2f pureGetMax()
	{
		return new Vector2f(max());
	}

	public Vector2f pureGetTopLeftPoint()
	{
		return new Vector2f(getTopLeftPoint());
	}

	public Vector2f pureGetBottomRightPoint()
	{
		return new Vector2f(getBottomRightPoint());
	}

	public Vector2f[] getPoints()
	{
		return points;
	}

	public Vector2f[] pureGetPoints()
	{
		Vector2f tmp[] = new Vector2f[NUM_POINTS];
		for (int i = 0; i < NUM_POINTS; ++i) {
			tmp[i] = new Vector2f(points[i]);
		}
		return tmp;
	}
	public String toString()
	{
		return "Collision Body: min = (" + min().x + ", " + min().y
			+ "), max = (" + max().x + ", " + max().y
			+ "), width = " + width + ", height = " + height;
	}
}
