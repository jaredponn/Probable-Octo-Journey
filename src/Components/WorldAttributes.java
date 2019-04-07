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

	// constructor: intializes the object outside the map, with a
	// width and height of 0
	public WorldAttributes()
	{
		this(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE), 0, 0);
	}

	// constructor: intializes the object outside the map, with a specified
	// width and height
	public WorldAttributes(float w, float h)
	{
		this(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE), w, h);
	}

	// notice -- the vector is the origin coodinate
	public WorldAttributes(Vector2f c, float w, float h)
	{
		this.coord = c;
		this.width = w;
		this.height = h;
	}
	public WorldAttributes(Vector2f c)
	{
		this.coord = c;
		this.width = 0;
		this.height = 0;
	}

	public WorldAttributes(float x, float y, float w, float h)
	{
		this(new Vector2f(x, y), w, h);
	}

	public float getWidth()
	{
		return this.width;
	}

	public float getHeight()
	{
		return this.height;
	}

	public Vector2f getOriginCoord()
	{
		return new Vector2f(this.coord);
	}

	public Vector2f getTopLeftCoordFromOrigin()
	{
		return new Vector2f(this.coord.x - width / 2f,
				    this.coord.y - height / 2f);
	}


	public Vector2f getBottomRightCoordFromOrigin()
	{
		return new Vector2f(this.coord.x + width / 2f,
				    this.coord.y + height / 2f);
	}

	public Vector2f getCenteredBottomQuarter()
	{
		return new Vector2f(this.coord.x + width / 7f,
				    this.coord.y + height / 7f);
	}

	// unstable
	public Rectanglef getCenteredRect()
	{
		Vector2f topleft = this.getOriginCoord();
		Vector2f botright = this.getBottomRightCoordFromOrigin();
		return new Rectanglef(topleft.x, topleft.y, botright.x,
				      botright.y);
	}


	// setters
	public void setOriginCoord(Vector2f c)
	{

		this.coord = c;
	}

	public void setWidth(float w)
	{
		this.width = w;
	}

	public void setHeight(float h)
	{
		this.height = h;
	}

	public void add(float x, float y)
	{
		this.coord.add(x, y);
	}

	public void add(Vector2f n)
	{
		this.coord.add(n.x, n.y);
	}

	// print
	public void print()
	{
		System.out.println("WorldCoordinate Component: x = " + coord.x
				   + " y = " + coord.y + "width = " + width
				   + " height =  " + height);
	}
}
