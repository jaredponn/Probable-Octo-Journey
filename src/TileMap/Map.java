package TileMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Components.Render;
import Components.TileCord;
import Resources.GameResources;
import poj.Logger.Logger;

import poj.EngineState;
import poj.Render.ImageRenderObject;
import poj.Render.ImageWindow;
import poj.Render.Renderer;

/*
 * getTileCordFromWorldCord
 * isValidTileCord
 */

public class Map
{
	public ArrayList<EngineState> mapLayers;
	// store the image window of each tiles
	public ArrayList<ImageWindow> tilesRenderPart =
		new ArrayList<ImageWindow>();
	public int rowsOfTileSet, colsOfTileSet, tileHeight, tileWidth,
		tileCount, mapWidth = 0, mapHeight = 0;

	public Map(int numLayers)
	{
		mapLayers = new ArrayList<EngineState>(numLayers);
	}
	public Map()
	{
		mapLayers = new ArrayList<EngineState>();
	}

	public TileCord getTileCordFromWorldCord(int x, int y)
	{
		y = y - tileHeight + 32;
		// TODO did not include the out of bound screen exception yet
		// TODO make the parser to parese in the correct map tile width
		// and height configuration..
		//
		// maybe reverse transform the way to store the rtile cords..
		System.out.println("x cordinate in the function" + x);
		System.out.println("y cordinate in the function" + y);
		if (x < 0 || y < 0) {
			return new TileCord(-1, -1);
		}
		System.out.println("X tile cord =" + x / tileWidth);
		System.out.println("Y tile cord =" + y / 32);
		return new TileCord(x % mapHeight, y / mapHeight);
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
		try {
			mapLayers.add(new EngineState(mapWidth * mapHeight));
			// get the last added engine state
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(TileCord.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(Render.class);

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
						.getComponents()
						.addComponentAt(
							TileCord.class,
							new TileCord(
								numRows - 1,
								i % mapWidth),
							nextFreeIndex);
					if ((numRows) % 2 == 0
					    && (numRows) > 1) { // not in the
								// first row
						xShiftValue = tileWidth / 2;
					}

					if (Integer.parseInt(tempList[i])
					    != -1) {
						mapLayers
							.get(mapLayers.size()
							     - 1)
							.getComponents()
							.addComponentAt(
								Render.class,
								new Render(new ImageRenderObject(
									(i
									 % tileWidth) * tileWidth
										+ xShiftValue,
									//(numRows
									//- 1) *
									// tileHeight,
									(numRows
									 - 1)
										* tileHeight
										/// 4,
										/ 8,
									GameResources
										.testTile,
									tilesRenderPart
										.get(Integer.parseInt(
											tempList[i])))),
								nextFreeIndex);

					} else {
						// add NULL
						mapLayers
							.get(mapLayers.size()
							     - 1)
							.getComponents()
							.addComponentAt(
								Render.class,
								null,
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
	}


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

	public ArrayList<Render> getTileLayerRender(int layerNumber)
	{
		return this.mapLayers.get(layerNumber)
			.getComponents()
			.getRawComponentArrayListPackedData(Render.class);
	}
}
