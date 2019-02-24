package Components;

import poj.Component.Component;
import poj.linear.Vector2f;

public class PathFindCord implements Component
{
	private Vector2f cord;
	private boolean isWall;
	private float diffusionValue;
	public PathFindCord(Vector2f cord, boolean isWall, int diffusionValue)
	{
		this.cord = cord;
		this.isWall = isWall;
		this.diffusionValue = diffusionValue;
	}

	public PathFindCord(PathFindCord newcord)
	{
		this.cord = newcord.getCord();
		this.isWall = newcord.getIsWall();
		this.diffusionValue = newcord.getDiffusionValue();
	}

	public boolean getIsWall()
	{
		return this.isWall;
	}
	public float getDiffusionValue()
	{
		return this.diffusionValue;
	}
	public void setDiffusionValue(float newDifVal)
	{
		this.diffusionValue = newDifVal;
	}

	public void addDiffusionValue(float newDifVal)
	{
		this.diffusionValue += newDifVal;
	}
	public Vector2f getCord()
	{
		return new Vector2f(this.cord);
	}
	public void printCord()
	{
		System.out.println("cord x =" + this.cord.x);
		System.out.println("cord y =" + this.cord.y);
		System.out.println("----------");
	}

	public void printDiffusionVal()
	{
		System.out.println("diffusion value = " + this.diffusionValue);
	}
	public void printWall()
	{
		if (isWall) {
			System.out.println("True");
		} else {
			System.out.println("False");
		}
	}

	public void print()
	{
		System.out.println("hi from PathFindCord");
	}
}
