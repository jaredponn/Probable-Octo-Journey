package Components;

import poj.Component.Component; //must include this import
import poj.Logger.*;		//must include this import

public class PathfindSeek implements Component
{
	protected boolean isPathfinding;

	public PathfindSeek(boolean a)
	{
		isPathfinding = a;
	}


	public boolean isPathfinding()
	{
		return isPathfinding;
	}

	public boolean isNotPathfinding()
	{
		return !isPathfinding;
	}

	public void stopPathfinding()
	{
		isPathfinding = false;
	}

	public void startPathfinding()
	{
		isPathfinding = true;
	}

	public void print()
	{
	}
}
