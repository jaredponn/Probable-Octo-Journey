package poj.linear;
/**
 * Rectangle
 *
 * date March 10, 2019
 * @author Haiyang He
 * @version 1.0
 */


public class Rectanglef
{
	private Vector2f topLeft;
	private Vector2f bottomRight;

	/**
	 * Constructs the Rectanglef object with 4 cordinates - top left and
	 * bottom right, with their default value to be Float.MAX_VALUE
	 */
	public Rectanglef()
	{
		this.topLeft = new Vector2f();
		this.bottomRight = new Vector2f();
	}

	/**
	 * Constructs the Rectanglef object with 4 cordinates - top left and
	 * bottom right
	 * @param  x0	integer, top left x cordinate
	 * @param  y0	integer, top left y cordinate
	 * @param  x1	integer, bottom right x cordinate
	 * @param  y1	integer, bottom right y cordinate
	 */

	// topLeft: (x0, y0)
	// bottomRight: (x1, y1)
	public Rectanglef(float x0, float y0, float x1, float y1)
	{
		this.topLeft = new Vector2f(x0, y0);
		this.bottomRight = new Vector2f(x1, y1);
	}

	/**
	 * Constructs the Rectanglef object with 2 vector2f representing the top
	 * left and bottom right cordinates
	 * @param  topleft	Vector2f, top left cordinate
	 * @param  botright	Vector2f, bottom right cordinate
	 */
	public Rectanglef(Vector2f topleft, Vector2f botright)
	{
		this.topLeft = topleft;
		this.bottomRight = botright;
	}


	/**
	 * sets the top left of the rectanglef with new vector
	 * @param  n	Vector2f, the new top left cordinate
	 *  @return      void
	 */
	public void setTopLeft(Vector2f n)
	{
		this.topLeft = n;
	}

	/**
	 * sets the bottom right of the rectanglef with new vector
	 * @param  n	Vector2f, the new bottom right cordinate
	 *  @return      void
	 */
	public void setBotRight(Vector2f n)
	{
		this.bottomRight = n;
	}

	/**
	 * returns the top left of the rectanglef with new vector
	 *  @return      Vector2f, top left cordinate
	 */
	public Vector2f getTopLeft()
	{
		return this.topLeft;
	}

	/**
	 * returns the bottom right of the rectanglef with new vector
	 *  @return      Vector2f, bottom right cordinate
	 */
	public Vector2f getBotRight()
	{
		return this.bottomRight;
	}

	/**
	 * gets the width of the rectangle
	 *  @return      float, the width of the rectangle
	 */
	public float getWidth()
	{
		return Math.abs(this.topLeft.getX() - this.bottomRight.getX());
	}

	/**
	 * gets the height of the rectangle
	 *  @return      float, the height of the rectangle
	 */
	public float getHeight()
	{
		return Math.abs(this.topLeft.getY() - this.bottomRight.getY());
	}

	/**
	 * translate the rectangle by a vector2f
	 * @param  n	Vector2f, the translation vector
	 *  @return      void
	 */
	public void translate(Vector2f n)
	{
		this.topLeft.add(n);
		this.bottomRight.add(n);
	}

	/**
	 * test if two rectangles are colliding
	 * @param  a	Rectanglef, rectangle A
	 * @param  b	Rectanglef, rectangle B
	 *  @return      boolean, true if they are colliding, and false if they
	 * do not collide
	 */
	// if the edges are touching, then the objects are considered to be
	// colliding
	public static boolean isColliding(Rectanglef a, Rectanglef b)
	{
		return (a.getTopLeft().getX() <= b.getBotRight().getX()
			&& a.getBotRight().getX() >= b.getTopLeft().getX())
			&& (a.getTopLeft().getY() <= b.getBotRight().getY()
			    && a.getBotRight().getY() >= b.getTopLeft().getY());
	}

	/**
	 * test if this rectangle is colliding with another rectange
	 * @param  a	Rectanglef, rectangle A
	 *  @return      boolean, true if this and A are colliding, and false if
	 * they do not collide
	 */
	public boolean isColliding(Rectanglef a)
	{
		return Rectanglef.isColliding(this, a);
	}
}

