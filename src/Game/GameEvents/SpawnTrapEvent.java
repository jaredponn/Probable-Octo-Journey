package Game.GameEvents;

/**
 * SpawnTrapEvent.
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import poj.EngineState;
import poj.linear.*;

import Game.PlayGame;
import EntitySets.*;
import Resources.GameConfig;

public class SpawnTrapEvent extends PlayGameEvent
{
	private Vector2f position;

	/**
	 * construtor
	 * @param g :playgame
	 * @param position :position
	 */
	public SpawnTrapEvent(PlayGame g, Vector2f position)
	{
		super(g);
		this.position =
			position.pureAdd(GameConfig.TRAP_SPAWN_DISPLACEMENT);
	}

	/**
	 * event
	 */
	public void f()
	{
		EngineState engineState = super.getPlayGame().getEngineState();
		engineState.spawnEntitySet(new TrapSet(position));
	}
}
