package Game;

import java.util.concurrent.ThreadLocalRandom;

import Components.SoundAssets;
import EntitySets.PlayerSet;

public class PlayerOutOfHPEvent extends FocusedPlayGameEvent
{

	public PlayerOutOfHPEvent(PlayGame g)
	{
		super(g);
	}

	public PlayerOutOfHPEvent(PlayGame g, int focus)
	{
		super(g, focus);
	}

	public void f()
	{
		// play death sound
		//
		// TODO: this function is called multiple times!!!! fixx
		int dead = ThreadLocalRandom.current().nextInt(0, 4);
		System.out.println("dead at = " + dead);
		gameState.getEngineState()
			.unsafeGetComponentAt(
				SoundAssets.class,
				gameState.getEngineState().getInitialSetIndex(
					PlayerSet.class))
			.playSoundAt(dead + 6);
		getPlayGame().quit();
	}
}
