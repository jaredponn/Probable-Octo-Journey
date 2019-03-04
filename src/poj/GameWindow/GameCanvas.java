package poj.GameWindow;

import java.awt.*;


public class GameCanvas extends Canvas
{

	static final long serialVersionUID = 0;
	private int width;
	private int height;


	/**
	 * Constructuct's the game window
	 *
	 * @param  w width of canvas
	 * @param  h height of canvas
	 * @param  pol Input poller
	 */
	public GameCanvas(int w, int h, InputPoller pol)
	{
		super();
		this.setIgnoreRepaint(true);
		this.setSize(w, h);
		this.addKeyListener(pol);
		this.addMouseMotionListener(pol);
		this.addMouseWheelListener(pol);
		this.addMouseListener(pol);
		this.setFocusable(true);

		width = w;
		height = h;
	}

	/**
	 * gets the width
	 *
	 * @return width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * gets the height
	 *
	 * @return height
	 */
	public int getHeight()
	{
		return height;
	}
}
