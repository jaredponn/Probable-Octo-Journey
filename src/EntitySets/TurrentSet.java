package EntitySets;
import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class TurrentSet extends EntitySet
{
	public TurrentSet()
	{
		super();
		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.turrent)));
		addComponent(new WorldAttributes(new Vector2f(),
						 GameConfig.MOB_WIDTH,
						 GameConfig.MOB_HEIGHT));
	}
}
