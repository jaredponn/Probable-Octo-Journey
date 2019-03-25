package Game;

import Components.DespawnTimer;
import Components.HasAnimation;
import Components.MovementDirection;
import Components.Render;
import Components.WorldAttributes;
import Resources.GameConfig;
import Resources.GameResources;

import poj.EngineState;
import poj.Logger.Logger;


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

		// for some reason sometimes zombies that do not have the
		// MovementDirection Component and this crashes everything. This
		// is needed to prevent the crashing
		if (!engineState.hasComponent(MovementDirection.class, focus)) {
			Logger.logMessage(
				"Error in ZombieOutOfHPEvent -- trying to delete an entity that was already deleted. This entity has the following components:");
			engineState.printAllComponentsAt(focus);
			return;
		}

		MovementDirection mv = engineState.unsafeGetComponentAt(
			MovementDirection.class, focus);

		// deletes everything but the  render, animation, and
		// worldattributes components so we can show the death animation
		// for a bit
		engineState.deleteAllComponentsAtExcept(focus, Render.class,
							HasAnimation.class,
							WorldAttributes.class);


		if (engineState.hasComponent(MovementDirection.class, focus)) {
			Logger.logMessage(
				"Error in ZombieOutOfHPEvent -- trying to delete an entity that was already deleted. This entity has the following components:");
			engineState.printAllComponentsAt(focus);
			return;
		}

		// play death sound
		GameResources.zombieDeathSound.play();
		engineState.addComponentAt(
			DespawnTimer.class,
			new DespawnTimer(GameConfig.MOB_DESPAWN_TIMER), focus);


		if (!engineState.hasComponent(HasAnimation.class, focus)) {
			Logger.logMessage(
				"Error in ZombieOutOfHPEvent -- trying to delete an entity that was already deleted. This entity has the following components:");
			engineState.printAllComponentsAt(focus);
			return;
		}
		engineState.unsafeGetComponentAt(HasAnimation.class, focus)
			.setAnimation(AnimationGetter.queryEnemySprite(
				mv.getDirection(), 3));
	}
}
