package EntitySets;

import poj.EntitySet.*;
import Components.*;

public class PlayerSet extends EntitySet
{

	public PlayerSet()
	{
		super();
		addComponent(new Physics(3));
	}
}
