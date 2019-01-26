package poj.GameWindow;

import java.awt.*;


public class GameCanvas extends Canvas
{

	static final long serialVersionUID = 0;
	private int width;
	private int height;


	public GameCanvas(int w, int h, InputPoller pol)
	{
		super();
		this.setIgnoreRepaint(true);
		this.setSize(w, h);
		this.addKeyListener(pol);
		this.addMouseMotionListener(pol);
		this.addMouseListener(pol);
		this.setFocusable(true);

		width = w;
		height = h;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
