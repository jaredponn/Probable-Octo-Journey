package Game.GameEvents;

/**
 * set speed to 0 event.
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import Components.*;
import EntitySets.*;
import Game.PlayGame;
import Resources.GameConfig;
import Resources.GameResources;

import java.awt.Color;
import java.util.Optional;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.Component.*;
import poj.linear.Vector2f;
import poj.Animation;
import poj.Collisions.*;

public class SetSpeedToZeroEvent extends FocusedPlayGameEvent
{

	/**
	 * constructor
	 * @param g: playgame
	 * @param e: entity
	 */
	public SetSpeedToZeroEvent(PlayGame g, int e)
	{
		super(g, e);
	}

	/**
	 * event
	 */
	public void f()
	{
		EngineState engineState = super.getPlayGame().getEngineState();

		Optional<Movement> mopt =
			engineState.getComponentAt(Movement.class, focus1);

		if (!mopt.isPresent())
			return;

		mopt.get().setVelocity(0f, 0f);
	}
}
