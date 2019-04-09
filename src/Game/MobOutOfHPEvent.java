package Game;

import java.util.concurrent.ThreadLocalRandom;

import Components.*;
import Resources.GameConfig;
import Resources.GameResources;

import poj.EngineState;
import poj.Logger.Logger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Optional;


public class MobOutOfHPEvent extends FocusedPlayGameEvent
{

	/**
	 * Constructor
	 * @param g: non owning pointer to the game state
	 * @param e: zombie entity that is out of HP
	 */

	public MobOutOfHPEvent(PlayGame g)
	{
		super(g);
	}

	public void f()
	{
		EngineState engineState = gameState.getEngineState();

		// for some reason sometimes zombies that do not have the
		// MovementDirection Component and this crashes everything. This
		// is needed to prevent the crashing
		if (!engineState.hasComponent(MovementDirection.class, focus1)) {
			Logger.logMessage(
				"Error in ZombieOutOfHPEvent -- trying to delete an entity that was already deleted. This entity has the following components:");
			engineState.printAllComponentsAt(focus1);
			return;
		}

		MovementDirection mv = engineState.unsafeGetComponentAt(
			MovementDirection.class, focus1);


		final Optional<PHitBox> mobBodyOptional =
			engineState.getComponentAt(PHitBox.class, focus1);

		if (!mobBodyOptional.isPresent())
			return;

		final PHitBox mobBody = mobBodyOptional.get();

		// play death sound
		engineState.unsafeGetComponentAt(SoundEffectAssets.class, focus1)
			.playSoundEffectAt(
				ThreadLocalRandom.current().nextInt(0, 4) + 3);

		Optional<AnimationWindowAssets> animWindowAssetsOpt =
			engineState.getComponentAt(AnimationWindowAssets.class,
						   focus1);

		if (!animWindowAssetsOpt.isPresent())
			return;

		AnimationWindowAssets animWindowAssets =
			animWindowAssetsOpt.get();

		// play death sound
		engineState.unsafeGetComponentAt(SoundEffectAssets.class, focus1)
			.playSoundEffectAt(
				ThreadLocalRandom.current().nextInt(0, 4) + 3);

		// deletes everything but the  render, animation, and
		// worldattributes components so we can show the death animation
		// for a bit
		engineState.deleteAllComponentsAtExcept(focus1, Render.class,
							HasAnimation.class,
							WorldAttributes.class);


		if (engineState.hasComponent(MovementDirection.class, focus1)) {
			Logger.logMessage(
				"Error in ZombieOutOfHPEvent -- trying to delete an entity that was already deleted. This entity has the following components:");
			engineState.printAllComponentsAt(focus1);
			return;
		}


		engineState.addComponentAt(
			DespawnTimer.class,
			new DespawnTimer(GameConfig.MOB_DESPAWN_TIMER), focus1);


		if (!engineState.hasComponent(HasAnimation.class, focus1)) {
			Logger.logMessage(
				"Error in ZombieOutOfHPEvent -- trying to delete an entity that was already deleted. This entity has the following components:");
			engineState.printAllComponentsAt(focus1);
			return;
		}
		engineState.unsafeGetComponentAt(HasAnimation.class, focus1)
			.setAnimation(animWindowAssets.getAnimation(
				mv.getDirection(), 30));

		gameState.killCount.increase();
		if (gameState.killCount.get() >= gameState.mobsSpawned)
			gameState.lastWaveDefeatedAt = gameState.getPlayTime();
		int dropRoll = ThreadLocalRandom.current().nextInt(0, 99) + 1;
		if (dropRoll >= (100 - GameConfig.MOB_DROP_RATE)) {
			int dropType =
				ThreadLocalRandom.current().nextInt(0, 4);
			if (dropType == 0)
				gameState.cashSpawner(false,
						      mobBody.getCenter().x,
						      mobBody.getCenter().y);
			else if (dropType == 1)
				gameState.powerUpSpawner(false,
							 mobBody.getCenter().x,
							 mobBody.getCenter().y);
			else if (dropType == 2)
				gameState.ammoPackSpawner(
					false, mobBody.getCenter().x,
					mobBody.getCenter().y);
			else
				gameState.healthPackSpawner(
					false, mobBody.getCenter().x,
					mobBody.getCenter().y);
		}
	}
}
