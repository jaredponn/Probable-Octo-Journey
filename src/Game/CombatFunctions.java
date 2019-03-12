package Game;

import Components.FacingDirection;
import Components.HasAnimation;
import Components.AttackCycle;
import Components.HitPoints;
import Components.Lifespan;
import Components.Movement;
import Components.MovementDirection;
import Components.PHitBox;
import Components.PhysicsPCollisionBody;
import Components.Render;
import Components.WorldAttributes;
import EntitySets.Bullet;
import EntitySets.CollectibleSet;
import EntitySets.MobSet;
import Resources.GameConfig;

import poj.EngineState;
import poj.Collisions.GJK;

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
		engineState.deleteComponentAt(Render.class, bullet);
		engineState.deleteComponentAt(WorldAttributes.class, bullet);
		engineState.deleteComponentAt(Movement.class, bullet);
		engineState.deleteComponentAt(Lifespan.class, bullet);
		engineState.deleteComponentAt(PhysicsPCollisionBody.class,
					      bullet);
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
				removeBullet(mainState, bullet);
				mainState.getComponentAt(HitPoints.class, i)
					.hurt(GameConfig.BULLET_DAMAGE);
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

		if (Systems.arePCollisionBodiesColliding(gjk, playerBody,
							 mobBody)) {
			engineState.getComponentAt(AttackCycle.class, mob)
				.startAttackCycle();
			handlePlayerDamage(engineState, player,
					   GameConfig.MOB_ATTACK_DAMAGE);
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
}
