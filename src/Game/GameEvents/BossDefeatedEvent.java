package Game.GameEvents;

/**
 * Boss defeated event
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Game.PlayGame;

public class BossDefeatedEvent extends MobOutOfHPEvent
{
	/**
	 * constructor
	 * @param g: playgame
	 */
	public BossDefeatedEvent(PlayGame g)
	{
		super(g);
	}

	/**
	 * event function
	 */
	public void f()
	{
		super.f();
	}
}
