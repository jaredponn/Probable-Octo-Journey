package poj;

import poj.Logger;
import poj.LogLevels;

public class Component
{
	public void print()
	{
		System.out.println(this.getClass());
		Logger.logMessage(
			"All components should implement a print method",
			LogLevels.VERBOSE);
	}
}

