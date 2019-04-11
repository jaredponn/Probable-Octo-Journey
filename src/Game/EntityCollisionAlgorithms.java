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
import EntitySets.*;

import java.util.ArrayList;

import Game.GameEvents.*;

import poj.Component.*;
import poj.Time.Timer;
import poj.Collisions.*;
import poj.EngineState;


public class EntityCollisionAlgorithms
{

	private static ArrayList<TaggedCollisionShape> COLLISION_QUERY_MEMO =
		new ArrayList<TaggedCollisionShape>(1000000);
	public static <T extends PCollisionBody, U extends PCollisionBody> void
	ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
		PlayGame g, Class<? extends Component> a,
		Class<? extends Component> b, Class<T> collisionBodyTypeA,
		Class<U> collisionBodyTypeB, BiFocusedPlayGameEvent event)
	{
		// Timer.START_BENCH();
		EngineState engineState = g.getEngineState();

		Node<TaggedCollisionShape> DBVT_NODE =
			new Node<TaggedCollisionShape>();

		for (int i = engineState.getInitialSetIndex(a);
		     engineState.isValidEntity(i);
		     i = engineState.getNextSetIndex(a, i)) {

			Optional<? extends Component> apopt =
				engineState.getComponentAt(collisionBodyTypeA,
							   i);

			if (!apopt.isPresent())
				continue;

			PCollisionBody ap = (PCollisionBody)apopt.get();

			DBVT_NODE.insert(
				new TaggedCollisionShape(ap.getPolygon(), i));
		}

		for (int j = engineState.getInitialSetIndex(b);
		     engineState.isValidEntity(j);
		     j = engineState.getNextSetIndex(b, j)) {


			Optional<? extends Component> bpopt =
				engineState.getComponentAt(collisionBodyTypeB,
							   j);

			if (!bpopt.isPresent())
				continue;

			PCollisionBody bp = (PCollisionBody)bpopt.get();

			TaggedCollisionShape bpt =
				new TaggedCollisionShape(bp.getPolygon(), j);

			COLLISION_QUERY_MEMO.clear();
			DBVT_NODE.queryCollisions(bpt, COLLISION_QUERY_MEMO);

			for (TaggedCollisionShape apt : COLLISION_QUERY_MEMO) {
				if (((PCollisionBody)
					     engineState.unsafeGetComponentAt(
						     collisionBodyTypeA,
						     apt.getEntity()))
					    .isCollidingWith(bp)
				    && apt.getEntity() != bpt.getEntity()) {

					event.setFocus1(apt.getEntity());
					event.setFocus2(bpt.getEntity());
					event.f();
				}
			}
		}
		// Timer.END_BENCH(); Timer.LOG_BENCH_DELTA();
	}


	public static <T extends PCollisionBody>
		boolean ifCollisionBodyIsCollidingWithSetARunGameEventOnFirst(
			PlayGame g, PCollisionBody pbody,
			Class<? extends Component> a,
			Class<T> collisionBodyType, FocusedPlayGameEvent event)
	{
		return ifCollisionBodyIsCollidingWithSetARunGameEventOnFirstBruteForce(
			g, pbody, a, collisionBodyType, event);
	}

	public static <T extends PCollisionBody, U extends PCollisionBody> void
	ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEventBruteForce(
		PlayGame g, Class<? extends Component> a,
		Class<? extends Component> b, Class<T> collisionBodyTypeA,
		Class<U> collisionBodyTypeB, BiFocusedPlayGameEvent event)
	{
		// Timer.START_BENCH();
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
		// Timer.END_BENCH();
		// Timer.LOG_BENCH_DELTA();
	}

	public static <T extends PCollisionBody> boolean
	ifCollisionBodyIsCollidingWithSetARunGameEventOnFirstBruteForce(
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
				return true;
			}
		}
		return false;
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

	private static DamageFocusedEntityEvent DAMAGED_FOCUSED_ENTITY_MEMO =
		new DamageFocusedEntityEvent();
	public static boolean
	damageSetAIfCollisionBodiesAreTouching(PlayGame g, PCollisionBody pbody,
					       Class<? extends Component> a,
					       int dmg)
	{
		DAMAGED_FOCUSED_ENTITY_MEMO.setPlayGame(g);
		DAMAGED_FOCUSED_ENTITY_MEMO.setDamage(dmg);

		return ifCollisionBodyIsCollidingWithSetARunGameEventOnFirst(
			g, pbody, a, PHitBox.class,
			DAMAGED_FOCUSED_ENTITY_MEMO);
	}

	public static <T extends CollectibleSet, U
			       extends SingleIntComponent> void
	pickUpEventForPlayer(PlayGame g, int amount, Class<T> set, Class<U> t)
	{
		SingleIntComponentModifierEvent<U> event =
			new SingleIntComponentModifierEvent<U>();

		event.setPlayGame(g);
		event.setAmount(amount);
		event.setType(t);

		ifSetAAndBPCollisionBodyAreCollidingAndAreUniqueRunGameEvent(
			g, PlayerSet.class, set, PhysicsPCollisionBody.class,
			PhysicsPCollisionBody.class, event);
	}
}
