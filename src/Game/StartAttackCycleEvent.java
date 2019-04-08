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
		super.focus = e;
	}

	public void f()
	{
		if (focus == -1)
			return;

		EngineState engineState = super.getPlayGame().getEngineState();

		Optional<AttackCycle> atkcycleOpt = engineState.getComponentAt(
			AttackCycle.class, super.focus);

		if (!atkcycleOpt.isPresent())
			return;

		atkcycleOpt.get().startAttackCycle();
	}
}
