package Game.GameEvents;

import poj.EngineState;
import Components.*;

import java.util.Optional;

public class TrapTouchingEntityEvent extends BiFocusedPlayGameEvent
{

	float speedReduce = 0f;

	public TrapTouchingEntityEvent()
	{
	}

	public void setSpeedReduce(float n)
	{
		speedReduce = n;
	}

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
