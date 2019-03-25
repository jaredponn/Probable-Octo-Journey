package Game;


import Components.*;
import EntitySets.*;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.Component.*;
import poj.linear.Vector2f;
import poj.Animation;
import poj.Collisions.*;

public class PlayerAttackCycleHandler implements EntityAttackSetHandler
{

	/**
	 * Player's attack handler.
	 */
	public void primerHandler(PlayGame playGame)
	{
		EngineState engineState = playGame.getEngineState();
		WeaponState playerCurWPState = playGame.curWeaponState;
		InputPoller ip = playGame.getInputPoller();
		Camera invCam = playGame.invCam;

		int player = engineState.getInitialSetIndex(PlayerSet.class);
		Vector2f playerPosition =
			engineState.unsafeGetComponentAt(PHitBox.class, player)
				.pureGetCenter();

		switch (playerCurWPState) {
		case Gun:
			break;
		case Melee:
			System.out.println("attacked with melee weapon");
			break;
		}
	}

	/**
	 * Player's attack handler.
	 */
	public void attackHandler(PlayGame playGame)
	{
		EngineState engineState = playGame.getEngineState();
		WeaponState playerCurWPState = playGame.curWeaponState;
		InputPoller ip = playGame.getInputPoller();
		Camera invCam = playGame.invCam;

		int player = engineState.getInitialSetIndex(PlayerSet.class);
		Vector2f playerPosition =
			engineState.unsafeGetComponentAt(PHitBox.class, player)
				.pureGetCenter();

		switch (playerCurWPState) {
		case Gun:

			Vector2f unitVecToMouse =
				AttackCycleHandlers
					.queryEntitySetWithPHitBoxToMouseDirection(
						playGame, player);

			engineState
				.unsafeGetComponentAt(HasAnimation.class,
						      player)
				.setAnimation(AnimationGetter.queryPlayerSprite(
					CardinalDirections
						.getClosestDirectionFromDirectionVector(
							unitVecToMouse),
					0));

			// generation of the bullet
			if (playGame.playerAmmo > 0) {
				int e = engineState.spawnEntitySet(
					new Bullet(playerPosition));
				engineState
					.unsafeGetComponentAt(
						PhysicsPCollisionBody.class, e)
					.setPositionPoint(
						engineState
							.unsafeGetComponentAt(
								PHitBox.class,
								player)
							.getCenter());
				float bulletSpeed =
					engineState
						.unsafeGetComponentAt(
							Movement.class, e)
						.getSpeed();

				engineState
					.unsafeGetComponentAt(Movement.class, e)
					.setVelocity(unitVecToMouse.pureMul(
						bulletSpeed));

				playGame.playerAmmo -= 1;
			}
			break;
		case Melee:
			System.out.println("attacked with melee weapon");
			break;
		}
	}

	public void recoilHandler(PlayGame g)
	{
	}
}
