package Components;

/**
 * PathfindSeek
 * @author Jared Pon
 * March 1, 2019
 * v 0.0
 */

import poj.Component.Component;
import poj.Logger.*;

public class PathfindSeek implements Component
{
	protected boolean isPathfinding;


	/**
	 * PathfindSeek
	 * @param a -- boolean
	 */
	public PathfindSeek(boolean a)
	{
		isPathfinding = a;
	}


	/**
	 * is pathfinding
	 * @return boolean
	 */
	public boolean isPathfinding()
	{
		return isPathfinding;
	}

	/**
	 * is not pathfinding
	 * @return boolean
	 */
	public boolean isNotPathfinding()
	{
		return !isPathfinding;
	}

	/**
	 * stop path finding
	 */
	public void stopPathfinding()
	{
		isPathfinding = false;
	}

	/**
	 * start path finding
	 */
	public void startPathfinding()
	{
		isPathfinding = true;
	}

	/**
	 * print
	 */
	public void print()
	{
		Logger.logMessage("PathfindSeek: " + this.isPathfinding);
	}
}
