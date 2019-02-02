package EntitySets;

import Components.*;    //include this
import poj.EntitySet.*; //include this

public class ExampleEntitySet extends EntitySet // ensure it extends this
{
	public ExampleEntitySet()
	{
		super(); // must be called before everything

		addComponent(new WorldAttributes(
			3, 3)); // add new components to the set like this

		// DO NOT ADD A COMPONENT TWICE
		// VERY BAD DO NOT DO THIS
		/* addComponent(new WorldAttributes(3, 3));*/

		// Adding different components is totally okay though: Add as
		// many as you need:
		addComponent(new Velocity(3));
	}
}
