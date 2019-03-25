package Game;


import Components.*;
import EntitySets.*;
import Resources.GameConfig;
import java.awt.Color;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.Component.*;
import poj.linear.Vector2f;
import poj.Animation;
import poj.Collisions.*;

import java.util.Optional;

public class PlayerAttackCycleHandler implements EntityAttackSetHandler
{

	class PlayerAttackPrimerEvent extends FocusedPlayGameEvent
	{
		PlayerAttackPrimerEvent(PlayGame g, int e)
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
					engineState, focus, MobSet.class, 10,
					d);

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

			switch (playerCurWPState) {
			case Gun:

				Vector2f unitVecToMouse =
					AttackCycleHandlers
						.queryEntitySetWithPHitBoxToMouseDirection(
							super.getPlayGame(),
							player);

				engineState
					.unsafeGetComponentAt(
						HasAnimation.class, player)
					.setAnimation(AnimationGetter.queryPlayerSprite(
						CardinalDirections
							.getClosestDirectionFromDirectionVector(
								unitVecToMouse),
						0));

				// generation of the bullet
				if (super.getPlayGame().playerAmmo > 0) {
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

					super.getPlayGame().playerAmmo -= 1;
				}
				break;
			case Melee:
				System.out.println(
					"attacked with melee weapon");

				// Spawn the hitbox in the correct location and
				// check against all enemies
				PCollisionBody patk = new PCollisionBody(
					GameConfig.MOB_MELEE_ATTACK_BODY);
				Systems.updatePCollisionBodyPositionFromWorldAttr(
					patk,
					engineState.unsafeGetComponentAt(
						WorldAttributes.class, focus));


				// debug rendering
				Systems.pCollisionBodyDebugRenderer(
					patk, super.getPlayGame().debugBuffer,
					super.getPlayGame().cam, Color.orange);

				EngineTransforms.doDamageInSetifPCollisionBodyAndSetPHitBoxAreColliding(
					engineState, patk, MobSet.class,
					(int)(GameConfig
						      .PLAYER_STARTING_MELEE_DAMAGE
					      * super.getPlayGame()
							.playerDamageBonus));
				break;
			}
		}
	}


	public class PlayerRecoilEvent extends FocusedPlayGameEvent
	{
		PlayerRecoilEvent(PlayGame g, int e)
		{
			super(g, e);
		}
		public void f()
		{
		}
	}

	public PlayGameEvent primerHandler(PlayGame g, int focus)
	{
		return new PlayerAttackPrimerEvent(g, focus);
	}
	public PlayGameEvent attackHandler(PlayGame g, int focus)
	{
		return new PlayerAttackEvent(g, focus);
	}
	public PlayGameEvent recoilHandler(PlayGame g, int focus)
	{
		return new PlayerRecoilEvent(g, focus);
	}
}
