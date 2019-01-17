package EntitySets;

import Components.*;
import poj.EntitySet.*;

public class B extends EntitySet
{
	public B()
	{
		super();
		addComponent(new Physics(10));
		addComponent(new Render(10, 3));
	}
}
