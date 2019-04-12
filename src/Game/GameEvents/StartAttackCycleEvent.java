package Game.GameEvents;

/**
 * StartAttackCycleEvent.
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Components.*;
import Game.PlayGame;

import java.util.Optional;
import poj.EngineState;

public class StartAttackCycleEvent extends BiFocusedPlayGameEvent
{

	/**
	 * Constructor
	 */
	public StartAttackCycleEvent()
	{
	}

	/**
	 * Constructor
	 * @param g: playgame
	 */
	public StartAttackCycleEvent(PlayGame g)
	{
		this(g, -1);
	}

	/**
	 * Constructor
	 * @param g: playgame
	 * @param e: entity
	 */
	public StartAttackCycleEvent(PlayGame g, int e)
	{
		super(g);
		super.focus1 = e;
	}

	/**
	 * event
	 */
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
