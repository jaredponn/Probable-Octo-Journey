package EntitySets;

import Components.*;
import poj.EntitySet.*;

public class ExampleEntitySet extends EntitySet
{
	public ExampleEntitySet()
	{
		super(); // must be called the
		addComponent(new WorldAttributes(
			3, 3)); // add new components to the set like this
	}
}
