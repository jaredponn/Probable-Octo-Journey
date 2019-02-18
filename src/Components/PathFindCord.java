package Components;

import poj.Component.Component;
import poj.linear.MatrixCord;

public class PathFindCord implements Component
{
	private MatrixCord cord;
	private boolean isWall;
	public PathFindCord(MatrixCord cord, boolean isWall)
	{
		this.cord = cord;
		this.isWall = isWall;
	}

	public boolean getIsWall()
	{
		return this.isWall;
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
