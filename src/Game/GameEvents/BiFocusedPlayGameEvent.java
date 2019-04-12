package Game.GameEvents;
/**
 * BiFocusedPlayGameEvent. bifocused play game event
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Game.PlayGame;

public abstract class BiFocusedPlayGameEvent extends FocusedPlayGameEvent
{
	protected int focus2;


	/**
	 * constructor
	 */
	public BiFocusedPlayGameEvent()
	{
		this(null, -1, -1);
	}

	/**
	 * constructor
	 * @param g: playgame
	 */
	public BiFocusedPlayGameEvent(PlayGame g)
	{
		this(g, -1, -1);
	}

	/**
	 * constructor
	 * @param g: playgame
	 * @param f1: focus1
	 * @param f2: focus2
	 */
	public BiFocusedPlayGameEvent(PlayGame g, int f1, int f2)
	{
		super(g, f1);
		focus2 = f2;
	}

	/**
	 * sets the focus 2
	 * @param f: focus2
	 */
	public void setFocus2(int f)
	{
		focus2 = f;
	}
}
