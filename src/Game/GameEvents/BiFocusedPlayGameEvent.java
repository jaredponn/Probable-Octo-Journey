package Game.GameEvents;

import Game.PlayGame;

public abstract class BiFocusedPlayGameEvent extends FocusedPlayGameEvent
{
	protected int focus2;

	public BiFocusedPlayGameEvent()
	{
		this(null, -1, -1);
	}

	public BiFocusedPlayGameEvent(PlayGame g)
	{
		this(g, -1, -1);
	}

	public BiFocusedPlayGameEvent(PlayGame g, int f1, int f2)
	{
		super(g, f1);
		focus2 = f2;
	}

	public void setFocus2(int f)
	{
		focus2 = f;
	}
}
