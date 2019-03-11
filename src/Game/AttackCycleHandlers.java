package Game;

import Components.*;
import EntitySets.*;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.linear.Vector2f;

public class AttackCycleHandlers
{

	public static void playerAttackHandler(EngineState engineState,
					       int player,
					       WeaponState playerCurWPState,
					       InputPoller ip, Camera invCam,
					       double gameElapsedTime)
	{
		Vector2f playerPosition =
			engineState
				.getComponentAt(WorldAttributes.class, player)
				.getCenteredBottomQuarter();

		switch (playerCurWPState) {
		case Gun:

			Vector2f mousePosition = ip.getMousePosition();
			mousePosition.matrixMultiply(invCam);

			Vector2f tmp =
				playerPosition.pureSubtract(mousePosition);
			tmp.negate();
			Vector2f unitVecPlayerPosToMouseDelta =
				tmp.pureNormalize();

			engineState.getComponentAt(HasAnimation.class, player)
				.setAnimation(EngineTransforms.findPlayerFacingSprite(
					CardinalDirections
						.getClosestDirectionFromDirectionVector(
							tmp),
					0));

			// generation of the bullet
			int e = engineState.spawnEntitySet(new Bullet(
				gameElapsedTime,
				new Vector2f(
					engineState
						.getComponentAt(
							WorldAttributes.class,
							player)
						.getCenteredBottomQuarter())));
			engineState
				.getComponentAt(PhysicsPCollisionBody.class, e)
				.setPositionPoint(
					engineState
						.getComponentAt(
							WorldAttributes.class,
							player)
						.getCenteredBottomQuarter());
			float bulletSpeed =
				engineState.getComponentAt(Movement.class, e)
					.getSpeed();

			engineState.getComponentAt(Movement.class, e)
				.setVelocity(
					unitVecPlayerPosToMouseDelta.pureMul(
						bulletSpeed));
			break;
		case Melee:
			System.out.println("melee weapon was attacked");
			break;
		}
	}
}
