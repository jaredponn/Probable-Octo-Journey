package Game.GameEvents;
/**
 * TrapTouchingEntityEvent.
 * @author Jared Pon
 * @version 03/09/19 v1.0
 */

import poj.EngineState;
import Components.*;

import java.util.Optional;

public class TrapTouchingEntityEvent extends BiFocusedPlayGameEvent
{

	float speedReduce = 0f;

	/**
	 * Constructor
	 */

	public TrapTouchingEntityEvent()
	{
	}

	/**
	 * set speed reduce
	 * @param n: speed reduce -- should be between 0-1
	 */
	public void setSpeedReduce(float n)
	{
		speedReduce = n;
	}

	/**
	 * event
	 */
	public void f()
	{
		EngineState engineState = super.getPlayGame().getEngineState();


		Optional<Movement> mvtOpt =
			engineState.getComponentAt(Movement.class, focus1);

		if (!mvtOpt.isPresent())
			return;

		Movement mvt = mvtOpt.get();

		mvt.setSpeed(mvt.getSpeed() * speedReduce);

		// deleting the trap
		engineState.deleteAllComponentsAt(focus2);
	}
}
