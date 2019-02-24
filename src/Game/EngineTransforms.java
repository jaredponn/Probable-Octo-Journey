package Game;
import poj.EngineState;
import poj.Component.*;
import poj.Logger.Logger;

import java.util.Optional;
import java.util.ArrayList;

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

	// path finding

	// IMPORTANT: in world attributes  and PathFindCord, X is RowNum, and Y
	// is ColNum!!!!!!
	// Width is rows, height is cols
	public static void generateDiffusionMap(Map map, int layerNumber,
						float difCoefficient)
	{
		// TODO: HAIYANG will only do one layer!!!!!
		// will get the 8 neighbours aroud it

		// temporary buffer used to store the modified diffusion values
		// int playerECSindex = this.map.getEcsIndexFromWorldVector2f(
		// super.getComponentAt(WorldAttributes.class, this.player)
		//.getCenteredBottomQuarter());
		MapLayer mapLayer = map.getLayerEngineState(layerNumber);
		ArrayList<Float> tempDiffusionBuffer = new ArrayList<Float>();
		// will not loop to the empty tiles inside the map, hopefull !!
		for (int i = mapLayer.getInitialComponentIndex(
			     PathFindCord.class);
		     Components.isValidEntity(i);
		     i = mapLayer.getNextComponentIndex(PathFindCord.class,
							i)) {
			// Vector2f testVector=
			//

			float sum = 0f;
			PathFindCord center =
				mapLayer.getComponentAt(PathFindCord.class, i);
			/*
			System.out.println(
				"center's diffusion value: = "
				+ center.getDiffusionValue());
				*/
			if (!center.getIsWall()) {
				ArrayList<PathFindCord> tempNeighbours =
					getEightNeighbourVector(map, i,
								mapLayer);
				// System.out.println("size of
				// tempNeighbours ="
				//+ tempNeighbours.size());
				/*
				for (PathFindCord a : tempNeighbours) {
					a.printCord();
				}
				*/
				for (PathFindCord a : tempNeighbours) {
					// if not a wall
					if (!a.getIsWall()) {
						sum += a.getDiffusionValue()
						       - center.getDiffusionValue();
					} else {
						/*
					System.out.println(
						"I did not pass!!, the
					Vector x  index is ="
						+ a.getCord()
							  .x);
					System.out.println(
						"I did not pass!!, the
					Vector y  index is ="
						+ a.getCord()
							  .y);
							  */
					}
				}
				/*
				System.out.println(
					"sum before adding center
				diffusion value = "
					+ sum);
				*/
				sum = center.getDiffusionValue()
				      + sum * difCoefficient;
				sum = sum * 1 / 2;
				/*
				if (i != playerECSindex) {
					sum = sum * 1 / 2;
				}
				*/


				tempDiffusionBuffer.add(sum);
				/*
				System.out.println(
					"sum after adding center
				diffusion value = "
					+ sum);
				*/
			} else {
				sum = 0f;
				tempDiffusionBuffer.add(sum);
			}
		}


		/*
		int counter = 0;
		for (int i = 0; i < tempDiffusionBuffer.size(); ++i) {
			if (counter == this.map.mapWidth) {
				counter = 0;
				System.out.println();
			}
			System.out.print(tempDiffusionBuffer.get(i) + " , ");
			counter++;
		}
		System.out.println();
		*/
		if (tempDiffusionBuffer.size() > 0) {
			for (int i = mapLayer.getInitialComponentIndex(
				     PathFindCord.class);
			     Components.isValidEntity(i);
			     i = mapLayer.getNextComponentIndex(
				     PathFindCord.class, i)) {
				mapLayer.getComponentAt(PathFindCord.class, i)
					.setDiffusionValue(
						tempDiffusionBuffer.get(0));
				tempDiffusionBuffer.remove(0);
			}
		}
	}

	public static ArrayList<PathFindCord>
	getEightNeighbourVector(Map map, int indexOfEcs, MapLayer mapLayer)
	{
		ArrayList<Vector2f> neighbours = new ArrayList<Vector2f>();
		ArrayList<PathFindCord> tmp = new ArrayList<PathFindCord>();
		Vector2f centerVector =
			mapLayer.getComponentAt(PathFindCord.class, indexOfEcs)
				.getCord();
		// add the 8 neighbours
		neighbours.add(centerVector.addAndReturnVector(-1, 0));
		neighbours.add(centerVector.addAndReturnVector(0, -1));
		neighbours.add(centerVector.addAndReturnVector(0, 1));
		neighbours.add(centerVector.addAndReturnVector(1, 0));

		neighbours.add(centerVector.addAndReturnVector(1, 1));
		neighbours.add(centerVector.addAndReturnVector(-1, 1));
		neighbours.add(centerVector.addAndReturnVector(1, -1));
		neighbours.add(centerVector.addAndReturnVector(-1, -1));
		for (Vector2f neib : neighbours) {
			// if the tile is valid
			if (map.isValidCord(neib)
			    && (mapLayer.hasComponent(
				       PathFindCord.class,
				       map.getEcsIndexFromWorldVector2f(
					       neib)))) {
				tmp.add(mapLayer.getComponentAt(
					PathFindCord.class,
					map.getEcsIndexFromWorldVector2f(
						neib)));
			}
		}
		return tmp;
	}

	public static void
	updateEnemyPositionFromPlayer(EngineState engineState, Map map,
				      int layerNumber, int player, int mob1)
	{
		EngineTransforms.addPlayerDiffusionValAtPlayerPos(
			engineState, map, layerNumber, player);
		MapLayer mapLayer = map.getLayerEngineState(layerNumber);
		/*
		System.out.println("mob position in world attribues: ");
		System.out.println(
			"mob ecs cord= "
			+ this.map.getEcsCordFromWorldAttributes(
				  super.getComponentAt(WorldAttributes.class,
						       this.mob1)));
		System.out.println(
			"mob x dir="
			+ super.getComponentAt(WorldAttributes.class, this.mob1)
				  .getCenteredBottomQuarter()
				  .x);

		System.out.println(
			"mob y dir="
			+ super.getComponentAt(WorldAttributes.class, this.mob1)
				  .getCenteredBottomQuarter()
				  .y);
		System.out.println(this.map.isValidCord(
			super.getComponentAt(WorldAttributes.class, this.mob1)
				.getCenteredBottomQuarter()));
			*/
		ArrayList<PathFindCord> mobNeighb = getEightNeighbourVector(
			map,
			map.getEcsIndexFromWorldVector2f(
				engineState
					.getComponentAt(WorldAttributes.class,
							mob1)
					.getCenteredBottomQuarter()),
			mapLayer);
		float maxValue = 0;
		Vector2f maxPosition = new Vector2f();
		Vector2f mobPosition =
			engineState.getComponentAt(WorldAttributes.class, mob1)
				.getCenteredBottomQuarter();

		Vector2f playerPosition =
			engineState
				.getComponentAt(WorldAttributes.class, player)
				.getCenteredBottomQuarter();
		/*
		System.out.println("mob x position: " + mobPosition.x);
		System.out.println("mob y position: " + mobPosition.y);
		System.out.println("mob x floored position: "
				   + (int)mobPosition.x);
		System.out.println("mob y floored position: "
				   + (int)mobPosition.y);
		//System.out.println("player x position: " + playerPosition.x);
		System.out.println("player y position: " + playerPosition.y);
		System.out.println("player x floored position: "
				   + (int)playerPosition.x);
		System.out.println("player y floored position: "
				   + (int)playerPosition.y);
		System.out.println("player x position  floor ="
				   + playerPosition.x);
		System.out.println("player y position  floor ="
				   + playerPosition.y);
		System.out.println("mob x position before floor ="
				   + mobPosition.x);
		System.out.println("mob y position before floor ="
				   + mobPosition.y);
		System.out.println("mob x position after  floor ="
				   + mobPosition.x);
		System.out.println("mob y position after  floor ="
				   + mobPosition.y);
				   */

		// else {
		for (PathFindCord neib : mobNeighb) {
			if (neib.getDiffusionValue() >= maxValue) {
				maxValue = neib.getDiffusionValue();
				maxPosition = neib.getCord();
			}
		}
		/*
		System.out.println(" the max value calculated for the enemy: "
				   + maxValue);
		System.out.println("maxPosition x =" + maxPosition.x);
		System.out.println("maxPosition y =" + maxPosition.y);
		System.out.println("cardinal inside enemy :");
		CardinalDirections.print(
			CardinalDirections
				.getClosestDirectionFromDirectionVector(
					maxPosition.subtractAndReturnVector(
						mobPosition)));
						*/
		CardinalDirections.print(
			CardinalDirections
				.getClosestDirectionFromDirectionVector(
					playerPosition.subtractAndReturnVector(
						mobPosition)));
		System.out.println(
			" the diffusion value at mob is ="
			+ map.getLayerEngineState(0)
				  .getComponentAt(
					  PathFindCord.class,
					  map.getEcsIndexFromWorldVector2f(
						  engineState
							  .getComponentAt(
								  WorldAttributes
									  .class
								  ,
								  mob1)
							  .getCenteredBottomQuarter()))
				  .getDiffusionValue());
		System.out.println("player x position  floor ="
				   + playerPosition.x);
		System.out.println("player y position  floor ="
				   + playerPosition.y);
		System.out.println("mob x position before floor ="
				   + mobPosition.x);
		System.out.println("mob y position before floor ="
				   + mobPosition.y);
		// if mob and player are at the same tile
		if ((int)mobPosition.x == (int)playerPosition.x
		    && (int)mobPosition.y == (int)playerPosition.y) {
			System.out.println(
				"went inside where the player cord is equal to mob cord!");

			if (Math.abs(mobPosition.x - playerPosition.x) != 0f
			    && Math.abs(mobPosition.y - playerPosition.y)
				       != 0f) {
				engineState
					.getComponentAt(MovementDirection.class,
							mob1)
					.setDirection(CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition)));
				engineState.getComponentAt(Movement.class, mob1)
					.setSpeed(GameConfig.MOB_SPEED);
			} else {
				engineState.getComponentAt(Movement.class, mob1)
					.setSpeed(0);
			}
		}
		// test if the current tile the mob is at is bigger than the max
		// value
		else if (
			maxValue
			<= map.getLayerEngineState(0)
				   .getComponentAt(
					   PathFindCord.class,
					   map.getEcsIndexFromWorldVector2f(
						   engineState
							   .getComponentAt(
								   WorldAttributes
									   .class
								   ,
								   mob1)
							   .getCenteredBottomQuarter()))
				   .getDiffusionValue()

		) {
			System.out.println(
				" went inside this cord is bigger than all neightbours!!");
			System.out.println(
				"set the mob speed equal to 0!!!!!!!");
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(0f);
		} else {
			// mobPosition.floor();
			// maxPosition.floor();

			/*
			super.getComponentAt(MovementDirection.class, this.mob1)
				.setDirection(
					CardinalDirections.getClosestDirectionFromDirectionVector(
						maxPosition
							.subtractAndReturnVector(
								mobPosition)));
				*/


			// if the mob is on a wall
			// TODO: Please don't delete this!!!!! might be
			// important when the player is moving aroudn the
			// corners..
			/*
			if (mapLayer.getComponentAt(
					    PathFindCord.class,
					    this.map.getEcsIndexFromWorldVector2f(
						    mobPosition))
				    .getIsWall()) {
				System.out.println("mob inside wall!!");

				CardinalDirections curDir =
					CardinalDirections.getClosestDirectionFromDirectionVector(
						maxPosition
							.subtractAndReturnVector(
								mobPosition));
				System.out.println(
					"inside the wall, the new cardinal
			position of the player is ");
				CardinalDirections.print(curDir);
				if (curDir == CardinalDirections.W
				    || curDir == CardinalDirections.E
				    || curDir == CardinalDirections.N
				    || curDir == CardinalDirections.S) {
					curDir =
			CardinalDirections.getClosestDirectionFromDirectionVector(
						playerPosition
							.subtractAndReturnVector(
								mobPosition));
					System.out.println(
						"went inside WENS!! The new
			cardinal direction need to be checked it: ");
					CardinalDirections.print(curDir);
					System.out.println(
						"finish went inside WENS!! ");
				}


				if (curDir == CardinalDirections.NW) {
					super.getComponentAt(
						     MovementDirection.class,
						     this.mob1)
						.setDirection(
							CardinalDirections.NE);
				} else if (curDir == CardinalDirections.SW) {
					super.getComponentAt(
						     MovementDirection.class,
						     this.mob1)
						.setDirection(
							CardinalDirections.NW);
				} else if (curDir == CardinalDirections.NE) {
					super.getComponentAt(
						     MovementDirection.class,
						     this.mob1)
						.setDirection(
							CardinalDirections.SE);
				} else if (curDir == CardinalDirections.SE) {
					super.getComponentAt(
						     MovementDirection.class,
						     this.mob1)
						.setDirection(
							CardinalDirections.SW);
				}

				System.out.println(
					"aftr changign the cardianl direction
			inside the wall, the new cardinal position of the player
			is "); CardinalDirections.print( super.getComponentAt(
						     MovementDirection.class,
						     this.mob1)
						.getDirection());
			} else {
			*/
			mobPosition.floor();
			maxPosition.floor();
			engineState
				.getComponentAt(MovementDirection.class, mob1)
				.setDirection(
					CardinalDirections.getClosestDirectionFromDirectionVector(
						maxPosition
							.subtractAndReturnVector(
								mobPosition)));
			engineState.getComponentAt(Movement.class, mob1)
				.setSpeed(GameConfig.MOB_SPEED);
		}
	}

	public static void
	addPlayerDiffusionValAtPlayerPos(EngineState engineState, Map map,
					 int layerNumber, int player)
	{

		MapLayer mapLayer = map.getLayerEngineState(layerNumber);
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


	public static void
	updateCollisionAabbBoxBodiesTopLeftFromWorldAttributes(
		EngineState engineState)
	{

		for (int i = engineState.getInitialComponentIndex(
			     CollisionAabbBodies.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(CollisionAabbBodies.class,
						     i)) {
			Systems.updateAabbCollisionBodiesTopLeftFromWorldAttributes(
				engineState.getComponentAt(
					CollisionAabbBodies.class, i),
				engineState.getComponentAt(
					WorldAttributes.class, i));
		}
	}

	public static void
	aabbCollisionBodiesResolve(EngineState engineState,
				   Class<? extends Component> set0,
				   Class<? extends Component> set1, double dt)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CollisionAabbBodies a =
				engineState.getComponentAt(
					CollisionAabbBodies.class, i);
			Movement va =
				engineState.getComponentAt(Movement.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final CollisionAabbBodies b =
					engineState.getComponentAt(
						CollisionAabbBodies.class, j);
				Movement vb = engineState.getComponentAt(
					Movement.class, j);


				final Optional<Double> tmp =
					Systems.calcTimeForCollisionForAabbBodies(
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
	aabbCollisionBodiesCheckCollision(EngineState engineState,
					  Class<? extends Component> set0,
					  Class<? extends Component> set1)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CollisionAabbBodies a =
				engineState.getComponentAt(
					CollisionAabbBodies.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final CollisionAabbBodies b =
					engineState.getComponentAt(
						CollisionAabbBodies.class, j);
				if (Systems.areAabbCollisionBodiesColliding(
					    a, b)) {
					System.out.println("Are colliding");
					break;
				}
			}
		}
	}

	public static void
	debugAabbCollisionBodiesRender(EngineState engineState, Renderer r,
				       final Camera cam)
	{
		for (int i = engineState.getInitialSetIndex(
			     CollisionAabbBodies.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(CollisionAabbBodies.class,
						     i)) {
			Systems.aabbCollisionBodiesDebugRender(
				engineState.getComponentAt(
					CollisionAabbBodies.class, i),
				r, cam);
		}
	}

	public static void
	updateCircleCollisionFromWorldAttributes(EngineState engineState)
	{
		for (int i = engineState.getInitialSetIndex(
			     CircleCollisionBody.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(CircleCollisionBody.class,
						     i)) {
			Systems.updateCircleCollisionFromWorldAttributes(
				engineState.getComponentAt(
					CircleCollisionBody.class, i),
				engineState.getComponentAt(
					WorldAttributes.class, i));
		}
	}

	public static void
	updateAabbCollisionFromWorldAttributes(EngineState engineState)
	{
		for (int i = engineState.getInitialSetIndex(
			     AabbCollisionBody.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(AabbCollisionBody.class,
						     i)) {
			Systems.updateAabbCollisionBodyFromWorldAttributes(
				engineState.getComponentAt(
					AabbCollisionBody.class, i),
				engineState.getComponentAt(
					WorldAttributes.class, i));
		}
	}

	public static void
	areCirclesCollidingFromSets(EngineState engineState,
				    Class<? extends Component> set0,
				    Class<? extends Component> set1)
	{

		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CircleCollisionBody a =
				engineState.getComponentAt(
					CircleCollisionBody.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final CircleCollisionBody b =
					engineState.getComponentAt(
						CircleCollisionBody.class, j);
				if (Systems.areCollisionCirclesColliding(a,
									 b)) {
					System.out.println("Are colliding");
					break;
				}
			}
		}
	}

	public static void
	areCirclesCollidingAgainstAabb(EngineState engineState,
				       Class<? extends Component> set0,
				       Class<? extends Component> set1)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CircleCollisionBody a =
				engineState.getComponentAt(
					CircleCollisionBody.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final AabbCollisionBody b =
					engineState.getComponentAt(
						AabbCollisionBody.class, j);
				if (Systems.areCollisionCirclesCollidingAgainstAabb(
					    a, b)) {
					System.out.println("Are colliding");
					break;
				}
			}
		}
	}

	public static void resolveCircleCollisionBodyWithAabbCollisionBody(
		EngineState engineState, Class<? extends Component> set0,
		Class<? extends Component> set1, double dt)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CircleCollisionBody a =
				engineState.getComponentAt(
					CircleCollisionBody.class, i);
			Movement m =
				engineState.getComponentAt(Movement.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final AabbCollisionBody b =
					engineState.getComponentAt(
						AabbCollisionBody.class, j);

				Systems.resolveCircleCollisionBodyWithAabbCollisionBody(
					m, a, b, dt);
			}
		}
	}

	public static void pushCircleCollisionBodyOutOfAabbCollisionBody(
		EngineState engineState, Class<? extends Component> set0,
		Class<? extends Component> set1)
	{
		for (int i = engineState.getInitialSetIndex(set0);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(set0, i)) {

			final CircleCollisionBody a =
				engineState.getComponentAt(
					CircleCollisionBody.class, i);
			WorldAttributes w = engineState.getComponentAt(
				WorldAttributes.class, i);

			for (int j = engineState.getInitialSetIndex(set1);
			     Components.isValidEntity(j);
			     j = engineState.getNextSetIndex(set1, j)) {

				final AabbCollisionBody b =
					engineState.getComponentAt(
						AabbCollisionBody.class, j);

				if (Systems.areCollisionCirclesCollidingAgainstAabb(
					    a, b)) {
					System.out.println("coollisions");
					Systems.pushCircleCollisionBodyOutOfAabbCollisionBody(
						w, a, b);
					break;
				}
			}
		}
	}


	public static void debugCircleCollisionRender(EngineState engineState,
						      Renderer r,
						      final Camera cam)
	{
		for (int i = engineState.getInitialSetIndex(
			     CircleCollisionBody.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(CircleCollisionBody.class,
						     i)) {
			Systems.circleCollisionDebugRenderer(
				engineState.getComponentAt(
					CircleCollisionBody.class, i),
				r, cam);
		}
	}

	public static void debugAabbCollisionRender(EngineState engineState,
						    Renderer r,
						    final Camera cam)
	{
		for (int i = engineState.getInitialSetIndex(
			     AabbCollisionBody.class);
		     Components.isValidEntity(i);
		     i = engineState.getNextSetIndex(AabbCollisionBody.class,
						     i)) {
			Systems.aabbCollisionBodyDebugRender(
				engineState.getComponentAt(
					AabbCollisionBody.class, i),
				r, cam);
		}
	}
}
