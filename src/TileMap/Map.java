package TileMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Components.Render;
import Components.WorldAttributes;
import Components.PathFindCord;
import Resources.GameResources;
import poj.Logger.Logger;

import poj.Render.ImageRenderObject;
import poj.linear.Vector2f;
import poj.linear.MatrixCord;
import poj.Render.ImageWindow;
import poj.Render.Renderer;

/*
 * getTileCordFromWorldCord
 * isValidTileCord
 */

public class Map
{
	public ArrayList<MapLayer> mapLayers;
	// store the image window of each tiles
	public ArrayList<ImageWindow> tilesRenderPart =
		new ArrayList<ImageWindow>();
	public int rowsOfTileSet, colsOfTileSet, tileHeight, tileWidth,
		tileCount, mapWidth = 0, mapHeight = 0;

	public Map(int numLayers)
	{
		mapLayers = new ArrayList<MapLayer>(numLayers);
	}
	public Map()
	{
		mapLayers = new ArrayList<MapLayer>();
	}

	public void addMapConfig(String mapConfigLocation)
	{
		try {
			Scanner configReader =
				new Scanner(new File(mapConfigLocation));
			String tempString[];
			tempString = configReader.nextLine().split("\"");
			/*
			mapHeight = Integer.parseInt(
				tempString[2].substring(1,
			tempString[2].length() - 1));
				*/
			while (configReader.hasNextLine()) {
				tempString =
					configReader.nextLine().split("\"");
				if (tempString.length > 1
				    && (mapHeight == 0 || mapWidth == 0)) {
					if (tempString[1].equals("height")) {
						mapHeight = Integer.parseInt(
							tempString[2].substring(
								1,
								tempString[2].length()
									- 1));
					}
					if (tempString[1].equals("width")) {
						mapWidth = Integer.parseInt(
							tempString[2].substring(
								1,
								tempString[2].length()
									- 1));
					}
				}
			}
			configReader.close();
		} catch (FileNotFoundException e) {
			Logger.lassert(
				true,
				"In TileMap addTileSet ,file not found exception!"
					+ e.getMessage());
		}
	}


	public void addTileSet(String tileLocation)
	{
		try {
			Scanner mapReader = new Scanner(new File(tileLocation));
			while (mapReader.hasNextLine()) {
				String line = mapReader.nextLine();
				String tempList[] = line.split("\"");
				if (tempList.length > 1) {
					switch (tempList[1]) {
					case "columns":
						colsOfTileSet = Integer.parseInt(
							tempList[2].substring(
								1,
								tempList[2].length()
									- 1));
						break;
					// case "imageheight":
					// break;
					// case "imagewidth":
					// break;
					case "tilecount":
						tileCount = Integer.parseInt(
							tempList[2].substring(
								1,
								tempList[2].length()
									- 1));
						rowsOfTileSet = tileCount
								/ colsOfTileSet;
						break;
					case "tileheight":
						tileHeight = Integer.parseInt(
							tempList[2].substring(
								1,
								tempList[2].length()
									- 1));
						break;
					case "tilewidth":
						tileWidth = Integer.parseInt(
							tempList[2].substring(
								1,
								tempList[2].length()
									- 1));
						break;
					}
				}
			}
			mapReader.close();
			createTileRenderObjects();
		} catch (FileNotFoundException e) {
			System.out.println(
				"In TileMap addTileSet ,file not found exception!"
				+ e.getMessage());
		}
	}
	public void addMapLayer(String mapLayerLocation)
	{
		// TODO HAIANG:
		//!: parse wall for different layers????
		// 2:have indicator for which is wall!!!!!!
		try {
			mapLayers.add(new MapLayer(mapWidth * mapHeight));
			// get the last added engine state
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(Render.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(WorldAttributes.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(PathFindCord.class);

			Scanner mapReader =
				new Scanner(new File(mapLayerLocation));
			int numRows = 0;
			while (mapReader.hasNextLine()) {
				int xShiftValue = 0;
				++numRows;
				String line = mapReader.nextLine();
				String tempList[] = line.split(",");
				for (int i = 0; i < tempList.length; ++i) {
					int nextFreeIndex =
						mapLayers
							.get(mapLayers.size()
							     - 1)
							.getFreeIndex();
					// add the tile cord to the engine

					mapLayers.get(mapLayers.size() - 1)
						.addComponentAt(
							WorldAttributes.class,
							new WorldAttributes(
								numRows - 1,
								i % mapWidth,
								1f, 1f),
							nextFreeIndex);

					if ((numRows) % 2 == 0
					    && (numRows) > 1) { // not in the
								// first row
						xShiftValue = tileWidth / 2;
					}

					// PathFindCord create
					if (Integer.parseInt(tempList[i])
					    == 17) {
						mapLayers
							.get(mapLayers.size()
							     - 1)
							.addComponentAt(
								PathFindCord
									.class,
								new PathFindCord(
									new Vector2f(
										numRows - 1,
										i % mapWidth),
									true,
									0),
								nextFreeIndex);
					} else {
						mapLayers
							.get(mapLayers.size()
							     - 1)
							.addComponentAt(
								PathFindCord
									.class,
								new PathFindCord(
									new Vector2f(
										numRows - 1,
										i % mapWidth),
									false,
									0),
								nextFreeIndex);
					}

					if (Integer.parseInt(tempList[i])
					    != -1) {
						mapLayers
							.get(mapLayers.size()
							     - 1)
							.getComponents()
							.addComponentAt(
								Render.class,
								new Render(
									new ImageRenderObject(
										(i
										 % tileWidth)
											* tileWidth,
										//+
										// xShiftValue,
										//(numRows
										//- 1) *
										// tileHeight,
										(numRows
										 - 1) * tileHeight
											/ 4,
										/// 8,
										GameResources
											.testTile,
										tilesRenderPart
											.get(Integer.parseInt(
												tempList[i]))),
									new Vector2f(
										-(float)tileWidth
											/ 2f, // TODO also awful someone please figure out whty this does this.
										-(float)tileHeight // TODO This is awful -- this is the translation needed to render the tiles so they line up with where the world coordinates are
											/ 2f)),
								nextFreeIndex);
					}
				}
			}
			mapReader.close();
		} catch (FileNotFoundException e) {
			System.out.println(
				"In TileMap addMapLayer ,file not found exception!"
				+ e.getMessage());
		}
		/*
		System.out.println("map width = " + mapWidth);
		System.out.println("map height = " + mapHeight);
		System.out.println(getEcsCordFromWorldAttributes(
			new WorldAttributes(1, 0, 1f, 1f)));
		*/
	}

	public void printPathfindCord(int layerNumber)
	{

		ArrayList<PathFindCord> pathfindLayerData =
			mapLayers.get(layerNumber)
				.getComponents()
				.getRawComponentArrayListPackedData(
					PathFindCord.class);
		int tempCount = 0;
		for (int i = 0; i < pathfindLayerData.size(); ++i) {
			System.out.println("i =" + i);
			pathfindLayerData.get(i).printWall();
			if (pathfindLayerData.get(i).getIsWall()) {
				++tempCount;
			}
			pathfindLayerData.get(i).printDiffusionVal();
		}
	}

	// add here for future loop reference
	/*
		public void printMapLayer(int layerNumber)
		{
			ArrayList<TileCord> mapTileCordData =
				mapLayers.get(layerNumber)
					.getComponents()
					.getRawComponentArrayListPackedData(
						TileCord.class);
			for (int i = 0; i < mapTileCordData.size(); ++i) {
				mapTileCordData.get(i).print();
			}
		}
		*/
	public void createTileRenderObjects()
	{
		// rendering tile maps loop
		for (int j = 0; j < tileCount; ++j) {
			int curTilesetCols = colsOfTileSet,
			    curTilesetHeight = tileHeight,
			    curTilesetWidth = tileWidth;
			tilesRenderPart.add(new ImageWindow(
				j % curTilesetCols * curTilesetWidth,
				j / curTilesetCols * curTilesetHeight,
				curTilesetWidth, curTilesetHeight));
		}
	}
	public void renderTileMap(Renderer renderer)
	{
		ArrayList<Render> mapRenderLayer;
		for (int layerNumber = 0; layerNumber < mapLayers.size();
		     ++layerNumber) {
			mapRenderLayer =
				mapLayers.get(layerNumber)
					.getComponents()
					.getRawComponentArrayListPackedData(
						Render.class);
			for (int i = 0; i < mapRenderLayer.size(); ++i) {
				if (mapRenderLayer.get(i) != null) {
					mapRenderLayer.get(i).render(renderer);
				}
			}
		}
	}


	// IMPORTANT: in world attributes  and PathFindCord, X is RowNum, and Y
	// is ColNum!!!!!!
	// Width is rows, height is cols
	public boolean isValidCord(WorldAttributes tile)
	{
		return isValidCord(tile.getOriginCoord());
	}

	public boolean isValidCord(Vector2f cord)
	{
		return (cord.x <= mapHeight && cord.y <= mapWidth)
			&& (cord.x >= 0 && cord.y >= 0);
	}
	public boolean isValidCord(PathFindCord tile)
	{
		Vector2f cord = tile.getCord();
		return !(cord.x >= mapHeight || cord.y >= mapWidth)
			&& (cord.x >= 0 && cord.y >= 0);
	}

	public int getEcsCordFromWorldAttributes(WorldAttributes cord)
	{
		return getEcsIndexFromWorldVector2f(cord.getOriginCoord());
	}

	public int getEcsIndexFromWorldVector2f(Vector2f v)
	{

		if (isValidCord(v)) {

			return (int)v.x * (mapWidth) + (int)v.y;
		} else {
			return -1;
		}
	}
	public Vector2f getVector2fFromEcsIndex(int index)
	{
		return new Vector2f(index / mapWidth, index % mapWidth);
	}

	public ArrayList<Render> getTileLayerRender(int layerNumber)
	{
		return this.mapLayers.get(layerNumber)
			.getComponents()
			.getRawComponentArrayListPackedData(Render.class);
	}

	public MapLayer getLayerEngineState(int layerNumber)
	{
		return this.mapLayers.get(layerNumber);
	}

	public int getMapLength()
	{
		return this.mapWidth * this.mapHeight;
	}
}
