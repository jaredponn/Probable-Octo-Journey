package Game;
/**
 * Attack cycler handlers. Game handlers for the attack cycle
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import java.awt.Color;
import java.util.Optional;

import Components.*;
import EntitySets.*;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.Component.*;
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
			AttackCycle a = engineState.unsafeGetComponentAt(
				AttackCycle.class, i);

			if (a.isAttacking()) {
				switch (a.getAttackState()) {
				case 0: // priming
					break;

				case 1: // attack
					AttackCycleHandlers.playerAttackHandler(
						playGame);
					break;
				case 2: // recoil
					break;
				case 3: // end attack cycle
					a.endAttackCycle();
					a.resetCycle();
					break;
				}
			}
		}

		// mobs attacking
		for (int i = engineState.getInitialSetIndex(MobSet.class);
		     EngineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(MobSet.class, i)) {
			AttackCycle a = engineState.unsafeGetComponentAt(
				AttackCycle.class, i);

			if (a.isAttacking()) {
				switch (a.getAttackState()) {
				case 0:
					AttackCycleHandlers
						.meleeAttackPrimerHandler(
							engineState, i,
							MobSet.class, 2);
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
			}
		}

		// turret attack
		for (int i = engineState.getInitialSetIndex(TurretSet.class);
		     EngineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(TurretSet.class, i)) {
			AttackCycle a = engineState.unsafeGetComponentAt(
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
	public static Vector2f queryEntityToMouseDirection()
	{
		return new Vector2f(0, 0);
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

		int player = engineState.getInitialSetIndex(PlayerSet.class);
		Vector2f playerPosition =
			engineState.unsafeGetComponentAt(PHitBox.class, player)
				.pureGetCenter();

		switch (playerCurWPState) {
		case Gun:
			Vector2f mousePosition = ip.getMousePosition();
			mousePosition.matrixMultiply(invCam);

			Vector2f tmp =
				playerPosition.pureSubtract(mousePosition);
			tmp.negate();
			Vector2f unitVecPlayerPosToMouseDelta =
				tmp.pureNormalize();

			engineState
				.unsafeGetComponentAt(HasAnimation.class,
						      player)
				.setAnimation(AnimationGetter.queryPlayerSprite(
					CardinalDirections
						.getClosestDirectionFromDirectionVector(
							tmp),
					0));

			// generation of the bullet
			if (playGame.playerAmmo > 0) {
				int e = engineState.spawnEntitySet(
					new Bullet(playerPosition));
				engineState
					.unsafeGetComponentAt(
						PhysicsPCollisionBody.class, e)
					.setPositionPoint(
						engineState
							.unsafeGetComponentAt(
								PHitBox.class,
								player)
							.getCenter());
				float bulletSpeed =
					engineState
						.unsafeGetComponentAt(
							Movement.class, e)
						.getSpeed();

				engineState
					.unsafeGetComponentAt(Movement.class, e)
					.setVelocity(
						unitVecPlayerPosToMouseDelta
							.pureMul(bulletSpeed));

				playGame.playerAmmo -= 1;
			}
			break;
		case Melee:
			System.out.println("attacked with melee weapon");
			break;
		}
	}

	/**
	 * Generalized melee attack handler
	 * @param engineState  engineState
	 * @param focus  focused entity
	 * @param c  Entity set to run the melee attack handler
	 * @param animationFlag  flag for the animation
	 */
	public static void
	meleeAttackPrimerHandler(EngineState engineState, int focus,
				 Class<? extends Component> c,
				 int animationFlag)
	{

		final Optional<MovementDirection> nOpt =
			engineState.getComponentAt(MovementDirection.class,
						   focus);

		final Optional<Movement> mOpt =
			engineState.getComponentAt(Movement.class, focus);

		final Optional<HasAnimation> animationOpt =
			engineState.getComponentAt(HasAnimation.class, focus);

		if (!nOpt.isPresent())
			return;

		if (!animationOpt.isPresent())
			return;

		if (!mOpt.isPresent())
			return;

		final MovementDirection n = nOpt.get();
		HasAnimation animation = animationOpt.get();

		animation.setAnimation(AnimationGetter.queryEnemySprite(
			n.getDirection(), animationFlag));

		mOpt.get().setVelocity(new Vector2f(0, 0));
	}

	public static void mobMeleeAttackHandler(PlayGame playGame, int focus)
	{
		EngineState engineState = playGame.getEngineState();
		GJK gjk = playGame.gjk;

		// in the future make the hit boxes attack forward and just
		// witch upon them
		final MovementDirection n = engineState.unsafeGetComponentAt(
			MovementDirection.class, focus);

		// Spawn the hitbox in the correct location and check against
		// all enemies
		PCollisionBody pmob =
			new PCollisionBody(GameConfig.MOB_MELEE_ATTACK_BODY);
		Systems.updatePCollisionBodyPositionFromWorldAttr(
			pmob, engineState.unsafeGetComponentAt(
				      WorldAttributes.class, focus));

		// debug rendering
		Systems.pCollisionBodyDebugRenderer(pmob, playGame.debugBuffer,
						    playGame.cam, Color.orange);

		// testing for hits against  the player
		for (int i = engineState.getInitialSetIndex(PlayerSet.class);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(PlayerSet.class, i)) {

			PHitBox pplayer = engineState.unsafeGetComponentAt(
				PHitBox.class, i);

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
			PHitBox pturret = engineState.unsafeGetComponentAt(
				PHitBox.class, i);

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
		CombatFunctions.turretTargeting(engineState, turret);
	}
}
