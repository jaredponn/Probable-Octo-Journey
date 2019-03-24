package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

/**
 * Health pack that heals the player on pick-up
 * @author Alex
 * @version 1.0 - 03/20/19
 */
public class HealthPack extends EntitySet {

	public HealthPack( double spawnTime ) {
		
		this( 1f , 1f , spawnTime );
	}
	
	public HealthPack( float x , float y , double spawnTime ) {
		
		super();
		
		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.healthImage)));

		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.PICKUP_WIDTH,
						 GameConfig.PICKUP_HEIGHT));

		addComponent(new Lifespan(GameConfig.PICKUP_HEALTHPACK_SPAWN_TIME,
					  spawnTime));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PICKUP_COLLISION_BODY));
	}
	
	public HealthPack(Vector2f posVector , double spawnTime ) {
		
		this( posVector.x , posVector.y , spawnTime );
	}
	
}
