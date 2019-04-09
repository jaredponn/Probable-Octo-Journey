package Game;

/**
 * EntityCollisionAlgorithms. Generalized and specilized collision algrotihms
 * for entities against entities.
 *
 * Date: Aptil 5, 2019
 * 2019
 * @author Jared Pon
 * @version 1.0
 */
import java.util.Optional;

import Components.*;
import Game.GameEvents.*;
import poj.Component.*;
import poj.EngineState;


public class EntityCollisionAlgorithms
{
	public static <T extends PCollisionBody, U extends PCollisionBody> void
	ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
		PlayGame g, Class<? extends Component> a,
		Class<? extends Component> b, Class<T> collisionBodyTypeA,
		Class<U> collisionBodyTypeB, BiFocusedPlayGameEvent event)
	{
		EngineState engineState = g.getEngineState();

		for (int i = engineState.getInitialSetIndex(a);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(a, i)) {

			Optional<? extends Component> apopt =
				engineState.getComponentAt(collisionBodyTypeA,
							   i);

			if (!apopt.isPresent())
				continue;

			PCollisionBody ap = (PCollisionBody)apopt.get();

			for (int j = engineState.getInitialSetIndex(b);
			     engineState.isValidEntity(j);
			     j = engineState.getNextSetIndex(b, j)) {

				Optional<? extends Component> bpopt =
					engineState.getComponentAt(
						collisionBodyTypeB, j);

				if (!bpopt.isPresent())
					continue;

				PCollisionBody bp = (PCollisionBody)bpopt.get();

				if (ap.isCollidingWith(bp) && i != j) {
					event.setFocus1(i);
					event.setFocus2(j);
					event.f();
				}
			}
		}
	}

	public static <T extends PCollisionBody> void
	ifCollisionBodyIsCollidingWithSetARunGameEventOnFirst(
		PlayGame g, PCollisionBody pbody, Class<? extends Component> a,
		Class<T> collisionBodyType, FocusedPlayGameEvent event)
	{
		EngineState engineState = g.getEngineState();

		for (int i = engineState.getInitialSetIndex(a);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(a, i)) {

			Optional<? extends Component> bpopt =
				engineState.getComponentAt(collisionBodyType,
							   i);
			if (!bpopt.isPresent())
				continue;

			PCollisionBody bp = (PCollisionBody)bpopt.get();

			if (bp.isCollidingWith(pbody)) {
				event.setFocus1(i);
				event.f();
				return;
			}
		}
	}


	public static <T extends PCollisionBody> void
	ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
		PlayGame g, Class<? extends Component> a,
		Class<? extends Component> b, Class<T> collisionBodyType,
		BiFocusedPlayGameEvent event)
	{

		ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
			g, a, b, collisionBodyType, collisionBodyType, event);
	}


	private static NudgeAOutOfBPCollisionBodyEvent<
		PhysicsPCollisionBody> NUDGE_A_OUT_OF_B_P_COLLISION_BODY_MEMO =
		new NudgeAOutOfBPCollisionBodyEvent<PhysicsPCollisionBody>(
			PhysicsPCollisionBody.class);
	public static void
	nudgeSetAAndBIfPCollisionBodiesAreTouching(PlayGame g,
						   Class<? extends Component> a,
						   Class<? extends Component> b)
	{
		NUDGE_A_OUT_OF_B_P_COLLISION_BODY_MEMO.setPlayGame(g);
		ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
			g, a, b, PhysicsPCollisionBody.class,
			NUDGE_A_OUT_OF_B_P_COLLISION_BODY_MEMO);
	}


	private static StartAttackCycleEvent START_ATTACK_CYCLE_EVENT_MEMO =
		new StartAttackCycleEvent();
	public static void
	startAttackCycleIfAggroRadiusCollidesPhysicsPCollisionBody(
		PlayGame g, Class<? extends Component> a,
		Class<? extends Component> b)
	{
		START_ATTACK_CYCLE_EVENT_MEMO.setPlayGame(g);

		ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
			g, a, b, AggroRange.class, PhysicsPCollisionBody.class,
			START_ATTACK_CYCLE_EVENT_MEMO);
	}
}
