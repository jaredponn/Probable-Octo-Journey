package Components;
import poj.Component.Component;
import poj.linear.Vector2f;
import poj.linear.Rectanglef;

public class WorldAttributes implements Component
{
	private Vector2f coord; // the origin of object
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

	public WorldAttributes(Vector2f c, float w, float h)
	{
		this.coord = c;
		this.width = w;
		this.height = h;
	}

	public WorldAttributes(float x, float y, float w, float h)
	{
		this(new Vector2f(x, y), w, h);
	}


	// basic getters
	public float getWidth()
	{
		return this.width;
	}

	public float getHeight()
	{
		return this.height;
	}

	// getters for getting coordinates from the origin (center)
	public Vector2f getOriginCoord()
	{
		return this.coord;
	}

	public Vector2f getTopLeftCoordFromOrigin()
	{
		return new Vector2f(this.coord.x - width / 2.f,
				    this.coord.y - height / 2.f);
	}

	public Rectanglef getCenteredRect()
	{
		return new Rectanglef(
			coord.x - width / 2.f, coord.y - height / 2.f,
			coord.x + width / 2.f, coord.y + height / 2.f);
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

	// print
	public void print()
	{
		System.out.println("WorldCoordinate Component: x = " + coord.x
				   + " y = " + coord.y);
	}
}
