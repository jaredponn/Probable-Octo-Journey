package Game;

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
		engineState.deleteComponentAt(Bullet.class, bullet);
		engineState.deleteComponentAt(
			CannonShell.class,
			bullet); // TODO: separate deleteShell method?
		engineState.deleteComponentAt(Render.class, bullet);
		engineState.deleteComponentAt(WorldAttributes.class, bullet);
		engineState.deleteComponentAt(Movement.class, bullet);
		// engineState.deleteComponentAt(Lifespan.class, bullet);
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
	public static void removeTurret(EngineState engineState, int t)
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
	 * @param g: godly game state
	 * @param bullet: the bullet that you are checking for collisions
	 */
	public static void bulletHitHandler(PlayGame g, int bullet)
	{
		EngineState mainState = g.getEngineState();
		EngineState mapState = g.getMap().getLayerEngineState(1);
		GJK gjk = g.getGJK();

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
					.hurt(mainState
						      .getComponentAt(
							      Damage.class,
							      bullet)
						      .getDamage());
				removeBullet(mainState, bullet);


				if (mainState.getComponentAt(HitPoints.class, i)
					    .getHP()
				    <= 0)
					g.pushEventToEventHandler(
						new ZombieOutOfHPEvent(g, i));

				return; // If it does hit something, it should
					// just delete the bullet (as seen here)
					// and just exit and not check if is
					// colliding with other things.
			}
		}

		// check for bullet collision with wall
		for (PhysicsPCollisionBody wall :
		     mapState.getRawComponentArrayListPackedData(
			     PhysicsPCollisionBody.class)) {
			if (Systems.arePCollisionBodiesColliding(
				    gjk, bulletBody, wall)) {
				removeBullet(mainState, bullet);
				return;
			}
		}
	}

	/**
	 * Handler for mobs touching the player
	 * @param engineState: the main game state
	 * @param gjk: GJK needed to handle collisions
	 * @param a: entity set to start the attack cycle if touching
	 * @param b: entity set to check against
	 */
	public static void
	startAttackCycleOfSetAIfPhysicsCollisionBodiesAreCollidingWithSetB(
		EngineState engineState, GJK gjk, Class<? extends Component> a,
		Class<? extends Component> b)
	{
		for (int i = engineState.getInitialSetIndex(a);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(a, i)) {

			final PCollisionBody abody = engineState.getComponentAt(
				PhysicsPCollisionBody.class, i);

			for (int j = engineState.getInitialSetIndex(b);
			     engineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(b, j)) {

				final PCollisionBody bbody =
					engineState.getComponentAt(
						PhysicsPCollisionBody.class, j);
				if (Systems.arePCollisionBodiesColliding(
					    gjk, bbody, abody)) {
					engineState
						.getComponentAt(
							AttackCycle.class, i)
						.startAttackCycle();
					break;
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
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   mob);

		final PhysicsPCollisionBody turretBody =
			engineState.getComponentAt(PhysicsPCollisionBody.class,
						   turret);

		if (Systems.arePCollisionBodiesColliding(gjk, mobBody,
							 turretBody)) {
			engineState.getComponentAt(AttackCycle.class, mob)
				.startAttackCycle();
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
		HitPoints turretHP =
			engineState.getComponentAt(HitPoints.class, turret);
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
	public static void turretTargeting(EngineState engineState, int turret)
	{
		Vector2f turretPosition =
			engineState.getComponentAt(PHitBox.class, turret)
				.getCenter();
		int currentTarget = 0;

		// find the first mob
		int i = engineState.getInitialSetIndex(MobSet.class);
		if (poj.EngineState.isValidEntity(i)) {
			// set the mob as curretTarget
			currentTarget = i;

			// find the centre of the mob's hit box
			Vector2f mob1Position =
				engineState.getComponentAt(PHitBox.class, i)
					.getCenter();

			// find the vector from turret to target
			Vector2f tmp =
				turretPosition.pureSubtract(mob1Position);
			tmp.negate();
			Vector2f unitVecturretPosTomob1Delta =
				tmp;

			// find next mob and compare range
			for (int j = engineState.getNextSetIndex(MobSet.class,
								 currentTarget);
			     poj.EngineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(MobSet.class, j)) {

				// find centre of next mob's hit box
				Vector2f mob2Position =
					engineState
						.getComponentAt(PHitBox.class,
								j)
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
				       < 20)
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
			engineState.getComponentAt(PHitBox.class, turret)
				.getPolygon()
				.pureGetAPointInPolygon(0);

		Vector2f targetPosition =
			engineState.getComponentAt(PHitBox.class, target)
				.getCenter();

		Vector2f tmp = turretPosition.pureSubtract(targetPosition);
		tmp.negate();
		Vector2f unitVecturretPosTotargetDelta = tmp.pureNormalize();

		int e = engineState.spawnEntitySet(
			new CannonShell(turretPosition));
		engineState.getComponentAt(PhysicsPCollisionBody.class, e)
			.setPositionPoint(
				engineState
					.getComponentAt(WorldAttributes.class,
							turret)
					.getCenteredBottomQuarter());
		float shellSpeed = engineState.getComponentAt(Movement.class, e)
					   .getSpeed();

		engineState.getComponentAt(Movement.class, e)
			.setVelocity(unitVecturretPosTotargetDelta.pureMul(
				shellSpeed));
	}
}
