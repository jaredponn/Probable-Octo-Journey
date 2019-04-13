package Game.GameEvents;

/**
 * PauseGame -- because the TA wanted it
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Game.PlayGame;

public class PauseGame extends PlayGameEvent
{

	/**
	 * constructor
	 * @param g: play game
	 */
	public PauseGame(PlayGame g)
	{
		super(g);
	}

	/**
	 * event
	 */
	public void f()
	{
	}
}
