package TileMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Components.Render;
import Components.TileCord;
import Resources.GameResources;

import poj.EngineState;
import poj.Render.ImageRenderObject;
import poj.Render.ImageWindow;
import poj.Render.Renderer;

public class Map
{
	public ArrayList<EngineState> mapLayers;
	// store the image window of each tiles
	public ArrayList<ImageWindow> tilesRenderPart =
		new ArrayList<ImageWindow>();
	public int rowsOfTileSet, colsOfTileSet, tileHeight, tileWidth,
		tileCount, mapWidth, mapHeight;

	public Map(int numLayers)
	{
		mapLayers = new ArrayList<EngineState>(numLayers);
	}
	public Map()
	{
		mapLayers = new ArrayList<EngineState>();
	}

	// addMapconfig
	// addTileSet
	// THEN addMapLayers

	public void addMapConfig(String mapConfigLocation)
		throws FileNotFoundException
	{
		Scanner configReader = new Scanner(new File(mapConfigLocation));
		String tempString[];
		tempString = configReader.nextLine().split("\"");
		/*
		mapHeight = Integer.parseInt(
			tempString[2].substring(1, tempString[2].length() - 1));
			*/
		while (configReader.hasNextLine()) {
			tempString = configReader.nextLine().split("\"");
			if (tempString.length > 1) {
				if (tempString[1].equals("tileheight")) {
					mapHeight = Integer.parseInt(
						tempString[2].substring(
							1,
							tempString[2].length()
								- 1));
				}
				if (tempString[1].equals("tilewidth")) {
					mapWidth = Integer.parseInt(
						tempString[2].substring(
							1,
							tempString[2].length()
								- 1));
				}
				/*
			if (tempString[1].equals("width")) {
				mapWidth = Integer.parseInt(
					tempString[2].substring(
						1,
						tempString[2].length()
							- 1));
				System.out.println("mapWidth ="
						   + mapWidth);
				break;
			}
			*/
			}
		}
		configReader.close();
	}


	public void addTileSet(String tileLocation) throws FileNotFoundException
	{
		Scanner mapReader = new Scanner(new File(tileLocation));
		while (mapReader.hasNextLine()) {
			String line = mapReader.nextLine();
			String tempList[] = line.split("\"");
			if (tempList.length > 1) {
				switch (tempList[1]) {
				case "columns":
					colsOfTileSet = Integer.parseInt(
						tempList[2].substring(
							1, tempList[2].length()
								   - 1));
					break;
				// case "imageheight":
				// break;
				// case "imagewidth":
				// break;
				case "tilecount":
					tileCount = Integer.parseInt(
						tempList[2].substring(
							1, tempList[2].length()
								   - 1));
					rowsOfTileSet =
						tileCount / colsOfTileSet;
					break;
				case "tileheight":
					tileHeight = Integer.parseInt(
						tempList[2].substring(
							1, tempList[2].length()
								   - 1));
					break;
				case "tilewidth":
					tileWidth = Integer.parseInt(
						tempList[2].substring(
							1, tempList[2].length()
								   - 1));
					break;
				}
			}
		}
		mapReader.close();
		createTileRenderObjects();
	}
	public void addMapLayer(String mapLayerLocation)
		throws FileNotFoundException
	{
		mapLayers.add(new EngineState(mapWidth * mapHeight));
		// get the last added engine state
		mapLayers.get(mapLayers.size() - 1)
			.registerComponent(TileCord.class);
		mapLayers.get(mapLayers.size() - 1)
			.registerComponent(Render.class);

		Scanner mapReader = new Scanner(new File(mapLayerLocation));
		int numRows = 0;
		while (mapReader.hasNextLine()) {
			++numRows;
			String line = mapReader.nextLine();
			String tempList[] = line.split(",");
			for (int i = 0; i < tempList.length; ++i) {
				int nextFreeIndex =
					mapLayers.get(mapLayers.size() - 1)
						.getFreeIndex();
				// add the tile cord to the engine
				mapLayers.get(mapLayers.size() - 1)
					.getComponents()
					.addComponentAt(
						TileCord.class,
						new TileCord(numRows - 1,
							     i % mapWidth),
						nextFreeIndex);
				if (Integer.parseInt(tempList[i]) != -1) {
					mapLayers.get(mapLayers.size() - 1)
						.getComponents()
						.addComponentAt(
							Render.class,
							new Render(new ImageRenderObject(
								numRows - 1,
								i % mapWidth,
								GameResources
									.testTile,
								tilesRenderPart.get(Integer.parseInt(
									tempList[i])))),
							nextFreeIndex);

				} else {
					// add NULL
					mapLayers.get(mapLayers.size() - 1)
						.getComponents()
						.addComponentAt(Render.class,
								null,
								nextFreeIndex);
				}
			}
		}
		mapReader.close();
	}


	public void createTileRenderObjects()
	{
		// rendering tile maps loop
		for (int j = 0; j < tileCount; ++j) {
			int curLayerCols = mapWidth,
			    // curTilesetRows = map.rowsOfTileSet,
				curTilesetCols = colsOfTileSet,
			    curTilesetHeight = tileHeight,
			    curTilesetWidth = tileWidth;
			// int valueOfTile = getTileFromMap(i, j);
			int xShiftValue = 0, yShiftValue = -mapHeight * 3 / 2;
			// TODO please don't delete the debug
			// message until the render for tile map
			// is set in stone!!!

			/*
			System.out.println("J = " + j);
			System.out.println("valueOfTile = "
					   + valueOfTile);
			System.out.println("curTilesetHeight = "
					   + curTilesetHeight);
			System.out.println("curTilesetWidth = "
					   + curTilesetWidth);
			System.out.println("j / curLayerCols ="
					   + j / curLayerCols
						     * 64);
			System.out.println("j % curLayerCols ="
					   + j % curLayerCols
						     * 64);
			System.out.println(
				"valueOfTile / curTilesetCols ="
				+ valueOfTile / curTilesetCols);
			System.out.println(
				"valueOfTile % curTilesetCols ="
				+ valueOfTile % curTilesetCols);
			System.out.println(
				"(j - curLayerCols) = "
				+ (j - curLayerCols));
			System.out.println(
				"(j - curLayerCols)/curLayerRows
			="
				+ (j - curLayerCols)
					  / curLayerCols);
			System.out.println(
				"(j
			-curLayerRows)/curLayerCols%2 = "
				+ ((j - curLayerCols)
				   / curLayerCols)
					  % 2);
			System.out.println("curLayerRows ="
					   + curLayerRows);
			System.out.println("xShiftValue= "
					   + xShiftValue);
			System.out.println("this bsv.."
					   + ((j - curLayerRows)
					      / curLayerCols));
			System.out.println(
				"((j - curLayerRows) /
			curLayerCols )% 2 = "
				+ ((j - curLayerRows)
				   / curLayerCols)
					  % 2);
			System.out.println("J/curLayerCols= "
					   + j / curLayerCols);
			System.out.println(
				"yshift bs ="
				+ (yShiftValue
				   * (j / curLayerCols)));
			*/
			if (((j - curLayerCols) / curLayerCols) % 2 == 0
			    && j >= curLayerCols) {
				xShiftValue = mapWidth / 2;
			}
			if (j < curLayerCols) {
				yShiftValue = 0;
			}
			tilesRenderPart.add(new ImageWindow(
				j % curTilesetCols * curTilesetWidth,
				j / curTilesetCols * curTilesetHeight,
				curTilesetWidth, curTilesetHeight));
		}
	}
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
	public void printRenderLayer(int layerNumber, Renderer renderer)
	{
		ArrayList<Render> mapRenderLayer =
			mapLayers.get(layerNumber)
				.getComponents()
				.getRawComponentArrayListPackedData(
					Render.class);
		renderer.pushRenderObject(mapRenderLayer.get(0).getGraphic());
		/*
		for (int i = 0; i < mapRenderLayer.size(); ++i) {
			mapRenderLayer.get(i).render(renderer);
		}
		*/
	}
}
