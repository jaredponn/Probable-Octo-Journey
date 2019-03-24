package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

/**
 * Power-up to allow the player to do more damage
 * @author Alex
 * @version 1.0 - 03/18/19
 */
public class PowerUp extends EntitySet {

	public PowerUp( double spawnTime ) {
		
		this( 1f , 1f , spawnTime );
	}
	
	public PowerUp( float x , float y , double spawnTime ) {
		
		super();
		
		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.powerupImage)));

		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.PICKUP_WIDTH,
						 GameConfig.PICKUP_HEIGHT));

		addComponent(new Lifespan(GameConfig.PICKUP_POWERUP_SPAWN_TIME,
					  spawnTime));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PICKUP_COLLISION_BODY));
	}
	
	public PowerUp(Vector2f posVector , double spawnTime ) {
		
		this( posVector.x , posVector.y , spawnTime );
	}
	
}
