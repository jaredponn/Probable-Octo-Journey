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
	public volatile boolean start = false;
	public MapGeneration(Map map, int layerNumber, float difCoefficient)
	{
		this.map = map;
		this.mapLayer = map.getLayerEngineState(layerNumber);
		this.layerNumber = layerNumber;
		this.difCoefficient = difCoefficient;
		System.out.println("thread generated!");
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

	public void run()
	{
		while (true) {
			// System.out.println("start in MapGeneration is ="
			//+ this.start);
			if (this.start == true) {
				// TODO: HAIYANG will only do one layer!!!!!
				// will get the 8 neighbours aroud it

				// temporary buffer used to store the modified
				// diffusion values int playerECSindex =
				// this.map.getEcsIndexFromWorldVector2f(
				// super.getComponentAt(WorldAttributes.class,
				// this.player)
				//.getCenteredBottomQuarter());
				ArrayList<Float> tempDiffusionBuffer =
					new ArrayList<Float>();
				// will not loop to the empty tiles inside the
				// map, hopefull !!
				for (int i = this.mapLayer
						     .getInitialComponentIndex(
							     PathFindCord
								     .class);
				     Components.isValidEntity(i);
				     i = this.mapLayer.getNextComponentIndex(
					     PathFindCord.class, i)) {
					// Vector2f testVector=
					//

					float sum = 0f;
					PathFindCord center =
						this.mapLayer.getComponentAt(
							PathFindCord.class, i);
					/*
					System.out.println(
						"center's diffusion value: = "
						+ center.getDiffusionValue());
						*/
					if (!center.getIsWall()) {
						ArrayList<
							PathFindCord> tempNeighbours =
							getEightNeighbourVector(
								map, i,
								this.mapLayer);
						// System.out.println("size of
						// tempNeighbours ="
						//+ tempNeighbours.size());
						/*
						for (PathFindCord a :
						tempNeighbours) { a.printCord();
						}
						*/
						for (PathFindCord a :
						     tempNeighbours) {
							// if not a wall
							if (!a.getIsWall()) {
								sum += a.getDiffusionValue()
								       - center.getDiffusionValue();
							} else {
								/*
							System.out.println(
								"I did not
							pass!!, the Vector x
							index is ="
								+ a.getCord()
									  .x);
							System.out.println(
								"I did not
							pass!!, the Vector y
							index is ="
								+ a.getCord()
									  .y);
									  */
							}
						}
						/*
						System.out.println(
							"sum before adding
						center diffusion value = "
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
				for (int i = 0; i < tempDiffusionBuffer.size();
				     ++i) {
					if (counter == this.map.mapWidth) {
						counter = 0;
						System.out.println();
					}
					System.out.print(
						tempDiffusionBuffer.get(i)
						+ " , ");
					counter++;
				}
				System.out.println();
				*/

				if (tempDiffusionBuffer.size() > 0) {
					for (int i = this.mapLayer.getInitialComponentIndex(
						     PathFindCord.class);
					     Components.isValidEntity(i);
					     i = this.mapLayer
							 .getNextComponentIndex(
								 PathFindCord
									 .class,
								 i)) {
						this.mapLayer
							.getComponentAt(
								PathFindCord
									.class,
								i)
							.setDiffusionValue(
								tempDiffusionBuffer
									.get(0));
						tempDiffusionBuffer.remove(0);
					}
				}
				this.start = false;
				System.out.println("start inside class is ="
						   + start);
			} else {
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

	public void setStart()
	{
		this.start = true;
	}
}
