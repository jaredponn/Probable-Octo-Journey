package EntitySets;

import poj.EntitySet.*;
import Resources.GameResources;
import Resources.GameConfig;
import poj.linear.*;
import poj.Render.ImageRenderObject;
import Components.*;

/**
 * Ammo pack that gives the player more bullets to shoot
 * @author Alex
 * @version 1.0 - 03/18/19
 */
public class AmmoPack extends EntitySet {

	public AmmoPack( double spawnTime ) {
		
		this( 1f , 1f , spawnTime );
	}
	
	public AmmoPack( float x , float y , double spawnTime ) {
		
		super();
		
		addComponent(new Render(
			new ImageRenderObject(0, 0, GameResources.cashImage)));

		addComponent(new WorldAttributes(new Vector2f(x, y),
						 GameConfig.PICKUP_WIDTH,
						 GameConfig.PICKUP_HEIGHT));

		addComponent(new Lifespan(GameConfig.PICKUP_AMMOPACK_SPAWN_TIME,
					  spawnTime));
		addComponent(new PhysicsPCollisionBody(
			GameConfig.PICKUP_COLLISION_BODY));
	}
	
	public AmmoPack(Vector2f posVector , double spawnTime ) {
		
		this( posVector.x , posVector.y , spawnTime );
	}
	
}
