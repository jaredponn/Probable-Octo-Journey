package Game;

import EntitySets.PlayerSet;

import java.awt.Color;

import poj.Render.RenderObject;
import poj.Render.StringRenderObject;

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
		getPlayGame().quit();
	}
}
