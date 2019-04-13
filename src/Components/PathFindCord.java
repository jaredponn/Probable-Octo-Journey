package Components;

/**
 * PathFindingCoordinate. PathFindingCoordiante component
 *
 * Date: March 10, 2019
 * @author Haiyang He
 * @version 1.0
 */

import poj.Component.Component;
import poj.linear.Vector2f;

public class PathFindCord implements Component
{
	private Vector2f cord;
	private boolean isWall;
	private float diffusionValue;

	/**
	 * Constructor
	 * @param cord -- vector coord
	 * @param isWall -- is wall
	 * @param diffusionValue -- diffusion value
	 */
	public PathFindCord(Vector2f cord, boolean isWall, int diffusionValue)
	{
		this.cord = cord;
		this.isWall = isWall;
		this.diffusionValue = diffusionValue;
	}

	/**
	 * get is wall?
	 * @return wall boolean
	 */
	public boolean getIsWall()
	{
		return this.isWall;
	}

	/**
	 * set is wall
	 * @param newWall -- value to set
	 */
	public void setIsWall(boolean newWall)
	{
		this.isWall = newWall;
	}


	/**
	 * get diffusion value
	 * @return  get's the diffusionValue
	 */
	public float getDiffusionValue()
	{
		return this.diffusionValue;
	}

	/**
	 * Sets diffusion value
	 * @param  newdifval -- new diffusion value
	 */
	public void setDiffusionValue(float newDifVal)
	{
		this.diffusionValue = newDifVal;
	}

	/**
	 * adds diffusion value
	 * @param  newdifval -- new diffusion value to add to current val
	 */
	public void addDiffusionValue(float newDifVal)
	{
		this.diffusionValue += newDifVal;
	}

	/**
	 * get cord
	 * @return  coordinate
	 */
	public Vector2f getCord()
	{
		return new Vector2f(this.cord);
	}

	/**
	 * print
	 */
	public void printCord()
	{
		System.out.println("cord x =" + this.cord.x);
		System.out.println("cord y =" + this.cord.y);
		System.out.println("----------");
	}

	/**
	 * print
	 */
	public void printDiffusionVal()
	{
		System.out.println("diffusion value = " + this.diffusionValue);
	}
	/**
	 * print
	 */
	public void printWall()
	{
		if (isWall) {
			System.out.println("True");
		} else {
			System.out.println("False");
		}
	}

	/**
	 * print
	 */
	public void print()
	{
		System.out.println("hi from PathFindCord");
	}
}
