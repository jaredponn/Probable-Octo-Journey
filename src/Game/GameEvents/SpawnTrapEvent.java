package Game.GameEvents;

import poj.EngineState;
import poj.linear.*;

import Game.PlayGame;
import EntitySets.*;

public class SpawnTrapEvent extends PlayGameEvent
{
	private Vector2f position;

	public SpawnTrapEvent(PlayGame g, Vector2f position)
	{
		super(g);
		this.position = position;
	}

	public void f()
	{
		EngineState engineState = super.getPlayGame().getEngineState();
		engineState.spawnEntitySet(new TrapSet(position));
	}
}
