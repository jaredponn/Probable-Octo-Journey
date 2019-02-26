package PathFinding;
import java.util.ArrayList;

import Components.PathFindCord;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Component.Components;
import poj.linear.Vector2f;

// https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html
// Note: all reads and writes of this system are atomic
public class MapGeneration extends Thread
{
	private Map map;
	private MapLayer mapLayer;
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
	 * @return      void
	 */
	public MapGeneration(Map map, int layerNumber, float difCoefficient)
	{
		this.map = map;
		this.mapLayer = map.getLayerEngineState(layerNumber);
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
	}
	/**
	 * set the end boolean of the thread
	 * *
	 * @return      void
	 */
	public void setEnd()
	{
		this.endGeneration = true;
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
				// will not loop to the empty tiles inside the
				// map
				for (int i = mapLayer.getInitialComponentIndex(
					     PathFindCord.class);
				     Components.isValidEntity(i);
				     i = mapLayer.getNextComponentIndex(
					     PathFindCord.class, i)) {
					PathFindCord center =
						mapLayer.getComponentAt(
							PathFindCord.class, i);
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
							// if not a wall
							if (!a.getIsWall()) {
								sum += a.getDiffusionValue()
								       - center.getDiffusionValue();
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
				int counter = 0;
				for (int i = 0; i < tempDiffusionBuffer.size();
				++i) { if (counter == map.mapWidth) { counter =
				0; System.out.println();
					}
					System.out.print(tempDiffusionBuffer.get(i)
				+ " , "); counter++;
				}
				System.out.println();
				*/

				if (tempDiffusionBuffer.size() > 0) {
					int count = 0;
					for (int i = mapLayer.getInitialComponentIndex(
						     PathFindCord.class);
					     Components.isValidEntity(i);
					     i = mapLayer.getNextComponentIndex(
						     PathFindCord.class, i)) {
						mapLayer.getComponentAt(
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
