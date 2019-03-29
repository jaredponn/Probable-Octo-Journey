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
		try {
		GameResources.playerDeathSound.play();
		}
		catch (NullPointerException e) {
			System.out.println("ERROR: Problem playing player death sound");
			e.printStackTrace();
		}
		getPlayGame().quit();
	}
}
