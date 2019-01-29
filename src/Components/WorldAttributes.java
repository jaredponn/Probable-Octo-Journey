package Components;
import poj.Component.Component;
import poj.linear.Vector2f;
import poj.linear.Rectanglef;

public class WorldAttributes implements Component
{
	private Vector2f coord;
	private float width;
	private float height;

	// constructor: intializes the object outside the map, with a
	// width and height of 0
	public WorldAttributes()
	{
		this.coord = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
		this.width = 0;
		this.height = 0;
	}

	// constructor: intializes the object outside the map, with a specified
	// width and height
	public WorldAttributes(float w, float h)
	{
		this.coord = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
		this.width = w;
		this.height = h;
	}

	public WorldAttributes(Vector2f c, float w, float h)
	{
		this.coord = c;
		this.width = w;
		this.height = h;
	}

	public WorldAttributes(float x, float y, float w, float h)
	{
		this.coord = new Vector2f(x, y);
		this.width = w;
		this.height = h;
	}


	// basic getters
	public Vector2f getCoord()
	{
		return this.coord;
	}

	public float getWidth()
	{
		return this.width;
	}

	public float getHeight()
	{
		return this.height;
	}

	// getters for getting coordinates where the orignal coord is treated as
	// the origin
	public Vector2f getOriginCoord()
	{
		return new Vector2f(this.coord.x + width / 2.f,
				    this.coord.x + height / 2.f);
	}

	public Rectanglef getCenteredRect()
	{
		return new Rectanglef(
			coord.x - width / 2.f, coord.y - height / 2.f,
			coord.x + width / 2.f, coord.y + height / 2.f);
	}


	// setters
	public void setCoord(Vector2f c)
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

	// print
	public void print()
	{
		System.out.println("WorldCoordinate Component: x = " + coord.x
				   + " y = " + coord.y);
	}
}
