import Components.Render;
import Components.Physics;

import poj.EngineState;
import poj.Render.*;
import poj.Time.*;
import Components.*;
import Systems.*;
import EntitySets.*;

import java.util.Random;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

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
			ti = Timer.getTimeInNanoSeconds();

			renderer.pushRenderObject(
				new RenderRect(100, 100, 100, 100, Color.BLUE));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 100, 100, Color.GREEN));
			renderer.pushRenderObject(
				new RenderRect(00, 0, 30, 40, Color.RED));
			renderer.pushRenderObject(
				new RenderString("aaa", 300, 300, Color.GREEN));


			renderer.render();

			tf = Timer.getTimeInNanoSeconds();

			dt = tf - ti;

			Timer.dynamicSleepToFrameRate(
				64, Timer.convertNanoSecondsToMilliseconds(dt));
		}

		// clearing
		// gwindow.disposeWindow();
	}
}
