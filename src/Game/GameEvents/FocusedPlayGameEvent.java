package Game.GameEvents;
/**
 * FocusedPlayGameEvent. focused play game event
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Game.PlayGame;

public abstract class FocusedPlayGameEvent extends PlayGameEvent
{

	protected int focus1;

	/**
	 * Constructor
	 * @param g: non owning pointer to the game state
	 * @param e: entity to be focused on
	 */
	public FocusedPlayGameEvent()
	{
		super();
	}

	/**
	 * constructor
	 * @param g: playgame
	 */
	public FocusedPlayGameEvent(PlayGame g)
	{
		super(g);
	}

	/**
	 * constructor
	 * @param g: playgame
	 * @param e: focus1
	 */
	public FocusedPlayGameEvent(PlayGame g, int e)
	{
		super(g);
		this.focus1 = e;
	}


	/**
	 * sets the focus 1
	 * @param f: focus1
	 */
	public void setFocus1(int f)
	{
		focus1 = f;
	}
}
