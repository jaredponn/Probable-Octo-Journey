package Game;

import EntitySets.Bullet;
import EntitySets.CollectibleSet;
import EntitySets.MobSet;
import Components.Render;
import Components.WorldAttributes;
import Components.HasAnimation;
import Components.Movement;
import Components.MovementDirection;
import Components.FacingDirection;
import Components.PhysicsPCollisionBody;
import Components.HitPoints;
import Components.Lifespan;
import poj.EngineState;

/**
 * A collection of methods to help handle combat and entity removal
 * @author Alex
 *
 */
public class CombatFunctions {

	public static void removeMob( EngineState engineState , int mob ) {
		engineState.deleteComponentAt( MobSet.class , mob );
		engineState.deleteComponentAt( WorldAttributes.class , mob );
		engineState.deleteComponentAt( Render.class, mob);
		engineState.deleteComponentAt( HasAnimation.class, mob );
		engineState.deleteComponentAt( Movement.class, mob );
		engineState.deleteComponentAt( MovementDirection.class, mob );
		engineState.deleteComponentAt( FacingDirection.class, mob );
		engineState.deleteComponentAt( PhysicsPCollisionBody.class, mob );
		engineState.deleteComponentAt( HitPoints.class, mob );
		engineState.markIndexAsFree( mob );
	}
	
	public static void removeBullet( EngineState engineState , int bullet ) {
		engineState.deleteComponentAt( Bullet.class  , bullet );
		engineState.deleteComponentAt( Render.class , bullet );
		engineState.deleteComponentAt( WorldAttributes.class , bullet );
		engineState.deleteComponentAt( Movement.class , bullet );
		engineState.deleteComponentAt( Lifespan.class , bullet );
		engineState.deleteComponentAt( PhysicsPCollisionBody.class , bullet );
		engineState.markIndexAsFree( bullet );
	}
	
	public static void removePickUp( EngineState engineState , int p ) {
		engineState.deleteComponentAt(CollectibleSet.class, p);
		engineState.deleteComponentAt(Render.class, p);
		engineState.deleteComponentAt(WorldAttributes.class, p);
		engineState.deleteComponentAt(Lifespan.class, p);
		engineState.deleteComponentAt(PhysicsPCollisionBody.class, p);
		engineState.markIndexAsFree(p);
	}
	
}

