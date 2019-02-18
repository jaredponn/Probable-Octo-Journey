package Components;

import poj.Component.Component;
import poj.linear.MatrixCord;

public class PathFindCord implements Component
{
	private MatrixCord cord;
	private boolean isWall;
	private int diffusionValue;
	public PathFindCord(MatrixCord cord, boolean isWall, int diffusionValue)
	{
		this.cord = cord;
		this.isWall = isWall;
		this.diffusionValue = diffusionValue;
	}

	public boolean getIsWall()
	{
		return this.isWall;
	}
	public int getDiffusionValue()
	{
		return this.diffusionValue;
	}
	public void setDiffusionValue(int newDifVal)
	{
		this.diffusionValue = newDifVal;
	}

	public MatrixCord getCord()
	{
		return this.cord;
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
