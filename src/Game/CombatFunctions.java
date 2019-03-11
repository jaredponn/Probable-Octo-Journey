package Game;

import EntitySets.Bullet;
import EntitySets.MobSet;
import Components.Render;
import Components.WorldAttributes;
import Components.HasAnimation;
import Components.Movement;
import Components.MovementDirection;
import Components.FacingDirection;
import Components.PCollisionBody;
import Components.HitPoints;
import Components.Lifespan;
import poj.EngineState;

public class CombatFunctions {

	public static void removeMob( EngineState engineState , int mob ) {
		engineState.deleteComponentAt( MobSet.class , mob );
		engineState.deleteComponentAt( WorldAttributes.class , mob );
		engineState.deleteComponentAt( Render.class, mob);
		engineState.deleteComponentAt( HasAnimation.class, mob );
		engineState.deleteComponentAt( Movement.class, mob );
		engineState.deleteComponentAt( MovementDirection.class, mob );
		engineState.deleteComponentAt( FacingDirection.class, mob );
		engineState.deleteComponentAt( PCollisionBody.class, mob );
		engineState.deleteComponentAt( HitPoints.class, mob );
		engineState.markIndexAsFree( mob );
	}
	
	public static void removeBullet( EngineState engineState , int bullet ) {
		System.out.println("Bullet Deleted");
		engineState.deleteComponentAt( Bullet.class  , bullet );
		engineState.deleteComponentAt( Render.class , bullet );
		engineState.deleteComponentAt( WorldAttributes.class , bullet );
		engineState.deleteComponentAt( Movement.class , bullet );
		engineState.deleteComponentAt( Lifespan.class , bullet );
		engineState.deleteComponentAt( PCollisionBody.class , bullet );
		engineState.markIndexAsFree( bullet );
	}
}

