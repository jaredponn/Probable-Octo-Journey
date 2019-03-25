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

			mopt.get().setSpeed(0.001f);
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
				GameConfig.MOB_MELEE_ATTACK_BODY);
			Systems.updatePCollisionBodyPositionFromWorldAttr(
				pmob, engineState.unsafeGetComponentAt(
					      WorldAttributes.class, focus));

			// debug rendering
			Systems.pCollisionBodyDebugRenderer(
				pmob, playGame.debugBuffer, playGame.cam,
				Color.orange);

			// testing for hits against  the player
			for (int i = engineState.getInitialSetIndex(
				     PlayerSet.class);
			     engineState.isValidEntity(i);
			     i = engineState.getNextSetIndex(PlayerSet.class,
							     i)) {

				PHitBox pplayer =
					engineState.unsafeGetComponentAt(
						PHitBox.class, i);

				if (Systems.arePCollisionBodiesColliding(
					    gjk, pplayer, pmob)) {
					CombatFunctions.handlePlayerDamage(
						engineState, i,
						GameConfig.MOB_ATTACK_DAMAGE);
					return; // shouldn't do damage to
						// multiple things
				}
			}

			// testing for hits against towers
			for (int i = engineState.getInitialSetIndex(
				     TurretSet.class);
			     engineState.isValidEntity(i);
			     i = engineState.getNextSetIndex(TurretSet.class,
							     i)) {
				PHitBox pturret =
					engineState.unsafeGetComponentAt(
						PHitBox.class, i);

				if (Systems.arePCollisionBodiesColliding(
					    gjk, pturret, pmob)) {
					CombatFunctions.handleMobDamageTurret(
						engineState, i);
					return;
				}
			}
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
