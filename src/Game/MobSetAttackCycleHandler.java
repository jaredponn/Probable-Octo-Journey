package Game;

/**
 * MobSetAttackCycleHandler
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import Components.AggroRange;
import Components.CardinalDirections;
import Components.Damage;
import Components.HitPoints;
import Components.Movement;
import Components.MovementDirection;
import Components.PCollisionBody;
import Components.PhysicsPCollisionBody;
import Components.SoundEffectAssets;
import Components.WorldAttributes;
import Components.*;
import EntitySets.MobSet;
import EntitySets.PlayerSet;
import EntitySets.TurretSet;
import Game.GameEvents.FocusedPlayGameEvent;
import Game.GameEvents.PlayGameEvent;
import Resources.GameConfig;

import poj.EngineState;
import poj.Collisions.GJK;


public class MobSetAttackCycleHandler implements EntityAttackSetHandler
{

	class MobStartingEvent extends FocusedPlayGameEvent
	{


		/**
		 * constructor
		 * @param g: playgame
		 * @param focus: focused entity
		 */
		public MobStartingEvent(PlayGame g, int focus)
		{
			super(g, focus);
		}


		/**
		 * event
		 */
		public void f()
		{
			EngineState engineState =
				getPlayGame().getEngineState();

			Optional<MovementDirection> dopt =
				engineState.getComponentAt(
					MovementDirection.class, focus1);

			Optional<Movement> mopt = engineState.getComponentAt(
				Movement.class, focus1);

			Optional<PathfindSeek> popt =
				engineState.getComponentAt(PathfindSeek.class,
							   focus1);

			if (!dopt.isPresent()) {
				return;
			}

			if (!mopt.isPresent()) {
				return;
			}

			if (!popt.isPresent()) {
				return;
			}
			popt.get().stopPathfinding();

			MovementDirection d = dopt.get();

			AttackCycleHandlers.meleeAttackPrimerHandler(
				engineState, focus1, 10, d.getDirection());
		}
	}

	class MobAttackEvent extends FocusedPlayGameEvent
	{
		/**
		 * constructor
		 * @param g: playgame
		 * @param focus: focused entity
		 */
		public MobAttackEvent(PlayGame g, int f)
		{
			super(g, f);
		}


		/**
		 * event
		 */
		public void f()
		{
			PlayGame playGame = super.getPlayGame();
			EngineState engineState = playGame.getEngineState();
			GJK gjk = playGame.gjk;

			// in the future make the hit boxes attack forward and
			// just witch upon them
			final MovementDirection n =
				engineState.unsafeGetComponentAt(
					MovementDirection.class, focus1);

			// Spawn the hitbox in the correct location and check
			// against all enemies
			PCollisionBody pmob = new PCollisionBody(
				engineState
					.unsafeGetComponentAt(
						OctoMeleeAttackBuffer.class,
						focus1)
					.getPCollisionBody(n.getDirection()));

			Systems.updatePCollisionBodyPositionFromWorldAttr(
				pmob, engineState.unsafeGetComponentAt(
					      WorldAttributes.class, focus1));

			// debug rendering
			Systems.pCollisionBodyDebugRenderer(
				pmob, super.getPlayGame().debugBuffer,
				playGame.cam, Color.orange);

			boolean playerHitByMob =
				EntityCollisionAlgorithms
					.damageSetAIfCollisionBodiesAreTouching(
						super.getPlayGame(), pmob,
						PlayerSet.class,
						engineState
							.unsafeGetComponentAt(
								Damage.class,
								focus1)
							.getDamage());

			// get the player health
			Optional<HitPoints> hpOpt = engineState.getComponentAt(
				HitPoints.class, engineState.getInitialSetIndex(
							 PlayerSet.class));
			if (hpOpt.isPresent()) {
				int hp = hpOpt.get().getHP();
				System.out.println("player hp = " + hp);
				// will play player hurt sound only if the
				// player health is above 0
				if (playerHitByMob && (hp > 0)) {
					engineState
						.unsafeGetComponentAt(
							SoundEffectAssets.class,
							engineState.getInitialSetIndex(
								PlayerSet
									.class))
						.playSoundEffectAt(
							ThreadLocalRandom
								.current()
								.nextInt(0, 4)
							+ 2);
				}
			}

			EntityCollisionAlgorithms
				.damageSetAIfCollisionBodiesAreTouching(
					super.getPlayGame(), pmob,
					TurretSet.class,
					GameConfig.MOB_ATTACK_DAMAGE);
		}
	}


	class MobAttackEnd extends FocusedPlayGameEvent
	{

		/**
		 * constructor
		 * @param g: playgame
		 * @param focus: focused entity
		 */
		public MobAttackEnd(PlayGame g, int focus)
		{
			super(g, focus);
		}

		/**
		 * event
		 */
		public void f()
		{
			EngineState engineState =
				super.getPlayGame().getEngineState();

			Optional<PhysicsPCollisionBody> pplayeropt =
				engineState.getComponentAt(
					PhysicsPCollisionBody.class,
					engineState.getInitialSetIndex(
						PlayerSet.class));

			Optional<MovementDirection> dmobopt =
				engineState.getComponentAt(
					MovementDirection.class, focus1);

			Optional<AggroRange> agopt = engineState.getComponentAt(
				AggroRange.class, focus1);

			Optional<PathfindSeek> popt =
				engineState.getComponentAt(PathfindSeek.class,
							   focus1);


			if (!popt.isPresent()) {
				return;
			}
			if (!agopt.isPresent())
				return;

			if (!pplayeropt.isPresent())
				return;

			if (!dmobopt.isPresent())
				return;

			popt.get().startPathfinding();

			CardinalDirections d =
				CardinalDirections.getClosestDirectionFromDirectionVector(
					pplayeropt.get()
						.pureGetCenter()
						.pureSubtract(
							agopt.get()
								.pureGetCenter()));
			dmobopt.get().setDirection(d);
		}
	}


	/**
	 * starting handler
	 * @param g: playgame
	 * @param focus: focused entity
	 */
	public PlayGameEvent startingHandler(PlayGame g, int focus)
	{
		return new MobStartingEvent(g, focus);
	}
	/**
	 * attack handler
	 * @param g: playgame
	 * @param focus: focused entity
	 */
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return new MobAttackEvent(g, focus);
	}

	/**
	 * end handler
	 * @param g: playgame
	 * @param focus: focused entity
	 */
	public PlayGameEvent endAttackHandler(PlayGame g, int focus)
	{
		return new MobAttackEnd(g, focus);
	}
}
