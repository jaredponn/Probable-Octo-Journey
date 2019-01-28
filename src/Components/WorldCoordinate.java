package Components;
import poj.Component.Component;
import poj.linear.Vector2f;

public class WorldCoordinate implements Component
{
	private Vector2f coord;

	public WorldCoordinate(Vector2f c)
	{
		this.coord = c;
	}

	public Vector2f getCoord()
	{
		return this.coord;
	}

	public void setCoord(Vector2f c)
	{
		this.coord = c;
	}

	public void print()
	{
		System.out.println("WorldCoordinate Component");
		System.out.println();
		System.out.println("END WorldCoordinate Component");
	}
}
