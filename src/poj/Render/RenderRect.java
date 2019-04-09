package poj.Render;
/**
 * RenderRect -- a data type to render a rectangle.
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared
 */

import java.awt.Color;
import poj.linear.*;

public class RenderRect extends RenderObject
{
	// dimensions
	private int width;
	private int height;


	private Color color;

	/**
	 * Render rect constructor
	 *
	 * @param  x top left coord
	 * @param  y top left coord
	 * @param  w width
	 * @param  h height
	 * @param  c color
	 */
	public RenderRect(int x, int y, int w, int h, Color c)
	{
		this.x = x;
		this.y = y;

		this.width = w;
		this.height = h;

		this.color = c;
	}

	/**
	 * Render rect constructor
	 *
	 * @param  x top left coord
	 * @param  y top left coord
	 * @param  w width
	 * @param  h height
	 */
	public RenderRect(int x, int y, int w, int h)
	{
		this(x, y, w, h, Color.RED);
	}

	/**
	 * Sets the position
	 *
	 * @param  v new position
	 */
	public void setPosition(Vector2f v)
	{
		this.x = Math.round(v.x);
		this.y = Math.round(v.y);
	}

	/**
	 * Sets the position
	 *
	 * @param  x new position
	 * @param  y new position
	 */
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the lengths
	 *
	 * @param  w new length
	 * @param  h new length
	 */
	public void setLengths(int w, int h)
	{
		this.width = w;
		this.height = h;
	}


	/**
	 * Sets the color
	 *
	 * @param  c new color
	 */
	public void setColor(Color c)
	{
		this.color = c;
	}

	/**
	 * Gets the color
	 *
	 * @return  color
	 */
	final public Color getColor()
	{
		return this.color;
	}

	/**
	 * Gets the width
	 *
	 * @return  width
	 */
	final public int getWidth()
	{
		return width;
	}

	/**
	 * Gets the height
	 *
	 * @return  height
	 */
	final public int getHeight()
	{
		return height;
	}
}
