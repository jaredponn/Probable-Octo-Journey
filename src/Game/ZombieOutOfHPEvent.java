package Game;

import poj.EngineState;

public class ZombieOutOfHPEvent implements PlayGameEvent
{
	public void f(PlayGame g, int... es)
	{
		EngineState engineState = g.getEngineState();

		for (int e : es) {
			// CombatFunctions.partialRemoveMob();
		}
	}
}
