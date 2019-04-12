package Game;
/**
 * Attack cycler handlers events.
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import Game.GameEvents.PlayGameEvent;
import Game.GameEvents.SetSpeedToZeroEvent;

public interface EntityAttackSetHandler {

	/**
	 * starting event
	 * @param g: play game
	 * @param focus: focused entity
	 */
	public PlayGameEvent startingHandler(PlayGame g, int focus);

	/**
	 * priming event
	 * @param g: play game
	 * @param focus: focused entity
	 */
	public default PlayGameEvent primerHandler(PlayGame g, int focus)
	{
		return new SetSpeedToZeroEvent(g, focus);
	}
	/**
	 * atacking event
	 * @param g: play game
	 * @param focus: focused entity
	 */
	public PlayGameEvent attackHandler(PlayGame g, int focus);

	/**
	 * recoil event
	 * @param g: play game
	 * @param focus: focused entity
	 */
	public default PlayGameEvent recoilHandler(PlayGame g, int focus)
	{
		return new SetSpeedToZeroEvent(g, focus);
	}

	/**
	 * end event
	 * @param g: play game
	 * @param focus: focused entity
	 */
	public PlayGameEvent endAttackHandler(PlayGame g, int focus);
}
