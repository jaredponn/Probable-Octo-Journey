package Game;

import poj.EngineState;
import Components.*;
import EntitySets.*;


public class ZombieOutOfHPEvent extends PlayGameEvent
{

	private int focus;


	/**
	 * Constructor
	 * @param g: non owning pointer to the game state
	 * @param e: zombie entity that is out of HP
	 */

	public ZombieOutOfHPEvent(PlayGame g, int e)
	{
		super(g);
		this.focus = e;
	}

	public void f()
	{
		EngineState engineState = gameState.getEngineState();

		MovementDirection mv = engineState.getComponentAt(
			MovementDirection.class, focus);

		// deletes everything but the  render and animation components
		engineState.deleteComponentAt(MobSet.class, focus);
		engineState.deleteComponentAt(WorldAttributes.class, focus);
		engineState.deleteComponentAt(Movement.class, focus);
		engineState.deleteComponentAt(MovementDirection.class, focus);
		engineState.deleteComponentAt(FacingDirection.class, focus);
		engineState.deleteComponentAt(PhysicsPCollisionBody.class,
					      focus);
		engineState.deleteComponentAt(PHitBox.class, focus);
		engineState.deleteComponentAt(HitPoints.class, focus);
		engineState.deleteComponentAt(AttackCycle.class, focus);

		engineState.addComponentAt(focus, new DespawnTimer());
	}
}
