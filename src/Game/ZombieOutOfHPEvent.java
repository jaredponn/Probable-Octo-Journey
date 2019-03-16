package Game;

import poj.EngineState;
import Components.*;
import EntitySets.*;
import Resources.GameConfig;


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

		// deletes everything but the  render, animation, and
		// worldattributes components so we can show the death animation
		// for a bit
		engineState.deleteAllComponentsAtExcept(focus, Render.class,
							HasAnimation.class,
							WorldAttributes.class);

		engineState.addComponentAt(
			DespawnTimer.class,
			new DespawnTimer(GameConfig.MOB_DESPAWN_TIMER), focus);


		engineState.getComponentAt(HasAnimation.class, focus)
			.setAnimation(AnimationGetter.queryEnemySprite(
				mv.getDirection(), 3));
	}
}
