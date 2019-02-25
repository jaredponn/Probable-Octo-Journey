package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class CollectibleSet extends EntitySet{

	public CollectibleSet() {
		super();
		
		addComponent(new Render(new ImageRenderObject(
				0, 0, GameResources.cashImage)));
		
		addComponent(new WorldAttributes(new Vector2f(1f, 1f),
				 GameConfig.MOB_WIDTH,
				 GameConfig.MOB_HEIGHT));
	}
	
	public CollectibleSet( float x , float y) {
		super();
		
		addComponent(new Render(new ImageRenderObject(
				0, 0, GameResources.cashImage)));
		
		addComponent(new WorldAttributes(new Vector2f( x , y ),
				 GameConfig.MOB_WIDTH,
				 GameConfig.MOB_HEIGHT));
	}
}
