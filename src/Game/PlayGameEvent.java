package Game;
/**
 * PlayGameEvent. Sum type of the game events.
 * @author Jared Pon. March 14, 2019
 * @version 1.0
 */

public abstract class PlayGameEvent
{
	protected PlayGame gameState;

	public PlayGameEvent(PlayGame g)
	{
		gameState = g;
	}

	/**
	 * Mutation to the game state
	 */
	public abstract void f();

	public PlayGame getPlayGame()
	{
		return gameState;
	}
}
