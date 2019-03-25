package Game;

import EntitySets.MobSet;

import poj.EngineState;
import poj.linear.*;

import java.util.Optional;

import Components.*;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;
import Components.*;
import EntitySets.*;
import Resources.*;

import poj.Collisions.GJK;
import poj.Logger.Logger;


public class MobSetAttackCycleHandler implements EntityAttackSetHandler
{

	class MobAttackPrimerEvent extends FocusedPlayGameEvent
	{

		public MobAttackPrimerEvent(PlayGame g, int focus)
		{
			super(g, focus);
		}

		public void f()
		{
			EngineState engineState =
				getPlayGame().getEngineState();

			Optional<MovementDirection> dopt =
				engineState.getComponentAt(
					MovementDirection.class, focus);

			Optional<Movement> mopt = engineState.getComponentAt(
				Movement.class, focus);

			if (!dopt.isPresent())
				return;

			if (!mopt.isPresent())
				return;

			MovementDirection d = dopt.get();

			AttackCycleHandlers.meleeAttackPrimerHandler(
				engineState, focus, MobSet.class, 10,
				d.getDirection());

			mopt.get().setSpeed(0);
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
					MovementDirection.class, focus);

			// Spawn the hitbox in the correct location and check
			// against all enemies
			PCollisionBody pmob = new PCollisionBody(
				queryMeleeAttackBody(n.getDirection()));
			Systems.updatePCollisionBodyPositionFromWorldAttr(
				pmob, engineState.unsafeGetComponentAt(
					      WorldAttributes.class, focus));

			// debug rendering
			Systems.pCollisionBodyDebugRenderer(
				pmob, playGame.debugBuffer, playGame.cam,
				Color.orange);

			EngineTransforms
				.doDamageInSetifPCollisionBodyAndSetPHitBoxAreColliding(
					engineState, pmob, PlayerSet.class,
					GameConfig.MOB_ATTACK_DAMAGE);

			EngineTransforms
				.doDamageInSetifPCollisionBodyAndSetPHitBoxAreColliding(
					engineState, pmob, TurretSet.class,
					GameConfig.MOB_ATTACK_DAMAGE);
		}
	}

	class MobAttackRecoil extends FocusedPlayGameEvent
	{

		public MobAttackRecoil(PlayGame g, int focus)
		{
			super(g, focus);
		}

		public void f()
		{
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


	public PlayGameEvent primerHandler(PlayGame g, int focus)
	{
		return new MobAttackPrimerEvent(g, focus);
	}
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return new MobAttackEvent(g, focus);
	}
	public PlayGameEvent recoilHandler(PlayGame g, int focus)
	{
		return new MobAttackRecoil(g, focus);
	}
}
