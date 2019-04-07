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
import poj.Render.*;
import poj.linear.Vector2f;

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
			// TODO: create new wall states!!

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
		// TODO HAIANG:
		//!: parse wall for different layers????
		// 2:have indicator for which is wall!!!!!!

		createWallState();
		try {
			mapLayers.add(new MapLayer(mapWidth * mapHeight));
			// get the last added engine state
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(Render.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(WorldAttributes.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(PathFindCord.class);
			mapLayers.get(mapLayers.size() - 1)
				.registerComponent(PhysicsPCollisionBody.class);

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

					if (Integer.parseInt(tempList[i])
					    != -1) {

						// PathFindCord create
						// if it is wall
						if (wallState.get(Integer.parseInt(
							    tempList[i]))) {
							// NOT on 0th layer
							if (mapLayers.size()
							    > 1) {
								// mark the tile
								// at 0th layer
								// as a wall
								mapLayers.get(0)
									.unsafeGetComponentAt(
										PathFindCord
											.class
										,
										getEcsIndexFromWorldVector2f(new Vector2f(
											numRows - 1,
											i % mapWidth)))
									.setIsWall(
										true);

								mapLayers.get(0)
									.unsafeGetComponentAt(
										PathFindCord
											.class
										,
										getEcsIndexFromWorldVector2f(new Vector2f(
											numRows - 1,
											i % mapWidth)))
									.setDiffusionValue(
										0f);
							}
							// on 0th layer
							else {
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
											true,
											0),
										nextFreeIndex);
							}

							Vector2f cbwc = new Vector2f(
								numRows - 1,
								i % mapWidth);

							wallHitBox
								.get(Integer.parseInt(
									tempList[i]))
								.setPositionPoint(
									cbwc);

							mapLayers
								.get(COLLISION_LAYER)
								.addComponentAt(
									PhysicsPCollisionBody
										.class
									,
									new PhysicsPCollisionBody(
										wallHitBox
											.get(Integer.parseInt(
												tempList[i]))),
									nextFreeIndex);
						}
						// if not on the wall
						else {
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
						// and render it

						boolean isSpecialTile = false;
						BufferedImage imagePath =
							GameResources.tree1;
						float specialWidth = 1f,
						      specialHeight = 0f;
						if (Integer.parseInt(
							    tempList[i])
						    == 576) {
							// first tree
							isSpecialTile = true;
							imagePath =
								GameResources
									.tree1;

							specialWidth -= 1f;
							specialHeight -= 1f;

						} else if (Integer.parseInt(
								   tempList[i])
							   == 577) {
							// second tree
							isSpecialTile = true;
							imagePath =
								GameResources
									.tree2;
							specialWidth -= 2f;
							specialHeight -= 2f;
						} else if (Integer.parseInt(
								   tempList[i])
							   == 578) {
							// third tree
							isSpecialTile = true;
							imagePath =
								GameResources
									.tree3;
							specialWidth -= 1f;
							specialHeight -= 1f;
						} else if (Integer.parseInt(
								   tempList[i])
							   == 579) {
							// fourth tree
							isSpecialTile = true;
							imagePath =
								GameResources
									.tree4;
							specialWidth -= 2f;
							specialHeight -= 2f;
						}

						else if (Integer.parseInt(
								 tempList[i])
							 == 580) {
							// fifth tree
							isSpecialTile = true;
							imagePath =
								GameResources
									.tree5;
							specialWidth -= 2f;
							specialHeight -= 2f;
						}

						else if (Integer.parseInt(
								 tempList[i])
							 == 674) {
							// first pole
							isSpecialTile = true;
							imagePath =
								GameResources
									.pole1;
							specialWidth -= 3f;
							specialHeight -= 3f;
						}

						else if (Integer.parseInt(
								 tempList[i])
							 == 675) {
							// second pole
							isSpecialTile = true;
							imagePath =
								GameResources
									.pole2;
							specialWidth -= 3f;
							specialHeight -= 3f;
						} else if (Integer.parseInt(
								   tempList[i])
							   == 676) {
							// third pole
							isSpecialTile = true;

							imagePath =
								GameResources
									.pole3;
							specialWidth -= 3f;
							specialHeight -= 3f;
						} else if (Integer.parseInt(
								   tempList[i])
							   == 677) {
							// fourth pole
							isSpecialTile = true;
							imagePath =
								GameResources
									.pole4;
							specialWidth -= 3f;
							specialHeight -= 3f;
						} else if (Integer.parseInt(
								   tempList[i])
							   == 686) {
							// stop sign
							isSpecialTile = true;
							imagePath =
								GameResources
									.stopSign;
							specialWidth -= 1f;
							specialHeight -= 1f;
						}

						// check if it is special tile
						// (for the trees and poles and
						// stop sign)
						if (isSpecialTile) {
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
											0,
											0,
											imagePath),
										new Vector2f(
											specialWidth,
											specialHeight)),
									nextFreeIndex);
						} else {
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
												.officialTileSetAllignedBuildings,
											tilesRenderPart
												.get(Integer.parseInt(
													tempList[i]))),
										// new Vector2f( -(float)tileWidth / 2f, -(float)tileHeight / 2f)),
										new Vector2f(
											1,
											0)

											),
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
			wallHitBox.set(
				i, new PhysicsPCollisionBody(
					   new Vector2f(0f, 0f), // allignment
					   cbwc.pureAdd(0.5f,
							0.5f), // center
					   cbwc.pureAdd(0f, 0f), cbwc,
					   cbwc.pureAdd(1f, 0f),
					   cbwc.pureAdd(0f, 1f),
					   cbwc.pureAdd(1f, 1f)));
		}

		// clang-format off
		// small chairs
		wallState.set(80, true);
		wallState.set(81, true);
		wallHitBox.set(80, new PhysicsPCollisionBody(
					   new Vector2f(0f, 0f),
					   cbwc.pureAdd(0.5f,
							0.5f), // center
					   cbwc.pureAdd(0f, 0f), cbwc,
					   cbwc.pureAdd(1f, 0f),
					   cbwc.pureAdd(0f, 1f),
					   cbwc.pureAdd(1f, 1f)));
		wallHitBox.set(81, new PhysicsPCollisionBody(
					   new Vector2f(0f, 0f),
					   cbwc.pureAdd(0.5f,
							0.5f), // center
					   cbwc.pureAdd(0f, 0f), cbwc,
					   cbwc.pureAdd(1f, 0f),
					   cbwc.pureAdd(0f, 1f),
					   cbwc.pureAdd(1f, 1f)));

		// fence1
		wallState.set(137, true);
		wallHitBox.set(137,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));
		wallState.set(122, true);
		wallHitBox.set(122,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));

		 //major packed vector error happens at ONLY fence2! idk why..
		//fence 2
		wallState.set(123, true);
		wallHitBox.set(123,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));
		wallState.set(140, true);
		wallHitBox.set(140,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));

		//fence 3
		wallState.set(141, true);
		wallHitBox.set(141,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));
		wallState.set(126, true);
		wallHitBox.set(126,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));

		//fence 4
		wallState.set(76, true);
		wallHitBox.set(76,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));
		wallState.set(93, true);
		wallHitBox.set(93,
			new PhysicsPCollisionBody(
				new Vector2f(0f, 0f), cbwc.pureAdd(0f, 0f),
				cbwc.pureAdd(0f, 0f), cbwc.pureAdd(0f, 1f),
				cbwc.pureAdd(1f, 0f), cbwc.pureAdd(1f, 1f)));
		//buildings
				//hitbox for the post sign
			wallState.set(670, true);
			wallHitBox.set(670, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
		//blue building 2
			wallState.set(403, true);
			wallHitBox.set(403, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(404, true);
			wallHitBox.set(403, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(420, true);
			wallHitBox.set(420, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.25f,
							       0.25f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));

			wallState.set(421, true);
			wallHitBox.set(421, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.25f,
							       0.25f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));

			//cafe colored building 2:
			//
			wallState.set(423, true);
			wallHitBox.set(423, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(440, true);
			wallHitBox.set(440, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(408, true);
			wallHitBox.set(408, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(424, true);
			wallHitBox.set(424, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(425, true);
			wallHitBox.set(425, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));

		//school building 2
			wallState.set(482, true);
			wallHitBox.set(482, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(498, true);
			wallHitBox.set(498, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(499, true);
			wallHitBox.set(499, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(515, true);
			wallHitBox.set(515, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(516, true);
			wallHitBox.set(516, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));

			//white building 2
			wallState.set(503, true);
			wallHitBox.set(503, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(519, true);
			wallHitBox.set(519, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(535, true);
			wallHitBox.set(535, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(518, true);
			wallHitBox.set(518, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(520, true);
			wallHitBox.set(520, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));

			//water fountain
			wallState.set(316, true);
			wallHitBox.set(316, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));

			wallState.set(317, true);
			wallHitBox.set(316, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(332, true);
			wallHitBox.set(316, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(333, true);
			wallHitBox.set(313, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(300, true);
			wallHitBox.set(300, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
			wallState.set(301, true);
			wallHitBox.set(301, new PhysicsPCollisionBody(
						  new Vector2f(0f, 0f),
						  cbwc.pureAdd(0.5f,
							       0.5f), // center
						  cbwc.pureAdd(0f, 0f), cbwc,
						  cbwc.pureAdd(1f, 0f),
						  cbwc.pureAdd(0f, 1f),
						  cbwc.pureAdd(1f, 1f)));
		// clang-format on
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
