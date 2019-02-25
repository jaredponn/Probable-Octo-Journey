package PathFinding;
import java.util.ArrayList;

import Components.PathFindCord;
import TileMap.Map;
import TileMap.MapLayer;

import poj.Component.Components;
import poj.linear.Vector2f;
public class MapGeneration extends Thread
{
	private Map map;
	private MapLayer mapLayer;
	private int layerNumber;
	private float difCoefficient;
	public volatile boolean startGeneration = false;
	public volatile boolean endGeneration = false;
	public MapGeneration(Map map, int layerNumber, float difCoefficient)
	{
		this.map = map;
		this.mapLayer = map.getLayerEngineState(layerNumber);
		this.layerNumber = layerNumber;
		this.difCoefficient = difCoefficient;
		System.out.println("map generation thread generated!");
	}
	public void setMapLayerNumber(int newLayerNum)
	{
		this.layerNumber = newLayerNum;
		this.mapLayer = map.getLayerEngineState(layerNumber);
	}
	public void setDiffusionCoefficient(int newDifCoef)
	{
		this.difCoefficient = newDifCoef;
	}
	public void setStart()
	{
		this.startGeneration = true;
	}
	public void setEnd()
	{
		this.endGeneration = true;
	}

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
