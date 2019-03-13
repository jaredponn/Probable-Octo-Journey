package Game;

import Components.*;
import EntitySets.Bullet;
import EntitySets.CollectibleSet;
import EntitySets.MobSet;
import EntitySets.CannonShell;
import EntitySets.TurretSet;
import Resources.GameConfig;

import poj.EngineState;
import poj.Collisions.GJK;
import poj.linear.Vector2f;

/**
 * A collection of methods to help handle combat and entity removal
 * @author Alex Stark
 * @version 03/09/19 v1.0
 */
public class CombatFunctions
{

	/**
	 * Removes a mob from an engine state
	 * @param engineState: that the mob is in
	 * @param mob: to be removed
	 */
	public static void removeMob(EngineState engineState, int mob)
	{
		engineState.deleteComponentAt(MobSet.class, mob);
		engineState.deleteComponentAt(WorldAttributes.class, mob);
		engineState.deleteComponentAt(Render.class, mob);
		engineState.deleteComponentAt(HasAnimation.class, mob);
		engineState.deleteComponentAt(Movement.class, mob);
		engineState.deleteComponentAt(MovementDirection.class, mob);
		engineState.deleteComponentAt(FacingDirection.class, mob);
		engineState.deleteComponentAt(PhysicsPCollisionBody.class, mob);
		engineState.deleteComponentAt(PHitBox.class, mob);
		engineState.deleteComponentAt(HitPoints.class, mob);
		engineState.deleteComponentAt(AttackCycle.class, mob);
		engineState.markIndexAsFree(mob);
	}

	/**
	 * Removes a bullet from an engine state
	 * @param engineState: that the bullet is in
	 * @param bullet: to be removed
	 */
	public static void removeBullet(EngineState engineState, int bullet)
	{
		engineState.deleteComponentAt(Bullet.class, bullet);
		engineState.deleteComponentAt(CannonShell.class, bullet);//TODO: separate deleteShell method?
		engineState.deleteComponentAt(Render.class, bullet);
		engineState.deleteComponentAt(WorldAttributes.class, bullet);
		engineState.deleteComponentAt(Movement.class, bullet);
		engineState.deleteComponentAt(Lifespan.class, bullet);
		engineState.deleteComponentAt(PhysicsPCollisionBody.class,
					      bullet);
		engineState.deleteComponentAt(Damage.class, bullet);
		engineState.markIndexAsFree(bullet);
	}

	/**
	 * Removes a pick-up from an engine state
	 * @param engineState: that the pick-up is in
	 * @param p: pick-up to be removed
	 */
	public static void removePickUp(EngineState engineState, int p)
	{
		engineState.deleteComponentAt(CollectibleSet.class, p);
		engineState.deleteComponentAt(Render.class, p);
		engineState.deleteComponentAt(WorldAttributes.class, p);
		engineState.deleteComponentAt(Lifespan.class, p);
		engineState.deleteComponentAt(PhysicsPCollisionBody.class, p);
		engineState.markIndexAsFree(p);
	}
	
	/**
	 * Removes a turret from an engine state
	 * @param engineState: that the turret is in
	 * @param t: turret to be removed
	 */
	public static void removeTurret(EngineState engineState , int t )
	{
		engineState.deleteComponentAt(TurretSet.class, t);
		engineState.deleteComponentAt(Render.class, t);
		engineState.deleteComponentAt(WorldAttributes.class, t);
		engineState.deleteComponentAt(Lifespan.class, t);
		engineState.deleteComponentAt(PhysicsPCollisionBody.class, t);
		engineState.deleteComponentAt(HitPoints.class, t);
		engineState.deleteComponentAt(PHitBox.class, t);
		engineState.deleteComponentAt(AttackCycle.class, t);
		engineState.markIndexAsFree(t);
	}


	/**
	 * Handler for bullet collisions
	 * @param mainState: The main game state
	 * @param mapState: The map layer that contains objects that should
	 *         block projectiles
	 * @param gjk: must pass the GJK to handle collisions
	 * @param bullet: the bullet that you are checking for collisions
	 */
	public static void bulletHitHandler(EngineState mainState,
					    EngineState mapState, GJK gjk,
					    int bullet)
	{
		final PhysicsPCollisionBody bulletBody =
			mainState.getComponentAt(PhysicsPCollisionBody.class,
						 bullet);

		// check for bullet collision with any mob
		for (int i = mainState.getInitialSetIndex(MobSet.class);
		     poj.EngineState.isValidEntity(i);
		     i = mainState.getNextSetIndex(MobSet.class, i)) {

			final PHitBox mobBody =
				mainState.getComponentAt(PHitBox.class, i);

			// if collision detected
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletBody, mobBody)) {
				mainState.getComponentAt(HitPoints.class, i)
					.hurt(mainState.getComponentAt(Damage.class, bullet).getDamage());
				removeBullet(mainState, bullet);
				if (mainState.getComponentAt(HitPoints.class, i)
					    .getHP()
				    <= 0)
					removeMob(mainState, i);
				break;
			}
		}

		// check for bullet collision with wall
		for (PhysicsPCollisionBody wall :
		     mapState.getRawComponentArrayListPackedData(
			     PhysicsPCollisionBody.class)) {
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletBody, wall)) {
				removeBullet(mainState, bullet);
				break;
			}
		}
	}

	/**
	 * Handler for mobs hitting the player
	 * @param engineState: the main game state
	 * @param gjk: GJK needed to handle collisions
	 * @param mob: the mob that is attacking the player
	 * @param player: reference to the player
	 */
	public static void handleMobHitPlayer(EngineState engineState, GJK gjk,
					      int mob, int player)
	{
		final PhysicsPCollisionBody playerBody =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   player);
		final PhysicsPCollisionBody mobBody =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   mob);

		// temporarily just cause damage on collision
		// TODO: make mobs attack
		if (Systems.arePCollisionBodiesColliding(gjk, playerBody,
							 mobBody)) {
			engineState.getComponentAt(AttackCycle.class, mob)
				.startAttackCycle();
			handlePlayerDamage(engineState, player,
					   GameConfig.MOB_ATTACK_DAMAGE);
		}
	}
	
	/**
	 * Handler for mobs hitting a turret
	 * @param engineState: the main game state
	 * @param gjk: GJK needed to handle collisions
	 * @param mob: the mob that is attacking the turret
	 * @param player: reference to the turret
	 */
	public static void handleMobHitTurret(EngineState engineState , GJK gjk , int mob , int turret) {
		final PHitBox turretBody =
				engineState.getComponentAt(PHitBox.class,
							   turret);
		final PhysicsPCollisionBody mobBody =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   mob);
		
		// TODO: make mobs attack
		if (Systems.arePCollisionBodiesColliding(gjk, turretBody, mobBody)) {
			HitPoints turretHP = engineState.getComponentAt(HitPoints.class, turret);
			System.out.println("A mob is hitting a turret! Turret HP: "+ turretHP.getHP());
			
			turretHP.hurt(GameConfig.MOB_ATTACK_DAMAGE);
			
			if (turretHP.getHP() <= 0 )
				removeTurret( engineState , turret );
		}
	}

	/**
	 * Applies damage to player, and handles player death
	 * @param engineState: the main game state
	 * @param player: reference to the player
	 * @param amount: amount of damage applied to the player
	 */
	public static void handlePlayerDamage(EngineState engineState,
					      int player, int amount)
	{
		HitPoints playerHP =
			engineState.getComponentAt(HitPoints.class, player);
		playerHP.hurt(amount);

		if (playerHP.getHP() <= 0) {
			System.out.println(
				"---------------\n"
				+ "The player has been killed!!!\n---GAME OVER---");
			// System.exit(0);
		}
	}
	
	/**
	 * Handler for selecting targets for turrets
	 * @param engineState: the current state of the game
	 * @param turret: the turret that will be targeting something
	 * @param gameTime: the time the game has been running
	 */
	public static void turretTargeting(EngineState engineState , int turret , double gameTime ) {
		Vector2f turretPosition = engineState.getComponentAt(PHitBox.class,turret)
				.getPolygon().pureGetAPointInPolygon(0);
		int currentTarget = 0;
		
		// TODO: refine targeting (often misses target)
		
		// cycle through all mobs and find the closest one.
		for (int i = engineState.getInitialSetIndex(MobSet.class);
			     poj.EngineState.isValidEntity(i);
			     i = engineState.getNextSetIndex(MobSet.class, i)) {
			currentTarget = i;
			
			Vector2f mob1Position = engineState.getComponentAt(PHitBox.class, i)
					.getPolygon().pureGetAPointInPolygon(1);
			
			Vector2f tmp =
					turretPosition.pureSubtract(mob1Position);
				tmp.negate();
			Vector2f unitVecturretPosTomob1Delta =
				tmp.pureNormalize();
			
			// compare distance to this mob with distance to other mobs
			for (int j = engineState.getInitialSetIndex(MobSet.class);
					poj.EngineState.isValidEntity(j);
					j = engineState.getNextSetIndex(MobSet.class, j )) {
				
				Vector2f mob2Position = engineState.getComponentAt(PHitBox.class, j)
						.getPolygon().pureGetAPointInPolygon(1);
				
				Vector2f tmp2 =
						turretPosition.pureSubtract(mob2Position);
					tmp2.negate();
				Vector2f unitVecturretPosTomob2Delta =
					tmp2.pureNormalize();
				
				if (unitVecturretPosTomob2Delta.lessThan(unitVecturretPosTomob1Delta)) {
					unitVecturretPosTomob1Delta = unitVecturretPosTomob2Delta;
					currentTarget = j;
				}
			}
			
			// TODO: limit turrets range/make them only fire
			//		 at targets within that range
			//shootTurret(engineState , turret , currentTarget , gameTime );
		}
		shootTurret(engineState , turret , currentTarget , gameTime );
	}
	
	/**
	 * Handler for turrets shooting at mobs
	 * @param engineState: the main game state
	 * @param turret: the turret the is shooting
	 * @param target: the mob that is being shot at
	 * @param gameTime: the time that the game has been running
	 */
	public static void shootTurret(EngineState engineState , int turret , int target , double gameTime) {
		Vector2f turretPosition = engineState.getComponentAt(
				PHitBox.class,turret)
				.getPolygon().pureGetAPointInPolygon(0);
		
		Vector2f targetPosition = engineState.getComponentAt(
				PHitBox.class,target)
				.getPolygon().pureGetAPointInPolygon(0);
		
		Vector2f tmp =
				turretPosition.pureSubtract(targetPosition);
			tmp.negate();
		Vector2f unitVecturretPosTotargetDelta =
			tmp.pureNormalize();
		
		int e = engineState.spawnEntitySet(
				new CannonShell(gameTime , turretPosition));
			engineState
				.getComponentAt(PhysicsPCollisionBody.class, e)
				.setPositionPoint(
					engineState
						.getComponentAt(
							WorldAttributes.class,
							turret)
						.getCenteredBottomQuarter());
			float shellSpeed =
				engineState.getComponentAt(Movement.class, e)
					.getSpeed();

			engineState.getComponentAt(Movement.class, e)
				.setVelocity(
						unitVecturretPosTotargetDelta.pureMul(
						shellSpeed));	
	}
}
