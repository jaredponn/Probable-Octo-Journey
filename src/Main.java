import Components.Render;
import Components.Physics;

import poj.EngineState;
import poj.Render.*;
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

	// https://www.gamedev.net/articles/programming/general-and-gameplay-programming/java-games-active-rendering-r2418/
	// https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferStrategy.html
	//
	public static final void main(String[] args)
	{

		GameWindow gwindow = new GameWindow("Game");
		GameCanvas gcanvas = new GameCanvas(600, 800);

		gwindow.defaultAddGameCanvasAndSetBufferStrat(gcanvas);

		Renderer renderer = new Renderer(gcanvas);


		renderer.render();
	}
}
