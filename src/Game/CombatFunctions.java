package Game;

import java.math.*;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import Components.*;
import EntitySets.*;
import EntitySets.Bullet;
import EntitySets.CollectibleSet;
import EntitySets.MobSet;
import EntitySets.CannonShell;
import EntitySets.TurretSet;
import Resources.GameConfig;

import poj.EngineState;
import poj.Component.*;
import poj.Collisions.GJK;
import poj.linear.Vector2f;
import Resources.*;

/**
 * A collection of methods to help handle combat and entity removal
 * @author Alex Stark
 * @version 03/09/19 v1.0
 */
public class CombatFunctions
{

	/**
	 * Removes a bullet from an engine state
	 * @param engineState: that the bullet is in
	 * @param bullet: to be removed
	 */
	public static void removeBullet(EngineState engineState, int bullet)
	{
		engineState.deleteAllComponentsAt(bullet);
		engineState.markIndexAsFree(bullet);
	}

	/**
	 * Removes a pick-up from an engine state
	 * @param engineState: that the pick-up is in
	 * @param p: pick-up to be removed
	 */
	public static void removePickUp(EngineState engineState, int p)
	{
		engineState.deleteAllComponentsAt(p);
		engineState.markIndexAsFree(p);
	}

	/**
	 * Removes a turret from an engine state
	 * @param engineState: that the turret is in
	 * @param t: turret to be removed
	 */
	public static void removeTurret(EngineState engineState, int t)
	{
		engineState.deleteAllComponentsAt(t);
		engineState.markIndexAsFree(t);
	}

	/**
	 * Removes an entity with a lifespan component
	 * if it has reached the end of its lifespan
	 * @param g: the main game
	 * @param entity: the entity to be deleted
	 */
	public static void removeEntityWithLifeSpan(PlayGame g, int entity)
	{
		EngineState engineState = g.getEngineState();

		double spawnTime =
			engineState.unsafeGetComponentAt(Lifespan.class, entity)
				.getSpawnTime();
		double lifespan =
			engineState.unsafeGetComponentAt(Lifespan.class, entity)
				.getLifespan();

		if (g.getPlayTime() - spawnTime >= lifespan) {
			engineState.deleteAllComponentsAt(entity);
			engineState.markIndexAsFree(entity);
		}
	}


	/**
	 * Handler for bullet collisions
	 * @param g: godly game state
	 * @param bullet: the bullet that you are checking for collisions
	 */
	public static void bulletHitHandler(PlayGame g, int bullet)
	{
		EngineState mainState = g.getEngineState();
		EngineState mapState0 = g.getMap().getLayerEngineState(0);
		EngineState mapState1 = g.getMap().getLayerEngineState(1);
		EngineState mapState2 = g.getMap().getLayerEngineState(2);
		EngineState mapState3 = g.getMap().getLayerEngineState(3);
		EngineState mapState4 = g.getMap().getLayerEngineState(4);
		// map layer 0: ???
		//			 1: map edge and small rocks
		//			 2: cars and buildings
		//			 3: trees
		//			 4: lights and signs
		GJK gjk = g.getGJK();

		final PhysicsPCollisionBody bulletBody =
			mainState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, bullet);

		// check for bullet collision with any mob
		for (int i = mainState.getInitialSetIndex(MobSet.class);
		     poj.EngineState.isValidEntity(i);
		     i = mainState.getNextSetIndex(MobSet.class, i)) {

			final Optional<PHitBox> mobBodyOptional =
				mainState.getComponentAt(PHitBox.class, i);

			if (!mobBodyOptional.isPresent())
				continue;

			final PHitBox mobBody = mobBodyOptional.get();

			// if collision detected
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletBody, mobBody)) {

				mainState
					.unsafeGetComponentAt(HitPoints.class,
							      i)
					.hurt((int)(Math.floor(
						mainState
							.unsafeGetComponentAt(
								Damage.class,
								bullet)
							.getDamage()
						* g.playerDamageBonus)));
				removeBullet(mainState, bullet);


				return; // If it does hit something, it should
					// just delete the bullet (as seen here)
					// and just exit and not check if is
					// colliding with other things.
			}
		}

		// check for bullet collision with wall
		for (PhysicsPCollisionBody wall :
		     mapState1.getRawComponentArrayListPackedData(
			     PhysicsPCollisionBody.class)) {
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletBody, wall)) {
				removeBullet(mainState, bullet);
				return;
			}
		}
		for (PhysicsPCollisionBody wall :
		     mapState2.getRawComponentArrayListPackedData(
			     PhysicsPCollisionBody.class)) {
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletBody, wall)) {
				removeBullet(mainState, bullet);
				return;
			}
		}
		for (PhysicsPCollisionBody wall :
		     mapState3.getRawComponentArrayListPackedData(
			     PhysicsPCollisionBody.class)) {
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletBody, wall)) {
				removeBullet(mainState, bullet);
				return;
			}
		}
	}

	/**
	 * Generic Handler for mobs touching the player
	 * @param engineState: the main game state
	 * @param gjk: GJK needed to handle collisions
	 * @param a: entity set to start the attack cycle if touching
	 * @param b: entity set to check against
	 */
	public static void
	startAttackCycleOfSetAIfAggroCollisionBodyAreCollidingWithSetBPHitBox(
		EngineState engineState, GJK gjk, Class<? extends Component> a,
		Class<? extends Component> b)
	{
		for (int i = engineState.getInitialSetIndex(a);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(a, i)) {

			final Optional<AggroRange> abodyOptional =
				engineState.getComponentAt(AggroRange.class, i);

			Optional<AttackCycle> atkcycleOpt =
				engineState.getComponentAt(AttackCycle.class,
							   i);

			if (!atkcycleOpt.isPresent())
				continue;

			if (!abodyOptional.isPresent())
				continue;

			final PCollisionBody abody = abodyOptional.get();

			AttackCycle atkcycle = atkcycleOpt.get();

			for (int j = engineState.getInitialSetIndex(b);
			     engineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(b, j)) {

				final Optional<
					PhysicsPCollisionBody> bbodyOptional =
					engineState.getComponentAt(
						PhysicsPCollisionBody.class, j);


				if (!bbodyOptional.isPresent())
					continue;

				final PCollisionBody bbody =
					bbodyOptional.get();


				if (Systems.arePCollisionBodiesColliding(
					    gjk, bbody, abody)) {
					atkcycle.startAttackCycle();
				}
			}
		}
	}

	/**
	 * Handler for mobs touching turret
	 * @param engineState: the main game state
	 * @param gjk: GJK needed to handle collisions
	 * @param mob: the mob that is attacking the turret
	 * @param player: reference to the turret
	 */
	public static void handleMobTouchTurret(EngineState engineState,
						GJK gjk, int mob, int turret)
	{
		final PhysicsPCollisionBody mobBody =
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, mob);

		final PhysicsPCollisionBody turretBody =
			engineState.unsafeGetComponentAt(
				PhysicsPCollisionBody.class, turret);

		if (Systems.arePCollisionBodiesColliding(gjk, mobBody,
							 turretBody)) {
			Optional<AttackCycle> atkcycle =
				engineState.getComponentAt(AttackCycle.class,
							   mob);
			if (!atkcycle.isPresent())
				return;
			atkcycle.get().startAttackCycle();
		}
	}

	/**
	 * Handler for mobs hitting a turret
	 * @param engineState: the main game state
	 * @param gjk: GJK needed to handle collisions
	 * @param mob: the mob that is attacking the turret
	 * @param player: reference to the turret
	 */
	public static void handleMobDamageTurret(EngineState engineState,
						 int turret)
	{
		HitPoints turretHP = engineState.unsafeGetComponentAt(
			HitPoints.class, turret);
		System.out.println("A mob is hitting a turret! Turret HP: "
				   + turretHP.getHP());


		turretHP.hurt(GameConfig.MOB_ATTACK_DAMAGE);

		if (turretHP.getHP() <= 0)
			removeTurret(engineState, turret);
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
		HitPoints playerHP = engineState.unsafeGetComponentAt(
			HitPoints.class, player);
		playerHP.hurt(amount);
		if (playerHP.getHP() > 0) {
			// play player hurt sound
			int hurtSoundPlay =
				ThreadLocalRandom.current().nextInt(0, 4);
			switch (hurtSoundPlay) {
			case 0:
				GameResources.playerHpDropSound1.play();
				break;
			case 1:
				GameResources.playerHpDropSound2.play();
				break;
			case 2:
				GameResources.playerHpDropSound3.play();
				break;
			case 3:
				GameResources.playerHpDropSound4.play();
				break;
			}
		} else {
			// play death sound
			GameResources.playerDeathSound.play();
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
	public static void turretTargeting(EngineState engineState, int turret)
	{
		Vector2f turretPosition =
			engineState.unsafeGetComponentAt(PHitBox.class, turret)
				.getCenter();
		int currentTarget = 0;

		// find the first mob
		int i = engineState.getInitialSetIndex(MobSet.class);
		if (poj.EngineState.isValidEntity(i)) {
			// set the mob as curretTarget
			currentTarget = i;

			// find the centre of the mob's hit box
			Vector2f mob1Position =
				engineState
					.unsafeGetComponentAt(PHitBox.class, i)
					.getCenter();

			// find the vector from turret to target
			Vector2f tmp =
				turretPosition.pureSubtract(mob1Position);
			tmp.negate();
			Vector2f unitVecturretPosTomob1Delta = tmp;

			// find next mob and compare range
			for (int j = engineState.getNextSetIndex(MobSet.class,
								 currentTarget);
			     poj.EngineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(MobSet.class, j)) {

				// find centre of next mob's hit box
				Vector2f mob2Position =
					engineState
						.unsafeGetComponentAt(
							PHitBox.class, j)
						.getCenter();

				// find the vector from turret to this mob
				Vector2f tmp2 = turretPosition.pureSubtract(
					mob2Position);
				tmp2.negate();
				Vector2f unitVecturretPosTomob2Delta = tmp2;

				// if vector from turret to next mob is smaller
				// than turret to currentTarget, target next mob
				if (Vector2f.scalarValueOfVector(
					    unitVecturretPosTomob2Delta)
				    < Vector2f.scalarValueOfVector(
					      unitVecturretPosTomob1Delta)) {
					unitVecturretPosTomob1Delta = new Vector2f(
						unitVecturretPosTomob2Delta);
					currentTarget = j;
				}
			}
			// Limit turret range
			if (currentTarget > 0
			    && Vector2f.scalarValueOfVector(
				       unitVecturretPosTomob1Delta)
				       < 15)
				shootTurret(engineState, turret, currentTarget);
		}
	}

	/**
	 * Handler for turrets shooting at mobs
	 * @param engineState: the main game state
	 * @param turret: the turret the is shooting
	 * @param target: the mob that is being shot at
	 * @param gameTime: the time that the game has been running
	 */
	public static void shootTurret(EngineState engineState, int turret,
				       int target)
	{
		Vector2f turretPosition =
			engineState.unsafeGetComponentAt(PHitBox.class, turret)
				.getPolygon()
				.pureGetAPointInPolygon(0);

		Vector2f targetPosition =
			engineState.unsafeGetComponentAt(PHitBox.class, target)
				.getCenter();

		Vector2f tmp = turretPosition.pureSubtract(targetPosition);
		tmp.negate();
		Vector2f unitVecturretPosTotargetDelta = tmp.pureNormalize();

		// create projectile
		int e = engineState.spawnEntitySet(
			new CannonShell(turretPosition));
		engineState.unsafeGetComponentAt(PhysicsPCollisionBody.class, e)
			.setPositionPoint(
				engineState
					.unsafeGetComponentAt(
						WorldAttributes.class, turret)
					.getCenteredBottomQuarter());

		float shellSpeed =
			engineState.unsafeGetComponentAt(Movement.class, e)
				.getSpeed();

		engineState.unsafeGetComponentAt(Movement.class, e)
			.setVelocity(unitVecturretPosTotargetDelta.pureMul(
				shellSpeed));

		// decrease ammo
		engineState.unsafeGetComponentAt(Ammo.class, turret)
			.decreaseAmmo(1, GameConfig.TURRET_STARTING_AMMO);

		// destroy turret if out of ammo
		if (engineState.unsafeGetComponentAt(Ammo.class, turret)
			    .hasAmmo(1))
			return;
		else {
			System.out.println("A turret ran out of ammo");
			removeTurret(engineState, turret);
		}
	}
}
