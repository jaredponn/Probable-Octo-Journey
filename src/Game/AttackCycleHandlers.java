package Game;
/**
 * Attack cycler handlers. Game handlers for the attack cycle
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import Components.*;
import EntitySets.*;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.linear.Vector2f;

public class AttackCycleHandlers
{

	public static void runAttackCycleHandlersAndFreezeMovement(
		EngineState engineState, WeaponState playerCurWPState,
		InputPoller ip, Camera invCam, double gameElapsedTime)
	{

		// players attacking
		for (int i = engineState.getInitialSetIndex(PlayerSet.class);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(PlayerSet.class, i)) {
			AttackCycle a = engineState.getComponentAt(
				AttackCycle.class, i);

			if (a.isAttacking()) {
				switch (a.getAttackState()) {
				case 0:
					break;

				case 1:
					AttackCycleHandlers.playerAttackHandler(
						engineState, playerCurWPState,
						ip, invCam, gameElapsedTime);
					break;
				case 2:
					break;
				case 3:
					a.endAttackCycle();
					a.resetCycle();
					break;
				}

				// setting velocity to 0
				engineState.getComponentAt(Movement.class, i)
					.setVelocity(new Vector2f(0, 0));
			}
		}

		// mobs attacking
		for (int i = engineState.getInitialSetIndex(MobSet.class);
		     EngineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(MobSet.class, i)) {
			AttackCycle a = engineState.getComponentAt(
				AttackCycle.class, i);

			if (a.isAttacking()) {
				switch (a.getAttackState()) {
				case 0:
					AttackCycleHandlers
						.mobMeleeAttackPrimerHandler(
							engineState, i);
					break;

				case 1:
					AttackCycleHandlers
						.mobMeleeAttackHandler(
							engineState, i);
					break;
				case 2:
					break;
				case 3:
					a.endAttackCycle();
					a.resetCycle();
					break;
				}

				// setting velocity to 0
				engineState.getComponentAt(Movement.class, i)
					.setVelocity(new Vector2f(0, 0));
			}
		}
		
		// turret attack
		for (int i = engineState.getInitialSetIndex(TurretSet.class);
			     EngineState.isValidEntity(i);
			     i = engineState.getNextSetIndex(TurretSet.class, i)) {
				AttackCycle a = engineState.getComponentAt(
					AttackCycle.class, i);

				if (a.isAttacking()) {
					switch (a.getAttackState()) {
					case 0:
						break;
					case 1:
						AttackCycleHandlers
							.turretAttackHandler(engineState, i, gameElapsedTime);
						break;
					case 2:
						break;
					case 3:
						a.endAttackCycle();
						a.resetCycle();
						break;
					}
				}
			}
	}

	/**
	 * Player's attack handler.
	 * Variable names should be intuitive.
	 */
	public static void playerAttackHandler(EngineState engineState,
					       WeaponState playerCurWPState,
					       InputPoller ip, Camera invCam,
					       double gameElapsedTime)
	{

		int player = engineState.getInitialSetIndex(PlayerSet.class);
		Vector2f playerPosition =
			engineState
				.getComponentAt(PhysicsPCollisionBody.class,
						player)
				.getPolygon()
				.pureGetAPointInPolygon(0);

		switch (playerCurWPState) {
		case Gun:
			playerPosition.add(-GameConfig.PLAYER_WIDTH / 3,
					   -GameConfig.PLAYER_HEIGHT / 3);
			Vector2f mousePosition = ip.getMousePosition();
			mousePosition.matrixMultiply(invCam);

			Vector2f tmp =
				playerPosition.pureSubtract(mousePosition);
			tmp.negate();
			Vector2f unitVecPlayerPosToMouseDelta =
				tmp.pureNormalize();

			engineState.getComponentAt(HasAnimation.class, player)
				.setAnimation(AnimationGetter.queryPlayerSprite(
					CardinalDirections
						.getClosestDirectionFromDirectionVector(
							tmp),
					0));

			// generation of the bullet
			int e = engineState.spawnEntitySet(
				new Bullet(gameElapsedTime, playerPosition));
			engineState
				.getComponentAt(PhysicsPCollisionBody.class, e)
				.setPositionPoint(
					engineState
						.getComponentAt(
							WorldAttributes.class,
							player)
						.getCenteredBottomQuarter());
			float bulletSpeed =
				engineState.getComponentAt(Movement.class, e)
					.getSpeed();

			engineState.getComponentAt(Movement.class, e)
				.setVelocity(
					unitVecPlayerPosToMouseDelta.pureMul(
						bulletSpeed));
			break;
		case Melee:
			System.out.println("melee weapon was attacked");
			break;
		}
	}


	public static void mobMeleeAttackPrimerHandler(EngineState engineState,
						       int i)
	{
		final MovementDirection n =
			engineState.getComponentAt(MovementDirection.class, i);
		engineState.getComponentAt(HasAnimation.class, i)
			.setAnimation(AnimationGetter.queryEnemySprite(
				n.getDirection(), 2));
	}
	public static void mobMeleeAttackHandler(EngineState engineState, int i)
	{
		System.out.println("direction");
	}
	
	public static void turretAttackHandler(EngineState engineState , int turret , double gameTime) {
		CombatFunctions.turretTargeting(engineState, turret, gameTime);
	}
}
