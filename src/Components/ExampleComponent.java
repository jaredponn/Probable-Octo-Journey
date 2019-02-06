package Components;

import poj.Component.Component; //must include this import
import poj.Logger.*;		//must include this import

public class ExampleComponent
	implements Component // ensure that it implements component
{
	public ExampleComponent()
	{
	}

	public void print() // You must write a print method for all components
	{
		Logger.logMessage(
			"Example Component: description of values here",
			LogLevels.VERBOSE);
	}
}
