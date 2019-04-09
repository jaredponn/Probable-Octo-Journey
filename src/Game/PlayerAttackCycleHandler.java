package Game;


import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import Components.AnimationWindowAssets;
import Components.CardinalDirections;
import Components.HasAnimation;
import Components.Movement;
import Components.PCollisionBody;
import Components.PHitBox;
import Components.PhysicsPCollisionBody;
import Components.SoundEffectAssets;
import Components.WorldAttributes;
import EntitySets.Bullet;
import EntitySets.MobSet;
import EntitySets.PlayerSet;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.linear.Vector2f;

public class PlayerAttackCycleHandler implements EntityAttackSetHandler
{

	class PlayerStartingAttackEvent extends FocusedPlayGameEvent
	{
		PlayerStartingAttackEvent(PlayGame g, int e)
		{
			super(g, e);
		}

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
				Movement.class, focus);

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
					engineState, focus, 10, d);

				break;
			}
			mopt.get().setSpeed(0f);
		}
	}


	public class PlayerAttackEvent extends FocusedPlayGameEvent
	{
		PlayerAttackEvent(PlayGame g, int e)
		{
			super(g, e);
		}
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

				if (super.getPlayGame().playerAmmo.hasAmmo(1)) {
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
				PCollisionBody patk =
					new PCollisionBody(queryMeleeAttackBody(
						closestDirToMouse));

				Systems.updatePCollisionBodyPositionFromWorldAttr(
					patk,
					engineState.unsafeGetComponentAt(
						WorldAttributes.class, focus));

				// play melee sound
				engineState
					.unsafeGetComponentAt(
						SoundEffectAssets.class, focus)
					.playSoundEffectAt(
						ThreadLocalRandom.current()
							.nextInt(0, 3)
						+ 10);


				// debug rendering
				Systems.pCollisionBodyDebugRenderer(
					patk, super.getPlayGame().debugBuffer,
					super.getPlayGame().cam, Color.orange);

				EngineTransforms.doDamageInSetifPCollisionBodyAndSetPHitBoxAreColliding(
					engineState, patk, MobSet.class,
					(int)(GameConfig
						      .PLAYER_STARTING_MELEE_DAMAGE
					      + super.getPlayGame()
							.playerDamageBonus.get()));
				break;
			}
		}
	}


	private PCollisionBody queryMeleeAttackBody(CardinalDirections d)
	{

		switch (d) {
		case N:
			return GameConfig.PLAYER_MELEE_N_ATK_BODY;
		case NE:
			return GameConfig.PLAYER_MELEE_NE_ATK_BODY;
		case NW:
			return GameConfig.PLAYER_MELEE_NW_ATK_BODY;
		case S:
			return GameConfig.PLAYER_MELEE_S_ATK_BODY;
		case SE:
			return GameConfig.PLAYER_MELEE_SE_ATK_BODY;
		case SW:
			return GameConfig.PLAYER_MELEE_SW_ATK_BODY;
		case W:
			return GameConfig.PLAYER_MELEE_W_ATK_BODY;
		case E:
			return GameConfig.PLAYER_MELEE_E_ATK_BODY;
		default:
			return GameConfig.PLAYER_MELEE_E_ATK_BODY;
		}
	}


	public PlayGameEvent startingHandler(PlayGame g, int focus)
	{
		return new PlayerStartingAttackEvent(g, focus);
	}
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return new PlayerAttackEvent(g, focus);
	}
	public PlayGameEvent endAttackHandler(PlayGame g, int focus)
	{
		return null;
	}
}
