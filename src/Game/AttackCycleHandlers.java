package Game;
/**
 * Attack cycler handlers. Game handlers for the attack cycle
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import Components.CardinalDirections;
import Components.HasAnimation;
import Components.Movement;
import Components.MovementDirection;
import Components.PhysicsPCollisionBody;
import Components.WorldAttributes;
import EntitySets.Bullet;
import EntitySets.PlayerSet;
import Resources.GameConfig;

import poj.EngineState;
import poj.GameWindow.InputPoller;
import poj.linear.Vector2f;

public class AttackCycleHandlers
{

	/**
	 * Player's attack handler.
	 * Variable names should be intuitive.
	 */
	public static void playerAttackHandler(EngineState engineState,
					       WeaponState playerCurWPState,
					       InputPoller ip, Camera invCam,
					       double gameElapsedTime)
	{

		int player = engineState.getInitialSetIndex(PlayerSet.class);
		Vector2f playerPosition =
			engineState
				.getComponentAt(PhysicsPCollisionBody.class,
						player)
				.getPolygon()
				.pureGetAPointInPolygon(0);

		switch (playerCurWPState) {
		case Gun:
			playerPosition.add(-GameConfig.PLAYER_WIDTH / 3,
					   -GameConfig.PLAYER_HEIGHT / 3);
			Vector2f mousePosition = ip.getMousePosition();
			mousePosition.matrixMultiply(invCam);

			Vector2f tmp =
				playerPosition.pureSubtract(mousePosition);
			tmp.negate();
			Vector2f unitVecPlayerPosToMouseDelta =
				tmp.pureNormalize();

			engineState.getComponentAt(HasAnimation.class, player)
				.setAnimation(AnimationGetter.queryPlayerSprite(
					CardinalDirections
						.getClosestDirectionFromDirectionVector(
							tmp),
					0));

			// generation of the bullet
			int e = engineState.spawnEntitySet(
				new Bullet(gameElapsedTime, playerPosition));
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

	public static void mobMeleeAttackHandler(EngineState engineState, int i)
	{
		engineState.getComponentAt(MovementDirection.class, i).print();
		System.out.println("direction");
	}
}
