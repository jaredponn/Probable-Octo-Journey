package Game.GameEvents;
/**
 * TurretOutOfHPEvent
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Game.PlayGame;

public class TurretOutOfHPEvent extends FocusedPlayGameEvent
{

	/**
	 * constructor
	 * @param g: play game
	 */
	public TurretOutOfHPEvent(PlayGame g)
	{
		super(g);
	}

	/**
	 * event
	 */
	public void f()
	{
		getPlayGame().getEngineState().deleteAllComponentsAt(focus1);
	}
}
