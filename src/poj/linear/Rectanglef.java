package poj.linear;

public class Rectanglef
{
	private Vector2f topLeft;
	private Vector2f bottomRight;

	public Rectanglef()
	{
		this.topLeft = new Vector2f();
		this.bottomRight = new Vector2f();
	}

	// topLeft: (x0, y0)
	// bottomRight: (x1, y1)
	public Rectanglef(float x0, float y0, float x1, float y1)
	{
		this.topLeft = new Vector2f(x0, y0);
		this.bottomRight = new Vector2f(x1, y1);
	}

	public Rectanglef(Vector2f topleft, Vector2f botright)
	{
		this.topLeft = topleft;
		this.bottomRight = botright;
	}


	public void setTopLeft(Vector2f n)
	{
		this.topLeft = n;
	}

	public void setBotRight(Vector2f n)
	{
		this.bottomRight = n;
	}

	public Vector2f getTopLeft()
	{
		return this.topLeft;
	}

	public Vector2f getBotRight()
	{
		return this.bottomRight;
	}

	public float getWidth()
	{
		return Math.abs(this.topLeft.getX() - this.bottomRight.getX());
	}

	public float getHeight()
	{
		return Math.abs(this.topLeft.getY() - this.bottomRight.getY());
	}

	public void translate(Vector2f n)
	{
		this.topLeft.add(n);
		this.bottomRight.add(n);
	}

	// if the edges are touching, then the objects are considered to be
	// colliding
	public static boolean isColliding(Rectanglef a, Rectanglef b)
	{
		return (a.getTopLeft().getX() <= b.getBotRight().getX()
			&& a.getBotRight().getX() >= b.getTopLeft().getX())
			&& (a.getTopLeft().getY() <= b.getBotRight().getY()
			    && a.getBotRight().getY() >= b.getTopLeft().getY());
	}

	public boolean isColliding(Rectanglef a)
	{
		return Rectanglef.isColliding(this, a);
	}
}

