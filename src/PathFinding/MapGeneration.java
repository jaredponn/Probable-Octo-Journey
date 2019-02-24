package PathFinding;

import TileMap.Map;
import TileMap.MapLayer;

public class MapGeneration implements Runnable
{
	private Map map;
	private MapLayer mapLayer;
	private int layerNumber;
	private float difCoefficient;
	public MapGeneration(Map map, MapLayer mapLayer, int layerNumber,
			     float difCoefficient)
	{
		this.map = map;
		this.mapLayer = mapLayer;
		this.layerNumber = layerNumber;
		this.difCoefficient = difCoefficient;
		System.out.println("thread generated!");
	}
	public void setMapLayerNumber(int newLayerNum)
	{
		this.layerNumber = newLayerNum;
	}
	public void setDiffusionCoefficient(int newDifCoef)
	{
		this.difCoefficient = newDifCoef;
	}

	public void run()
	{
	}
}
