package Game;


/**
 * PlayerAttackEvent
 * Date: March 12, 2019
 * @author Jared Pon
 * @version 1.0
 */


import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import Components.AnimationWindowAssets;
import Components.CardinalDirections;
import Components.Damage;
import Components.HasAnimation;
import Components.Movement;
import Components.OctoMeleeAttackBuffer;
import Components.PCollisionBody;
import Components.PHitBox;
import Components.PhysicsPCollisionBody;
import Components.SoundEffectAssets;
import Components.WorldAttributes;
import EntitySets.Bullet;
import EntitySets.MobSet;
import EntitySets.PlayerSet;
import EntitySets.BossSet;
import Game.GameEvents.*;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.linear.Vector2f;

public class PlayerAttackCycleHandler implements EntityAttackSetHandler
{

	class PlayerStartingAttackEvent extends FocusedPlayGameEvent
	{

		/**
		 *  constructor
		 *  @param g : play game
		 *  @param e : entity
		 */
		PlayerStartingAttackEvent(PlayGame g, int e)
		{
			super(g, e);
		}

		/**
		 *  event
		 */
		public void f()
		{
			EngineState engineState =
				super.getPlayGame().getEngineState();
			WeaponState playerCurWPState =
				super.getPlayGame().curWeaponState;
			InputPoller ip = super.getPlayGame().getInputPoller();
			Camera invCam = super.getPlayGame().invCam;

			int player =
				engineState.getInitialSetIndex(PlayerSet.class);
			Vector2f playerPosition =
				engineState
					.unsafeGetComponentAt(PHitBox.class,
							      player)
					.pureGetCenter();

			Optional<Movement> mopt = engineState.getComponentAt(
				Movement.class, focus1);

			if (!mopt.isPresent())
				return;

			switch (playerCurWPState) {

			case Gun:
				break;
			case Melee:
				CardinalDirections d =
					CardinalDirections.getClosestDirectionFromDirectionVector(
						AttackCycleHandlers
							.queryEntitySetWithPHitBoxToMouseDirection(
								super.getPlayGame(),
								player));

				AttackCycleHandlers.meleeAttackPrimerHandler(
					engineState, focus1, 10, d);

				break;
			}
			mopt.get().setSpeed(0f);
		}
	}


	public class PlayerAttackEvent extends FocusedPlayGameEvent
	{

		/**
		 *  constructor
		 *  @param g : play game
		 *  @param e : entity
		 */
		PlayerAttackEvent(PlayGame g, int e)
		{
			super(g, e);
		}

		/**
		 *  event
		 */
		public void f()
		{

			EngineState engineState =
				super.getPlayGame().getEngineState();
			WeaponState playerCurWPState =
				super.getPlayGame().curWeaponState;
			InputPoller ip = super.getPlayGame().getInputPoller();
			Camera invCam = super.getPlayGame().invCam;

			int player =
				engineState.getInitialSetIndex(PlayerSet.class);
			Vector2f playerPosition =
				engineState
					.unsafeGetComponentAt(PHitBox.class,
							      player)
					.pureGetCenter();

			Vector2f unitVecToMouse =
				AttackCycleHandlers
					.queryEntitySetWithPHitBoxToMouseDirection(
						super.getPlayGame(), player);
			CardinalDirections closestDirToMouse =
				CardinalDirections
					.getClosestDirectionFromDirectionVector(
						unitVecToMouse);

			switch (playerCurWPState) {
			case Gun:


				// set animation to face in correct
				// direction
				engineState
					.unsafeGetComponentAt(
						HasAnimation.class, player)
					.setAnimation(
						engineState
							.unsafeGetComponentAt(
								AnimationWindowAssets
									.class,
								player)
							.getAnimation(

								closestDirToMouse,
								0));

				if (super.getPlayGame().playerAmmo.hasAmmo()) {
					// play gun shooting sound
					engineState
						.unsafeGetComponentAt(
							SoundEffectAssets.class,
							player)
						.playSoundEffectAt(0);

					// generate bullet
					int e = engineState.spawnEntitySet(
						new Bullet(playerPosition));
					engineState
						.unsafeGetComponentAt(
							PhysicsPCollisionBody
								.class,
							e)
						.setPositionPoint(
							engineState
								.unsafeGetComponentAt(
									PHitBox.class
									,
									player)
								.getCenter());
					float bulletSpeed =
						engineState
							.unsafeGetComponentAt(
								Movement.class,
								e)
							.getSpeed();

					engineState
						.unsafeGetComponentAt(
							Movement.class, e)
						.setVelocity(
							unitVecToMouse.pureMul(
								bulletSpeed));

					engineState
						.unsafeGetComponentAt(
							Damage.class, e)
						.setDamage(
							engineState
								.unsafeGetComponentAt(
									Damage.class
									,
									player)
								.getDamage());


					super.getPlayGame()
						.playerAmmo.decreaseAmmo(1);
					;
				} else {
					// play empty clip sound
					engineState
						.unsafeGetComponentAt(
							SoundEffectAssets.class,
							player)
						.playSoundEffectAt(1);
				}

				break;
			case Melee:
				// Spawn the hitbox in the correct location and
				// check against all enemies
				PCollisionBody patk = new PCollisionBody(
					engineState
						.unsafeGetComponentAt(
							OctoMeleeAttackBuffer
								.class,
							player)
						.getPCollisionBody(
							closestDirToMouse));

				Systems.updatePCollisionBodyPositionFromWorldAttr(
					patk,
					engineState.unsafeGetComponentAt(
						WorldAttributes.class, focus1));

				// play melee sound
				engineState
					.unsafeGetComponentAt(
						SoundEffectAssets.class, focus1)
					.playSoundEffectAt(
						ThreadLocalRandom.current()
							.nextInt(0, 3)
						+ 10);


				// debug rendering
				Systems.pCollisionBodyDebugRenderer(
					patk, super.getPlayGame().debugBuffer,
					super.getPlayGame().cam, Color.orange);

				int playerDamage =
					engineState
						.unsafeGetComponentAt(
							Damage.class, player)
						.getDamage();

				EntityCollisionAlgorithms
					.ifCollisionBodyIsCollidingWithSetARunGameEventOnFirst(
						super.getPlayGame(), patk,
						MobSet.class, PHitBox.class,
						new DamageFocusedEntityEvent(
							super.getPlayGame(),
							playerDamage));

				EntityCollisionAlgorithms
					.ifCollisionBodyIsCollidingWithSetARunGameEventOnFirst(
						super.getPlayGame(), patk,
						BossSet.class, PHitBox.class,
						new DamageFocusedEntityEvent(
							super.getPlayGame(),
							playerDamage));


				break;
			}
		}
	}


	/**
	 *  starting handler
	 *  @param g : play game
	 *  @param e : focus
	 */
	public PlayGameEvent startingHandler(PlayGame g, int focus)
	{
		return new PlayerStartingAttackEvent(g, focus);
	}
	/**
	 *  attack handler
	 *  @param g : play game
	 *  @param e : focus
	 */
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return new PlayerAttackEvent(g, focus);
	}
	/**
	 *  end handler
	 *  @param g : play game
	 *  @param e : focus
	 */
	public PlayGameEvent endAttackHandler(PlayGame g, int focus)
	{
		return null;
	}
}
