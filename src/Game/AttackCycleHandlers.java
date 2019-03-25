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

		AttackCycleHandlers.runAttackCyclerHandler(playGame,
							   PlayerSet.class);
		AttackCycleHandlers.runAttackCyclerHandler(playGame,
							   MobSet.class);

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
	 * Generalized way to query an entity's direcion towards the mouse
	 * @param playGame godly game state
	 * @param focus  focused entity
	 */
	public static Vector2f
	queryEntitySetWithPHitBoxToMouseDirection(PlayGame playGame, int focus)
	{
		EngineState engineState = playGame.getEngineState();
		InputPoller ip = playGame.getInputPoller();
		Camera invCam = playGame.getInvCam();

		Vector2f focusPos =
			engineState.unsafeGetComponentAt(PHitBox.class, focus)
				.pureGetCenter();

		Vector2f mousePosition = ip.getMousePosition();
		mousePosition.matrixMultiply(invCam);

		Vector2f tmp = focusPos.pureSubtract(mousePosition);
		tmp.negate();

		Vector2f unitVecToMouse = tmp.pureNormalize();

		return unitVecToMouse;
	}


	/**
	 * Generalized melee attack handler -- freeze aand point in the
	 * direction
	 * @param engineState  engineState
	 * @param focus  focused entity
	 * @param c  Entity set to run the melee attack handler
	 * @param animationFlag  flag for the animation
	 * @param d  direction for animation
	 */
	public static void
	meleeAttackPrimerHandler(EngineState engineState, int focus,
				 Class<? extends Component> c,
				 int animationFlag, CardinalDirections d)
	{
		final Optional<Movement> mOpt =
			engineState.getComponentAt(Movement.class, focus);

		final Optional<HasAnimation> animationOpt =
			engineState.getComponentAt(HasAnimation.class, focus);

		if (!animationOpt.isPresent())
			return;

		if (!mOpt.isPresent())
			return;

		HasAnimation animation = animationOpt.get();

		animation.setAnimation(
			AnimationGetter.queryEnemySprite(d, animationFlag));

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


	public static EntityAttackSetHandler
	queryEntityAttackSetHandler(Class<? extends Component> c)
	{
		if (c == PlayerSet.class)
			return new PlayerAttackCycleHandler();
		else if (c == MobSet.class)
			return new MobSetAttackCycleHandler();
		else {
			return new PlayerAttackCycleHandler();
		}
	}

	public static void runAttackCyclerHandler(PlayGame playGame,
						  Class<? extends Component> c)
	{
		EngineState engineState = playGame.getEngineState();
		double gameElapsedTime = playGame.getPlayTime();

		EntityAttackSetHandler atkHandler =
			queryEntityAttackSetHandler(c);

		for (int i = engineState.getInitialSetIndex(c);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(c, i)) {

			Optional<AttackCycle> aOpt = engineState.getComponentAt(
				AttackCycle.class, i);

			if (!aOpt.isPresent())
				continue;

			AttackCycle a = aOpt.get();

			if (a.isAttacking()) {
				switch (a.getAttackState()) {
				case 0: // priming
					playGame.pushEventToEventHandler(
						atkHandler.primerHandler(
							playGame, i));
					break;

				case 1: // attack
					playGame.pushEventToEventHandler(
						atkHandler.attackHandler(
							playGame, i));
					break;
				case 2: // recoil
					playGame.pushEventToEventHandler(
						atkHandler.recoilHandler(
							playGame, i));
					break;

				case 3: // end attack cycle
					a.endAttackCycle();
					a.resetCycle();
					break;
				}
			}
		}
	}
}
