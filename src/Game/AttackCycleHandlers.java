package Game;
/**
 * Attack cycler handlers. Game handlers for the attack cycle
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import java.awt.Color;
import java.util.Optional;

import Components.AttackCycle;
import Components.CardinalDirections;
import Components.HasAnimation;
import Components.Movement;
import Components.MovementDirection;
import Components.PCollisionBody;
import Components.PHitBox;
import Components.PhysicsPCollisionBody;
import Components.WorldAttributes;
import EntitySets.Bullet;
import EntitySets.MobSet;
import EntitySets.PlayerSet;
import EntitySets.TurretSet;
import Resources.GameConfig;
import Resources.GameResources;

import poj.EngineState;
import poj.Collisions.GJK;
import poj.GameWindow.InputPoller;
import poj.Component.*;
import poj.linear.Vector2f;


public class AttackCycleHandlers
{
	public static void runAttackCyclers(PlayGame playGame)
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

		animation.setAnimation(AnimationGetter.queryAnimationSprite(
			c, d, animationFlag));

		mOpt.get().setSpeed(0);
		mOpt.get().setVelocity(new Vector2f(0, 0));
	}


	public static void turretAttackHandler(EngineState engineState,
					       int turret, double gameTime)
	{
		CombatFunctions.turretTargeting(engineState, turret);
	}

	// reduces the allocations during the main game loop
	private static EntityAttackSetHandler PLAYER_ATTACK_CYCLE_HANDLER_MEMO =
		new PlayerAttackCycleHandler();
	private static EntityAttackSetHandler MOBSET_ATTACK_CYCLE_HANDLER_MEMO =
		new MobSetAttackCycleHandler();

	public static EntityAttackSetHandler
	queryEntityAttackSetHandler(Class<? extends Component> c)
	{
		if (c == PlayerSet.class)
			return PLAYER_ATTACK_CYCLE_HANDLER_MEMO;
		else if (c == MobSet.class)
			return MOBSET_ATTACK_CYCLE_HANDLER_MEMO;
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
