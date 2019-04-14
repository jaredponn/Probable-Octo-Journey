package Game.GameEvents;

/**
 * Boss defeated event
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Game.PlayGame;
import Resources.GameResources;

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
		// stop the boss sound
		GameResources.bossSound.end();
		// play the win sound
		GameResources.winSound.play();
		// set the boolean in gameover to be true
		Game.GameOver.playerWin = true;
		super.f();

		super.getPlayGame().quit();
	}
}
