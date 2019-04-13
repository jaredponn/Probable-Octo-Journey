package Components;
import poj.Component.Component;
import poj.linear.Vector2f;
import poj.linear.Rectanglef;

/**
 * WorldAttributes. WorldAttributes component. the width and height should no
 * longer be used and are deprectaed. however, the coord (or origin coord) is
 * still useful and very relevant
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

public class WorldAttributes implements Component
{
	private Vector2f coord; // origin coordinate
	private float width;
	private float height;

	/**
	 * Constructor
	 */
	public WorldAttributes()
	{
		this(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE), 0, 0);
	}

	/**
	 * Constructor
	 * @param w -- width
	 * @param h -- height
	 */
	public WorldAttributes(float w, float h)
	{
		this(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE), w, h);
	}

	/**
	 * Constructor.  NOTICE vector c is the origin coordinate
	 * @param c -- origin coordinate
	 * @param w -- width
	 * @param h -- height
	 */
	public WorldAttributes(Vector2f c, float w, float h)
	{
		this.coord = c;
		this.width = w;
		this.height = h;
	}

	/**
	 * Constructor.
	 * @param c -- origin coordinate
	 */
	public WorldAttributes(Vector2f c)
	{
		this.coord = c;
		this.width = 0;
		this.height = 0;
	}

	/**
	 * Constructor.
	 * @param x -- x coordinate
	 * @param y -- y coordinate
	 * @param w -- width
	 * @param h -- height
	 */
	public WorldAttributes(float x, float y, float w, float h)
	{
		this(new Vector2f(x, y), w, h);
	}

	/**
	 * get width
	 * @return width
	 */
	public float getWidth()
	{
		return this.width;
	}

	/**
	 * get height
	 * @return height
	 */
	public float getHeight()
	{
		return this.height;
	}

	/**
	 * get origin coordinate
	 * @return origin coordinate
	 */
	public Vector2f getOriginCoord()
	{
		return new Vector2f(this.coord);
	}

	/**
	 * get top left coordinate from origin
	 * @return top left coord coordinate
	 */
	public Vector2f getTopLeftCoordFromOrigin()
	{
		return new Vector2f(this.coord.x - width / 2f,
				    this.coord.y - height / 2f);
	}


	/**
	 * get bottom left coordinate from origin
	 * @return bottom left coord coordinate
	 */
	public Vector2f getBottomRightCoordFromOrigin()
	{
		return new Vector2f(this.coord.x + width / 2f,
				    this.coord.y + height / 2f);
	}

	/**
	 * get centered bottom coordinate from origin
	 * @return centered bottom coord coordinate
	 */
	public Vector2f getCenteredBottomQuarter()
	{
		return new Vector2f(this.coord.x + width / 7f,
				    this.coord.y + height / 7f);
	}


	// setters
	/**
	 * set origin center
	 * @param c -- new val
	 */
	public void setOriginCoord(Vector2f c)
	{

		this.coord = c;
	}

	/**
	 * set width
	 * @param w -- new width
	 */
	public void setWidth(float w)
	{
		this.width = w;
	}

	/**
	 * set height
	 * @param h -- new height
	 */
	public void setHeight(float h)
	{
		this.height = h;
	}

	/**
	 * add to origin coord
	 * @param x -- add to coord in x direction
	 * @param y -- add to coord in y direction
	 */
	public void add(float x, float y)
	{
		this.coord.add(x, y);
	}

	/**
	 * add to origin coord
	 * @param n -- translation
	 */
	public void add(Vector2f n)
	{
		this.coord.add(n.x, n.y);
	}

	/**
	 * print
	 */
	public void print()
	{
		System.out.println("WorldCoordinate Component: x = " + coord.x
				   + " y = " + coord.y + "width = " + width
				   + " height =  " + height);
	}
}
