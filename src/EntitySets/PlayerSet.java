package EntitySets;

import poj.EntitySet;
import Components.*;

public class PlayerSet extends EntitySet
{

	PlayerSet()
	{
		super();
		addComponent(new Physics(3));
	}
}
