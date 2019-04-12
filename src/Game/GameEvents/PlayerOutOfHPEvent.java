package Game.GameEvents;
/**
 * BiFocusedPlayGameEvent. bifocused play game event
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import java.util.concurrent.ThreadLocalRandom;

import Components.SoundEffectAssets;
import EntitySets.PlayerSet;
import Game.PlayGame;
import Resources.GameResources;

public class PlayerOutOfHPEvent extends FocusedPlayGameEvent
{

	/**
	 * constructor
	 * @param g: playgame
	 */
	public PlayerOutOfHPEvent(PlayGame g)
	{
		super(g);
	}

	/**
	 * constructor
	 * @param g: playgame
	 * @param focus: focused entity
	 */
	public PlayerOutOfHPEvent(PlayGame g, int focus)
	{
		super(g, focus);
	}

	/**
	 * event
	 */
	public void f()
	{
		// play death sound
		//
		// TODO: this function is called multiple times!!!! fixx
		int dead = ThreadLocalRandom.current().nextInt(0, 4);
		System.out.println("dead at = " + dead);
		gameState.getEngineState()
			.unsafeGetComponentAt(
				SoundEffectAssets.class,
				gameState.getEngineState().getInitialSetIndex(
					PlayerSet.class))
			.playSoundEffectAt(dead + 6);

		GameResources.loseSound.play();
		gameState.clearWorld();
		getPlayGame().quit();
	}
}
