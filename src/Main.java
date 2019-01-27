import Components.Render;
import Components.Physics;

import poj.EngineState;
import poj.GameWindow.*;
import poj.Render.*;
import poj.Time.*;
import poj.linear.*;
import poj.Animation;
import Components.*;
import EntitySets.*;
import TileMap.MapRender;
import java.io.FileNotFoundException;

import java.awt.*;
import java.awt.event.KeyEvent;
import Resources.GameResources;
import Game.Game;

public class Main
{

	public static final void main(String[] args)
		throws FileNotFoundException
	{

		MapRender map = new MapRender();
		// map.addMapLayer("resources/testMap.csv");
		map.addMapLayer("resources/map1_ground.csv");
		map.addMapLayer("resources/map1_not_ground.csv");
		map.addMapLayer("resources/map1_roof.csv");
		// map.printMapLayers();
		map.addTileSet("resources/tiles1.json");

		// init
		GameWindow gwindow = new GameWindow("Game");
		InputPoller inputpol = new InputPoller();
		GameCanvas gcanvas = new GameCanvas(1000, 1000, inputpol);

		gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);

		Renderer renderer = new Renderer(gcanvas);
		renderer.setClearColor(Color.black);

		long ti = 0;
		long tf = 0;
		long dt = 0;

		// render
		while (true) {

			ti = Timer.getTimeInMilliSeconds();

			if (inputpol.isKeyDown(KeyEvent.VK_H)) {
				System.out.println("yo");
				break;
			}

			if (inputpol.isLeftMouseButtonDown()) {
				System.out.println("left is down");
			}
			if (inputpol.isRightMouseButtonDown()) {
				System.out.println("right is down");
			}

			System.out.println("X, y position of mouse");
			System.out.println(inputpol.getMouseX());
			System.out.println(inputpol.getMouseY());
			System.out.println("----");

			System.out.println("mouse wheel");
			System.out.println(inputpol.getMouseWheelNotches());

			renderer.pushRenderObject(new ImageRenderObject(
				500, 500, GameResources.testImage));

			// new ImageWindow(0, 0, 10, 10)));


			// pushingdifferent render objects to the screen
			renderer.pushRenderObject(
				new RenderRect(100, 100, 100, 100, Color.BLUE));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 100, 100, Color.GREEN));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 30, 40, Color.RED));
			renderer.pushRenderObject(new StringRenderObject(
				"aaa", 300, 300, Color.GREEN));

			// simple exmaple of pushing the entire image to the
			// screen
			renderer.pushRenderObject(new ImageRenderObject(
				500, 500, GameResources.testImage));


			// pushing an image to the a portion of the image to the
			// screen
			renderer.pushRenderObject(new ImageRenderObject(
				100, 100, GameResources.testImage,
				new ImageWindow(120, 60, 60, 30)));

			// pushing an image to the screen with an animation
			renderer.pushRenderObject(new ImageRenderObject(
				300, 300, GameResources.testImage,
				GameResources.testImageAnimation
					.getImageWindow()));

			// pushing another image to the screen
			renderer.pushRenderObject(
				new ImageRenderObject(
					100, 100, GameResources.testImage,
					new ImageWindow(120, 60, 60, 30))
					.setImageShade(0.3f));

			// another exampl eof pushing something simple to the
			// screen
			renderer.pushRenderObject(new ImageRenderObject(
				0, 500, GameResources.testImage));

			// rendering tile maps loop
			for (int i = 0; i < map.mapLayers.size(); ++i) {
				int curLayerRows = map.mapLayers.get(i).rows,
				    curLayerCols = map.mapLayers.get(i).cols,
				    // curTilesetRows = map.rowsOfTileSet,
					curTilesetCols = map.colsOfTileSet,
				    curTilesetHeight = map.tileHeight,
				    curTilesetWidth = map.tileWidth;
				for (int j = 0;
				     j < (curLayerRows) * (curLayerCols); ++j) {
					int valueOfTile =
						map.getTileFromMap(i, j);
					int xShiftValue = 0,
					    yShiftValue =
						    -curTilesetHeight * 3 / 4;
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
					if (((j - curLayerCols) / curLayerCols)
							    % 2
						    == 0
					    && j >= curLayerCols) {
						xShiftValue =
							curTilesetWidth / 2;
					}
					if (j < curLayerCols) {
						yShiftValue = 0;
					}
					if (valueOfTile != -1) {
						renderer.pushRenderObject(new ImageRenderObject(
							j % curLayerCols
									* curTilesetWidth
								+ xShiftValue,
							(j / curLayerCols
							 * curTilesetHeight)
								+ yShiftValue
									  * (j
									     / curLayerCols),

							GameResources.testTile,
							new ImageWindow(
								valueOfTile
									% curTilesetCols
									* curTilesetWidth,
								valueOfTile
									/ curTilesetCols
									* curTilesetHeight,
								curTilesetWidth,
								curTilesetHeight)));
					}
				}
			}

			renderer.render();

			tf = Timer.getTimeInMilliSeconds();

			dt = tf - ti;

			// updating the animation
			GameResources.testImageAnimation.updateAnimationWindow(
				(long)dt);

			Timer.dynamicSleepToFrameRate(
				64, Timer.convertNanoSecondsToMilliseconds(dt));
		}

		// clearing
		gwindow.disposeWindow();
		Game g = new Game();
		g.initGame();
		g.runGameLoop();
		g.disposeWindow();
	}
}
