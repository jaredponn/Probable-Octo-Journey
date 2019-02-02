package Components;

import poj.Component.Component; //must include this import

public class ExampleComponent
	implements Component // ensure that it implements component
{

	public void print() // You must write a print method for all components
	{
		System.out.println(
			"Example Component: description of values here");
	}
}
