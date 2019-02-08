package EntitySets;

import poj.EntitySet.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class PlayerSet extends EntitySet
{

	public PlayerSet()
	{
		super();
		addComponent(new Physics(3));
		addComponent(new Render(ImageRenderObject()));
	}
}
