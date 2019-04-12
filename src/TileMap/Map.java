package TileMap;

import java.awt.image.*;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Components.PathFindCord;
import Components.PhysicsPCollisionBody;
import Components.Render;
import Components.WorldAttributes;
import Resources.GameResources;

import poj.Logger.Logger;
import poj.Pair;
import poj.Render.*;
import poj.linear.Vector2f;
import java.util.Optional;

public class Map
{
	private ArrayList<MapLayer> mapLayers;
	// store the image window of each tiles
	private ArrayList<ImageWindow> tilesRenderPart =
		new ArrayList<ImageWindow>();
	public int rowsOfTileSet, colsOfTileSet, tileHeight, tileWidth,
		tileCount, mapWidth = 0, mapHeight = 0;
	private ArrayList<Boolean> wallState;
	private ArrayList<PhysicsPCollisionBody> wallHitBox;


	public static final int COLLISION_LAYER = 0;

	/**
	 * Create map with specified number of mapLayer and the vector
	 * containing the mapLayer will be allocate the same number of mapLayer
	 *
	 * @param  numLayers	the number of the map layers
	 */
	public Map(int numLayers)
	{
		mapLayers = new ArrayList<MapLayer>(numLayers);
	}
	/**
	 * 	Create map a vector of mapLayer
	 */
	public Map()
	{
		mapLayers = new ArrayList<MapLayer>();
	}

	/**
	 * add and parse the map config file (.json map config file)
	 *
	 * @param  mapConfigLocation	the location of the map config file
	 *  @return      void
	 */
	public void addMapConfig(String mapConfigLocation)
	{
		try {
			Scanner configReader =
				new Scanner(new File(mapConfigLocation));
			String tempString[];
			tempString = configReader.nextLine().split("\"");
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
				"In TileMap addMapConfig ,file not found exception!"
					+ e.getMessage());
		}
	}


	/**
	 * add and parse the tile set file (.json config file)
	 *
	 * @param  tileConfigLocation	the location of the tile config file
	 *  @return      void
	 */
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
	/**
	 * add, parse a map layer, add the WorldAttributes, Render, PathFinding,
	 * and AabbCollisionBody components to the map layer (.csv config file)
	 *
	 * @param  mapLayerLocation	the location of the map layer config
	 *         file
	 *  @return      void
	 */
	public void addMapLayer(String mapLayerLocation)
	{
		createWallState();
		try {
			mapLayers.add(new MapLayer(mapWidth * mapHeight));
			// register components for each layer of the engine
			// state
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(Render.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(WorldAttributes.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(PathFindCord.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(PhysicsPCollisionBody.class);
			// scanner to read the csv file
			Scanner mapReader =
				new Scanner(new File(mapLayerLocation));
			int numRows = 0;
			// loop that reads each line of the file
			while (mapReader.hasNextLine()) {
				// keeps track of the number of rows
				++numRows;
				String line = mapReader.nextLine();
				String tempList[] = line.split(",");
				// for each integer in each line
				for (int i = 0; i < tempList.length; ++i) {
					// get the next free index
					int nextFreeIndex =
						mapLayers
							.get(mapLayers.size()
							     - 1)
							.getFreeIndex();

					// add the world attribute of tile cord
					// to the engine
					mapLayers.get(mapLayers.size() - 1)
						.addComponentAt(
							WorldAttributes.class,
							new WorldAttributes(
								numRows - 1,
								i % mapWidth,
								1f, 1f),
							nextFreeIndex);

					// if the tile/integer is NOT empty
					if (Integer.parseInt(tempList[i])
					    != -1) {
						// if it is wall
						if (wallState.get(Integer.parseInt(
							    tempList[i]))) {
							// add the collision
							// tile inside collision
							// layer (layer 0)
							addCollisionTile(
								numRows - 1,
								i % mapWidth,
								Integer.parseInt(
									tempList[i]),
								nextFreeIndex);
						}
						// if not on the wall
						else {
							// mark the tile at  as
							// NOT a wall
							mapLayers
								.get(mapLayers
									     .size()
								     - 1)
								.addComponentAt(
									PathFindCord
										.class
									,
									new PathFindCord(
										new Vector2f(
											numRows - 1,
											i % mapWidth),
										false,
										0),
									nextFreeIndex);
						}

						// here will pick the tile image
						// // and add the render
						// component to the ECS

						// check if it is special tile
						/*
						 * special tiles are the trees,
						 * poles, and the stop signs. In
						 * order to have rendering
						 * order/precedence (trees will
						 * block the player), we need to
						 * make the trees their
						 * particular image and when a
						 * tile have that tree, the
						 * entire image of the tree,
						 * instead of the tree split up
						 * indo many tiles, will be
						 * rendered to acheive this
						 * effect.
						 */
						Optional<Render> specialTile =
							getSpecialTile(Integer.parseInt(
								tempList[i]));

						// if it is a special tile
						if (specialTile.isPresent()) {
							// for <float, float> it
							// is the ordered pair
							// <specialWidth,
							// specialHeight>
							mapLayers
								.get(mapLayers
									     .size()
								     - 1)
								.getComponents()
								.addComponentAt(
									Render.class
									,
									specialTile
										.get(),
									nextFreeIndex);

						}
						// if it is NOT a special tile
						else {
							// add the pieve of the
							// image into the render
							// component
							mapLayers
								.get(mapLayers
									     .size()
								     - 1)
								.getComponents()
								.addComponentAt(
									Render.class
									,
									new Render(
										new ImageRenderObject(
											(i
											 % tileWidth)
												* tileWidth,
											(numRows
											 - 1) * tileHeight
												/ 4,
											GameResources
												.officialTileSetGoodFountain,
											tilesRenderPart
												.get(Integer.parseInt(
													tempList[i]))),
										new Vector2f(
											1,
											0)),
									nextFreeIndex);
						}
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

	public void addCollisionTile(int row, int col, int tileCord,
				     int nextFreeIndex)
	{
		// if the collision tile is not on the 0th layer
		if (mapLayers.size() > 1) {
			// mark the tile at 0th layer as a wall
			mapLayers.get(COLLISION_LAYER)
				.unsafeGetComponentAt(
					PathFindCord.class,
					getEcsIndexFromWorldVector2f(
						new Vector2f(row, col)))
				.setIsWall(true);

			// set the diffusion value of that tile to 0
			mapLayers.get(COLLISION_LAYER)
				.unsafeGetComponentAt(
					PathFindCord.class,
					getEcsIndexFromWorldVector2f(
						new Vector2f(row, col)))
				.setDiffusionValue(0f);
		}
		// on 0th layer
		else {
			// mark the tile at 0th layer as a wall
			mapLayers.get(mapLayers.size() - 1)
				.addComponentAt(
					PathFindCord.class,
					new PathFindCord(new Vector2f(row, col),
							 true, 0),
					nextFreeIndex);
		}

		Vector2f cbwc = new Vector2f(row, col);

		// create temporary hitbox that deals with vector translation
		PhysicsPCollisionBody tempHitBox = wallHitBox.get(tileCord);
		tempHitBox.setPositionPoint(cbwc);
		// use the temporary hitbox and add it as the collision tile
		mapLayers.get(COLLISION_LAYER)
			.addComponentAt(PhysicsPCollisionBody.class,
					new PhysicsPCollisionBody(tempHitBox),
					nextFreeIndex);
	}

	public Optional<Render> getSpecialTile(int tileCord)
	{
		float specialWidth = 1f, specialHeight = 0f;
		Render tmp = null;
		switch (tileCord) {
		case 0:
			// fountain
			tmp = new Render(GameResources.fountainBuilding,
					 specialWidth - 2f, specialHeight - 2f);
			tmp.getGraphic().setRenderSortModifier(-80);
			return Optional.of(tmp);
		case 1:
			// pink building
			tmp = new Render(GameResources.pinkBuilding,
					 specialWidth - 2f, specialHeight - 2f);
			tmp.getGraphic().setRenderSortModifier(-80);
			return Optional.of(tmp);
		case 2:
			// brown building
			tmp = new Render(GameResources.brownBuilding,
					 specialWidth - 3f + 0.1f,
					 specialHeight - 3f + 0.1f);
			tmp.getGraphic().setRenderSortModifier(-60);
			return Optional.of(tmp);
		case 3:
			// blue building
			tmp = new Render(GameResources.blueBuilding,
					 specialWidth - 2f, specialHeight - 2f);
			tmp.getGraphic().setRenderSortModifier(-50);
			return Optional.of(tmp);
		case 4:
			// school building / red
			tmp = new Render(GameResources.schoolBuilding,
					 specialWidth - 2f, specialHeight - 3f);
			tmp.getGraphic().setRenderSortModifier(-40);
			return Optional.of(tmp);
		case 5:
			// gas station building
			tmp = new Render(GameResources.gasStationBuilding,
					 specialWidth - 3f, specialHeight - 3f);
			tmp.getGraphic().setRenderSortModifier(-80);
			return Optional.of(tmp);
		case 576:
			// first tree
			return Optional.of(new Render(GameResources.tree1,
						      specialWidth - 1f,
						      specialHeight - 1f));
		case 577:
			// second tree
			return Optional.of(new Render(GameResources.tree2,
						      specialWidth - 2f,
						      specialHeight - 2f));
		case 578:
			// third tree
			return Optional.of(new Render(GameResources.tree3,
						      specialWidth - 1f,
						      specialHeight - 1f));
		case 579:
			// fourth tree
			return Optional.of(new Render(GameResources.tree4,
						      specialWidth - 2f,
						      specialHeight - 2f));

		case 580:
			// fifth tree
			return Optional.of(new Render(GameResources.tree5,
						      specialWidth - 2f,
						      specialHeight - 2f));
		case 674:
			// first pole
			return Optional.of(new Render(GameResources.pole1,
						      specialWidth - 3f,
						      specialHeight - 3f));

		case 675:
			// second pole
			return Optional.of(new Render(GameResources.pole2,
						      specialWidth - 3f,
						      specialHeight - 3f));
		case 676:
			// third pole
			return Optional.of(new Render(GameResources.pole3,
						      specialWidth - 3f,
						      specialHeight - 3f));
		case 677:
			// fourth pole
			return Optional.of(new Render(GameResources.pole4,
						      specialWidth - 3f,
						      specialHeight - 3f));
		case 686:
			// stop sign
			return Optional.of(new Render(GameResources.stopSign,
						      specialWidth - 1f,
						      specialHeight - 1f));
		}

		// if is not one of the special tiles, return empty
		return Optional.empty();
	}

	/**
	 * print the PathFindCord of a particular map layer (for debugging)
	 * @param  layerNumber	integer, the map layer number to print
	 *  @return      void
	 */
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

	/**
	 * create the tile render objects
	 *  @return      void
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
	/**
	 * render the tileMap to the screen
	 *   @param  renderer	Renderer, the renderer class that will render
	 * the tiles in all map layers
	 *  @return      void
	 */
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

	/**
	 * tells the wallState vector which index is a wall and which is not
	 * (required for hard code each tile number since it is tile specific)
	 *  @return      void
	 */
	public void createWallState()
	{
		wallState = new ArrayList<Boolean>(
			Collections.nCopies(tileCount, false));

		Vector2f cbwc = new Vector2f(0f, 0f);
		wallHitBox = new ArrayList<PhysicsPCollisionBody>(
			Collections.nCopies(
				tileCount, new PhysicsPCollisionBody(
						   new Vector2f(0f, 0f),
						   cbwc.pureAdd(0.5f,
								0.5f), // center
						   cbwc, cbwc.pureAdd(1f, 0f),
						   cbwc.pureAdd(0f, 1f),
						   cbwc.pureAdd(1f, 1f))));


		// setting the tile cord of these tiles
		// cars
		for (int i = 128; i <= 136; ++i) {
			wallState.set(i, true);
		}

		// small chairs
		wallState.set(80, true);
		wallState.set(81, true);

		// medium chairs
		wallState.set(683, true);
		wallState.set(684, true);

		// fence1
		wallState.set(137, true);
		wallState.set(122, true);

		// fence 2
		wallState.set(123, true);
		wallState.set(140, true);

		// fence 3
		wallState.set(141, true);
		wallState.set(126, true);

		// fence 4
		wallState.set(76, true);
		wallState.set(93, true);
		// buildings
		// hitbox for the post sign

		wallState.set(670, true);
		// blue building 2
		wallState.set(403, true);
		wallState.set(404, true);
		wallState.set(420, true);
		wallState.set(421, true);
		// cafe colored building 2:

		wallState.set(423, true);
		wallState.set(440, true);
		wallState.set(408, true);
		wallState.set(424, true);
		wallState.set(425, true);

		// school building 2
		wallState.set(482, true);
		wallState.set(498, true);
		wallState.set(499, true);
		wallState.set(515, true);
		wallState.set(516, true);

		// white building 2
		wallState.set(503, true);
		wallState.set(519, true);
		wallState.set(535, true);
		wallState.set(518, true);
		wallState.set(520, true);
		// water fountain
		wallState.set(599, true);
		wallState.set(614, true);
		wallState.set(615, true);
		wallState.set(616, true);
		wallState.set(631, true);

		// gas station
		wallState.set(218, true);
		wallState.set(219, true);
		wallState.set(203, true);
	}


	// IMPORTANT: in world attributes  and PathFindCord, X is RowNum, and Y
	// is ColNum!!!!!!
	// Width is rows, height is cols
	/**
	 * test if a WorldAttributes is a valid map cordinate
	 *    @param  tile the WorldAttributes object
	 *  @return      void
	 */
	public boolean isValidCord(WorldAttributes tile)
	{
		return isValidCord(tile.getOriginCoord());
	}

	/**
	 * test if a Vector2f is a valid map cordinate
	 *    @param  cord the Vector2f object
	 *  @return      void
	 */
	public boolean isValidCord(Vector2f cord)
	{
		return ((int)cord.x < mapHeight && (int)cord.y < mapWidth)
			&& ((int)cord.x >= 0 && (int)cord.y >= 0);
	}
	/**
	 * test if a PathFindCord is a valid map cordinate
	 *    @param  tile the PathFindCord object
	 *  @return      void
	 */
	public boolean isValidCord(PathFindCord tile)
	{
		Vector2f cord = tile.getCord();
		return isValidCord(cord);
	}

	/**
	 * get ECS cordinate from WorldAttributes, will return -1 if it does
	 * not exist
	 *    @param  cord the WorldAttributes object
	 *  @return      void
	 */
	public int getEcsCordFromWorldAttributes(WorldAttributes cord)
	{
		return getEcsIndexFromWorldVector2f(cord.getOriginCoord());
	}

	/**
	 * get ECS cordinate from Vector2f, will return -1 if it does
	 * not exist
	 *    @param  v the Vector2f object
	 *  @return      void
	 */
	public int getEcsIndexFromWorldVector2f(Vector2f v)
	{

		if (isValidCord(v)) {
			return (int)v.x * (mapWidth) + (int)v.y;
		} else {
			return -1;
		}
	}
	/**
	 * get vector2f from an ECS index, if it is invalid index it will just
	 * crash the program
	 *    @param  index the ECS index
	 *  @return      Vector2f
	 */
	public Vector2f getVector2fFromEcsIndex(int index)
	{
		return new Vector2f(index / mapWidth, index % mapWidth);
	}
	/**
	 * get tile layer for the renderer with a specific layerNumber
	 *    @param  layerNumber integer, the layer number
	 *  @return      ArrayList<Render>
	 */
	public ArrayList<Render> getTileLayerRender(int layerNumber)
	{
		return this.mapLayers.get(layerNumber)
			.getComponents()
			.getRawComponentArrayListPackedData(Render.class);
	}

	/**
	 * get MapLayer object with a specific layerNumber
	 *    @param  layerNumber integer, the layer number
	 *  @return      MapLayer
	 */
	public MapLayer getLayerEngineState(int layerNumber)
	{
		return this.mapLayers.get(layerNumber);
	}

	/**
	 * get the map size (width * height)
	 *  @return      integer, the size of the map
	 */
	public int getMapSize()
	{
		return this.mapWidth * this.mapHeight;
	}

	/**
	 * get the number of layers of the map
	 *  @return      integer, number of layers in the map
	 */
	public int getNumberOfLayers()
	{
		return this.mapLayers.size();
	}
}
