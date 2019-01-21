import Components.Render;
import Components.Physics;

import poj.EngineState;
import poj.Render.*;
import poj.Time.*;
import poj.Animation;
import Components.*;
import Systems.*;
import EntitySets.*;

import java.awt.*;
import Resources.GameResources;

public class Main
{

	public static final void main(String[] args)
	{
		// init
		GameWindow gwindow = new GameWindow("Game");
		GameCanvas gcanvas = new GameCanvas(600, 800);
		gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);
		Renderer renderer = new Renderer(gcanvas);
		renderer.setClearColor(Color.black);

		long ti = 0;
		long tf = 0;
		long dt = 0;


		// render
		while (true) {
			ti = Timer.getTimeInMilliSeconds();


			renderer.pushRenderObject(
				new RenderRect(100, 100, 100, 100, Color.BLUE));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 100, 100, Color.GREEN));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 30, 40, Color.RED));
			renderer.pushRenderObject(new StringRenderObject(
				"aaa", 300, 300, Color.GREEN));

			/*
			renderer.pushRenderObject(new ImageRenderObject(
				300, 300, GameResources.testImage));*/

			renderer.pushRenderObject(new ImageRenderObject(
				300, 300, GameResources.testImage,
				GameResources.testImageAnimation
					.getImageWindow()));

			renderer.pushRenderObject(new ImageRenderObject(
				100, 100, GameResources.testImage,
				new ImageWindow(120, 60, 60, 30)));


			renderer.render();

			tf = Timer.getTimeInMilliSeconds();

			dt = tf - ti;

			GameResources.testImageAnimation.updateAnimationWindow(
				(long)dt);

			Timer.dynamicSleepToFrameRate(
				64, Timer.convertNanoSecondsToMilliseconds(dt));
		}

		// clearing
		// gwindow.disposeWindow();
	}
}
