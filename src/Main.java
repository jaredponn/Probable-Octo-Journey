import Components.Render;
import Components.Physics;

import poj.EngineState;
import poj.GameWindow.*;
import poj.Render.*;
import poj.Time.*;
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
		map.addMapLayer("resources/map1_ground.csv");
		map.addMapLayer("resources/map1_not_ground.csv");
		// map.printMapLayer();
		System.out.println(":10,".substring(1, 3));
		map.addTileSet("resources/tiles1.json");

		// init
		GameWindow gwindow = new GameWindow("Game");
		InputPoller inputpol = new InputPoller();
		GameCanvas gcanvas = new GameCanvas(600, 800, inputpol);

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

			renderer.pushRenderObject(new ImageRenderObject(
				500, 500, GameResources.testTile));

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
