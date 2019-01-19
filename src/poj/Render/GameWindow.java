package poj.Render;

import poj.Logger;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameWindow extends JFrame
{
	static final long serialVersionUID = 0;

	public GameWindow(String s)
	{
		super(s);

		this.setResizable(false);
		this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void defaultAddGameCanvasAndSetBufferStrat(GameCanvas gc)
	{
		this.add(gc);
		this.pack();
		this.setVisible(true);
		gc.createBufferStrategy(2);
	}
}
