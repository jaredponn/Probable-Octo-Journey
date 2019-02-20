package Game;
import poj.EngineState;
import poj.Component.*;
import poj.Logger.Logger;

import java.util.Optional;

import Components.*;

import poj.linear.Vector2f;

import Resources.*;
import TileMap.*;

import poj.Render.Renderer;

public class EngineTransforms
{
	public static void updateAnimationWindows(EngineState engineState,
						  double dt)
	{
		for (HasAnimation i :
		     engineState.getRawComponentArrayListPackedData(
			     HasAnimation.class)) {
			Systems.updateHasAnimationComponent(i, dt);
		}
	}

	public static void
	cropSpriteSheetsFromAnimationWindows(EngineState engineState)
	{

		for (int i = engineState.getInitialComponentIndex(
			     HasAnimation.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(HasAnimation.class,
							   i)) {
			Systems.updateRenderComponentWindowFromHasAnimation(
				engineState.getComponentAt(Render.class, i),
				engineState.getComponentAt(HasAnimation.class,
							   i));
		}
	}


	public static void
	updateRenderScreenCoordinatesFromWorldCoordinatesWithCamera(
		EngineState engineState, final Camera cam)
	{
		for (int i = engineState.getInitialComponentIndex(
			     WorldAttributes.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(
			     WorldAttributes.class, i)) {
			Systems.updateRenderScreenCoordinatesFromWorldCoordinates(
				engineState.getComponentAt(
					WorldAttributes.class, i),
				engineState.getComponentAt(Render.class, i),
				cam);
		}
	}

	public static void
	updateWorldAttribPositionFromMovement(EngineState engineState,
					      double dt)
	{
		for (int i = engineState.getInitialComponentIndex(
			     Movement.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextComponentIndex(Movement.class, i)) {
			Systems.updateWorldAttribPositionFromMovement(
				engineState.getComponentAt(
					WorldAttributes.class, i),
				engineState.getComponentAt(Movement.class, i),
				dt);
		}
	}


	public static void
	setMovementVelocityFromMovementDirection(EngineState engineState)
	{
		for (int i = engineState.getInitialSetIndex(
			     MovementDirection.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(MovementDirection.class,
						     i)) {
			Systems.setMovementVelocityFromMovementDirection(
				engineState.getComponentAt(Movement.class, i),
				engineState.getComponentAt(
					MovementDirection.class, i));
		}
	}

	public static void
	addPlayerDiffusionValAtPlayerPos(EngineState engineState, Map map,
					 int player)
	{


		Vector2f playerPosition =
			engineState
				.getComponentAt(WorldAttributes.class, player)
				.getCenteredBottomQuarter();
		/*
		System.out.println("player X=" + playerPosition.x);
		System.out.println("player Y=" + playerPosition.y);
		System.out.println(
			"this.map.getEcsIndexFromWorldVector2f(playerPosition)"
			+ this.map.getEcsIndexFromWorldVector2f(
				  playerPosition));
		*/
		System.out.println(
			"player x position inside addPlayerDiffusionValAtPlayerPos ="
			+ playerPosition.x);
		System.out.println(
			"player y position inside addPlayerDiffusionValAtPlayerPos ="
			+ playerPosition.y);
		if (map.getEcsIndexFromWorldVector2f(playerPosition) != -1) {

			MapLayer mapLayer = map.getLayerEngineState(0);
			// map.printPathfindCord(0);

			if (!mapLayer.getComponentAt(
					     PathFindCord.class,
					     map.getEcsIndexFromWorldVector2f(
						     playerPosition))
				     .getIsWall()) {

				mapLayer.getComponentAt(
						PathFindCord.class,
						map.getEcsIndexFromWorldVector2f(
							playerPosition))
					.setDiffusionValue(
						GameConfig
							.PLAYER_DIFFUSION_VALUE);
			} else {

				mapLayer.getComponentAt(
						PathFindCord.class,
						map.getEcsIndexFromWorldVector2f(
							playerPosition))
					.setDiffusionValue(0f);
			}
		}
	}

	public static void updateCollisionBoxBodyTopLeftFromWorldAttributes(
		EngineState engineState)
	{

		for (int i = engineState.getInitialComponentIndex(
			     CollisionBoxBody.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(CollisionBoxBody.class,
						     i)) {
			Systems.updateCollisionBoxBodyTopLeftFromWorldAttributes(
				engineState.getComponentAt(
					CollisionBoxBody.class, i),
				engineState.getComponentAt(
					WorldAttributes.class, i));
		}
	}

	public static void resolveCollisionsOfEntitySets(
		EngineState engineState, Class<? extends Component> set0,
		Class<? extends Component> set1, double dt)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CollisionBoxBody a = engineState.getComponentAt(
				CollisionBoxBody.class, i);
			Movement va =
				engineState.getComponentAt(Movement.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final CollisionBoxBody b =
					engineState.getComponentAt(
						CollisionBoxBody.class, j);
				Movement vb = engineState.getComponentAt(
					Movement.class, j);


				final Optional<Double> tmp =
					Systems.calcTimeForCollision(
						a, b, va.getVelocity(),
						vb.getVelocity());

				if (tmp.isPresent()) {
					double timestep;
					if (tmp.get() == 0d) {
						timestep = -dt;
					} else {
						timestep =
							(tmp.get().doubleValue()
							 - 0.01f)
							/ dt;
					}
					va.getVelocity().mul((float)timestep);
					vb.getVelocity().mul((float)timestep);
					System.out.println("collisions?");
					break;
				}
			}
		}
	}

	public static void
	checkCollisionsBetween(EngineState engineState,
			       Class<? extends Component> set0,
			       Class<? extends Component> set1)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CollisionBoxBody a = engineState.getComponentAt(
				CollisionBoxBody.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final CollisionBoxBody b =
					engineState.getComponentAt(
						CollisionBoxBody.class, j);
				if (Systems.areCollisionBodiesCollding(a, b)) {
					System.out.println("Are colliding");
					break;
				}
			}
		}
	}

	public static void debugCollisionRenderPush(EngineState engineState,
						    Renderer r,
						    final Camera cam)
	{
		for (int i = engineState.getInitialSetIndex(
			     CollisionBoxBody.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(CollisionBoxBody.class,
						     i)) {
			Systems.debugCollisionRenderPush(
				engineState.getComponentAt(
					CollisionBoxBody.class, i),
				r, cam);
		}
	}
}
