package Game;

import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import Components.AggroRange;
import Components.CardinalDirections;
import Components.HitPoints;
import Components.Movement;
import Components.MovementDirection;
import Components.PCollisionBody;
import Components.PhysicsPCollisionBody;
import Components.SoundEffectAssets;
import Components.WorldAttributes;
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

		public MobStartingEvent(PlayGame g, int focus)
		{
			super(g, focus);
		}

		public void f()
		{
			EngineState engineState =
				getPlayGame().getEngineState();

			Optional<MovementDirection> dopt =
				engineState.getComponentAt(
					MovementDirection.class, focus1);

			Optional<Movement> mopt = engineState.getComponentAt(
				Movement.class, focus1);

			if (!dopt.isPresent()) {
				return;
			}

			if (!mopt.isPresent()) {
				return;
			}

			MovementDirection d = dopt.get();

			AttackCycleHandlers.meleeAttackPrimerHandler(
				engineState, focus1, 10, d.getDirection());
		}
	}

	class MobAttackEvent extends FocusedPlayGameEvent
	{
		public MobAttackEvent(PlayGame g, int f)
		{
			super(g, f);
		}

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
				queryMeleeAttackBody(n.getDirection()));
			Systems.updatePCollisionBodyPositionFromWorldAttr(
				pmob, engineState.unsafeGetComponentAt(
					      WorldAttributes.class, focus1));

			// debug rendering
			Systems.pCollisionBodyDebugRenderer(
				pmob, super.getPlayGame().debugBuffer,
				playGame.cam, Color.orange);

			boolean playerHitByMob =
				EngineTransforms
					.doDamageInSetifPCollisionBodyAndSetPHitBoxAreColliding(
						engineState, pmob,
						PlayerSet.class,
						GameConfig.MOB_ATTACK_DAMAGE);

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

			EngineTransforms
				.doDamageInSetifPCollisionBodyAndSetPHitBoxAreColliding(
					engineState, pmob, TurretSet.class,
					GameConfig.MOB_ATTACK_DAMAGE);
		}
	}


	class MobAttackEnd extends FocusedPlayGameEvent
	{

		public MobAttackEnd(PlayGame g, int focus)
		{
			super(g, focus);
		}

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

			if (!agopt.isPresent())
				return;

			if (!pplayeropt.isPresent())
				return;

			if (!dmobopt.isPresent())
				return;

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

	private PCollisionBody queryMeleeAttackBody(CardinalDirections d)
	{

		switch (d) {
		case N:
			return GameConfig.MOB_MELEE_N_ATK_BODY;
		case NE:
			return GameConfig.MOB_MELEE_NE_ATK_BODY;
		case NW:
			return GameConfig.MOB_MELEE_NW_ATK_BODY;
		case S:
			return GameConfig.MOB_MELEE_S_ATK_BODY;
		case SE:
			return GameConfig.MOB_MELEE_SE_ATK_BODY;
		case SW:
			return GameConfig.MOB_MELEE_SW_ATK_BODY;
		case W:
			return GameConfig.MOB_MELEE_W_ATK_BODY;
		case E:
			return GameConfig.MOB_MELEE_E_ATK_BODY;
		default:
			return GameConfig.MOB_MELEE_E_ATK_BODY;
		}
	}

	public PlayGameEvent startingHandler(PlayGame g, int focus)
	{
		return new MobStartingEvent(g, focus);
	}
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return new MobAttackEvent(g, focus);
	}
	public PlayGameEvent endAttackHandler(PlayGame g, int focus)
	{
		return new MobAttackEnd(g, focus);
	}
}
