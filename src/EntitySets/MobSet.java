package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

public class MobSet extends EntitySet {

	public MobSet() {
		
		super();
		
		addComponent( new Speed(GameConfig.MOB_VELOCITY));
		
		addComponent(new Render(new ImageRenderObject(
				0, 0, GameResources.playerSpriteSheet)));
		// TODO: mob sprite sheet
		
		addComponent(new WorldAttributes(new Vector2f(50.f, 50.f),
				 GameConfig.MOB_WIDTH,
				 GameConfig.MOB_HEIGHT));
		// TODO: make mobs a different size than the player?
		
		// TODO: mob animations (walking, idle, attacking, dying)
	}
}
