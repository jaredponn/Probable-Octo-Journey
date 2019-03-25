package Game;

import EntitySets.MobSet;

import poj.EngineState;
import poj.linear.*;

import java.util.Optional;

import Components.*;

public class MobSetAttackCycleHandler implements EntityAttackSetHandler
{

	class MobAttackPrimerEvent extends Game.PlayGameEvent
	{

		protected int focus;
		public MobAttackPrimerEvent(PlayGame g, int focus)
		{
			super(g);
			this.focus = focus;
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

	public PlayGameEvent primerHandler(PlayGame g, int focus)
	{
		return new MobAttackPrimerEvent(g, focus);
	}
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return new MobAttackPrimerEvent(g, focus);
	}
	public PlayGameEvent recoilHandler(PlayGame g, int focus)
	{

		return new MobAttackPrimerEvent(g, focus);
	}
}
