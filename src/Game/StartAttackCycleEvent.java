package Game;

import Components.*;
import java.util.Optional;
import poj.EngineState;

public class StartAttackCycleEvent extends FocusedPlayGameEvent
{

	public StartAttackCycleEvent()
	{
	}

	public StartAttackCycleEvent(PlayGame g)
	{
		this(g, -1);
	}

	public StartAttackCycleEvent(PlayGame g, int e)
	{
		super(g);
		super.focus1 = e;
	}

	public void f()
	{
		if (focus1 == -1)
			return;

		EngineState engineState = super.getPlayGame().getEngineState();

		Optional<AttackCycle> atkcycleOpt = engineState.getComponentAt(
			AttackCycle.class, super.focus1);

		if (!atkcycleOpt.isPresent())
			return;

		atkcycleOpt.get().startAttackCycle();
	}
}
