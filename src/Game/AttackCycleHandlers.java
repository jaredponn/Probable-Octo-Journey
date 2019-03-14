package Game;
/**
 * Attack cycler handlers. Game handlers for the attack cycle
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import java.awt.Color;

import Components.*;
import EntitySets.*;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.linear.Vector2f;
import poj.Animation;
import poj.Collisions.*;

public class AttackCycleHandlers
{

	public static void
	runAttackCycleHandlersAndFreezeMovement(PlayGame playGame)
	{
		EngineState engineState = playGame.getEngineState();

		double gameElapsedTime = playGame.getPlayTime();

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
						playGame);
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
						.mobMeleeAttackHandler(playGame,
								       i);
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
					AttackCycleHandlers.turretAttackHandler(
						engineState, i,
						gameElapsedTime);
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
	public static void playerAttackHandler(PlayGame playGame)
	{

		EngineState engineState = playGame.getEngineState();
		WeaponState playerCurWPState = playGame.curWeaponState;
		InputPoller ip = playGame.getInputPoller();
		Camera invCam = playGame.invCam;
		double gameElapsedTime = playGame.getPlayTime();

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
						       int focus)
	{
		final MovementDirection n = engineState.getComponentAt(
			MovementDirection.class, focus);
		engineState.getComponentAt(HasAnimation.class, focus)
			.setAnimation(AnimationGetter.queryEnemySprite(
				n.getDirection(), 2));
	}
	public static void mobMeleeAttackHandler(PlayGame playGame, int focus)
	{
		EngineState engineState = playGame.getEngineState();
		GJK gjk = playGame.gjk;

		// in the future make the hit boxes attack forward and just
		// witch upon them
		final MovementDirection n = engineState.getComponentAt(
			MovementDirection.class, focus);

		// Spawn the hitbox in the correct location and check against
		// all enemies
		PCollisionBody pmob =
			new PCollisionBody(GameConfig.MOB_MELEE_ATTACK_BODY);
		Systems.updatePCollisionBodyPositionFromWorldAttr(
			pmob, engineState.getComponentAt(WorldAttributes.class,
							 focus));

		// debug rendering
		Systems.pCollisionBodyDebugRenderer(pmob, playGame.debugBuffer,
						    playGame.cam, Color.orange);

		// testing for hits against  the player
		for (int i = engineState.getInitialSetIndex(PlayerSet.class);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(PlayerSet.class, i)) {

			PHitBox pplayer =
				engineState.getComponentAt(PHitBox.class, i);

			if (Systems.arePCollisionBodiesColliding(gjk, pplayer,
								 pmob)) {
				CombatFunctions.handlePlayerDamage(
					engineState, i,
					GameConfig.MOB_ATTACK_DAMAGE);
				return; // shouldn't do damage to multiple
					// things
			}
		}

		// testing for hits against towers
		for (int i = engineState.getInitialSetIndex(TurretSet.class);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(TurretSet.class, i)) {
			PHitBox pturret =
				engineState.getComponentAt(PHitBox.class, i);

			if (Systems.arePCollisionBodiesColliding(gjk, pturret,
								 pmob)) {
				CombatFunctions.handleMobDamageTurret(
					engineState, i);
				return;
			}
		}
	}

	public static void turretAttackHandler(EngineState engineState,
					       int turret, double gameTime)
	{
		CombatFunctions.turretTargeting(engineState, turret, gameTime);
	}
}
