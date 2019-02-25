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


	/**
	 * Constructor
	 * Constructs circle from an x and y coordinate and heights and widths
	 *
	 * @param topleftx  x coord
	 * @param toplefty  y coord
	 * @param w  width
	 * @param h  height
	 */
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
		return "Collision Body: topLeft: "
			+ getTopLeftPoint().toString() + " min = (" + min().x
			+ ", " + min().y + "), max = (" + max().x + ", "
			+ max().y + "), width = " + width
			+ ", height = " + height;
	}
}
