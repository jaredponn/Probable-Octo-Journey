package PathFinding;
import java.util.ArrayList;

import Components.PathFindCord;
import Components.PhysicsPCollisionBody;
import EntitySets.PlayerSet;
import Resources.GameConfig;
import TileMap.Map;
import TileMap.MapLayer;

import poj.EngineState;
import poj.Component.Components;
import poj.linear.Vector2f;

// https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html
// Note: all reads and writes of this system are atomic
public class MapGeneration extends Thread
{
	private Map map;
	private MapLayer mapLayer;
	private EngineState engineState;
	private int layerNumber;
	private float difCoefficient;
	public volatile boolean startGeneration = false;
	public volatile boolean endGeneration = false;


	/**
	 * Constructs the MapGeneration thread with the Map, layerNumber, and
	 * diffusion coefficient
	 *
	 * @param  map	the Map type of the map
	 * @param  layerNumber	integer which tells the thread which map will it
	 *         run the diffusion generation on
	 * @param  difCoefficient	float which sets the diffusion
	 *         coefficient of the algorithm
	 */
	public MapGeneration(Map map, EngineState engineState, int layerNumber,
			     float difCoefficient)
	{
		this.map = map;
		this.mapLayer = map.getLayerEngineState(layerNumber);
		this.engineState = engineState;
		this.layerNumber = layerNumber;
		this.difCoefficient = difCoefficient;
		System.out.println("map generation thread generated!");
	}
	/**
	 * set the Map layer number
	 * *
	 * @param  newLayerNum integer, will make the algorithm to search on the
	 *         layer "newLayerNum"
	 * @return      void
	 */
	public void setMapLayerNumber(int newLayerNum)
	{
		this.layerNumber = newLayerNum;
		this.mapLayer = map.getLayerEngineState(layerNumber);
	}
	/**
	 * set the diffusion coefficient
	 * *
	 * @param  newDifCoef integer, will make the algorithm to search on the
	 *         layer "newLayerNum"
	 * @return      void
	 */
	public void setDiffusionCoefficient(float newDifCoef)
	{
		this.difCoefficient = newDifCoef;
	}
	/**
	 * set the start boolean of the thread
	 * *
	 * @return      void
	 */
	public void setStart()
	{
		this.startGeneration = true;
		super.start();
	}
	/**
	 * set the end boolean of the thread
	 * *
	 * @return      void
	 */
	public void setEnd()
	{
		this.endGeneration = true;
		System.out.println("pathfinding thread exited");
	}

	/**
	 * run the thread continuously, until it is set to end (the pathfinding
	 * diffusion map generation will start or end depending on the
	 * startGeneration bool)
	 * *
	 * @return      void
	 */
	public void run()
	{
		// this thread will continue to run until we specify it to end
		while (!endGeneration) {
			// if start generation state is true
			// IMPORTANT: in world attributes  and PathFindCord, X
			// is RowNum, and Y is ColNum!!!!!! Width is rows,
			// height is cols
			if (this.startGeneration) {
				// will allocate vector size of map size
				ArrayList<Float> tempDiffusionBuffer =
					new ArrayList<Float>(map.mapWidth
							     * map.mapHeight);
				Vector2f playerPosition =
					this.engineState
						.unsafeGetComponentAt(
							PhysicsPCollisionBody
								.class,
							this.engineState.getInitialSetIndex(
								PlayerSet
									.class))
						.pureGetCenter();
				// make the player position integers
				playerPosition.x = (int)playerPosition.x;
				playerPosition.y = (int)playerPosition.y;

				// will not loop to the empty tiles inside the
				// map
				for (int i = mapLayer.getInitialComponentIndex(
					     PathFindCord.class);
				     Components.isValidEntity(i);
				     i = mapLayer.getNextComponentIndex(
					     PathFindCord.class, i)) {
					PathFindCord center =
						mapLayer.unsafeGetComponentAt(
							PathFindCord.class, i);

					/*
					if (center.getCord().equals(
						    playerPosition)) {
						tempDiffusionBuffer.add(
							(float)GameConfig
								.PLAYER_DIFFUSION_VALUE);
						continue;
					}
					*/

					// if the center is not a wall OR if the
					// player is standing on it (so if
					// player standing on a wall it will
					// still diffuse)
					if (!center.getIsWall()) {
						ArrayList<
							PathFindCord> tempNeighbours =
							Game.EngineTransforms
								.getEightNeighbourVector(
									map, i,
									mapLayer);
						float sum = 0f;
						for (PathFindCord a :
						     tempNeighbours) {
							// if not a wall or if a
							// player is standing on
							// that wall
							if (!a.getIsWall()) {
								sum += (a.getDiffusionValue()
									- center.getDiffusionValue());
							}
						}
						sum = center.getDiffusionValue()
						      + sum * difCoefficient;
						sum = sum * 1 / 2;
						tempDiffusionBuffer.add(sum);
					} else {
						tempDiffusionBuffer.add(0f);
					}
				}

				/*
				 * This is for debugging - printing all of the
				diffusion values int counter = 0; for (int i =
				0; i < tempDiffusionBuffer.size();
				     ++i) {
					if (counter == map.mapWidth) {
						counter = 0;
						System.out.println();
					}
					System.out.print(
						tempDiffusionBuffer.get(i)
						+ " , ");
					counter++;
				}
				System.out.println();
				System.out.println("done once");
				*/

				// set the diffusion value of the temporary
				// buffer to the ECS buffer
				if (tempDiffusionBuffer.size() > 0) {
					int count = 0;
					for (int i = mapLayer.getInitialComponentIndex(
						     PathFindCord.class);
					     Components.isValidEntity(i);
					     i = mapLayer.getNextComponentIndex(
						     PathFindCord.class, i)) {
						mapLayer.unsafeGetComponentAt(
								PathFindCord
									.class,
								i)
							.setDiffusionValue(
								tempDiffusionBuffer
									.get(count));
						++count;
					}
				}
			}
		}
	}
}
