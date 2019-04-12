package Game.GameEvents;

import Game.PlayGame;

/**
 * PlayGameEvent. Sum type of the game events.
 * @author Jared Pon. March 14, 2019
 * @version 1.0
 */

public abstract class PlayGameEvent
{
	protected PlayGame gameState;

	/**
	 * constructor
	 */
	public PlayGameEvent()
	{
	}

	/**
	 * constructor
	 * @param g : play game
	 */
	public PlayGameEvent(PlayGame g)
	{
		gameState = g;
	}

	/**
	 * Mutation to the game state
	 */
	public abstract void f();


	/**
	 * gets playgame
	 * @return playgame
	 */
	public PlayGame getPlayGame()
	{
		return gameState;
	}

	/**
	 * sets play game
	 * @param g: play game
	 */
	public void setPlayGame(PlayGame g)
	{
		this.gameState = g;
	}
}
