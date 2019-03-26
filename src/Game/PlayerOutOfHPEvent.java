package Game;

import Resources.GameResources;

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
		GameResources.playerDeathSound.play();
		getPlayGame().quit();
	}
}
