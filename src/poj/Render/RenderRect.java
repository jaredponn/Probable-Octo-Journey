package poj.Render;

import java.awt.Color;
import poj.linear.*;


// DEPRECATED

public class RenderRect extends RenderObject
{
	// dimensions
	private int width;
	private int height;


	private Color color;

	public RenderRect(int x, int y, int w, int h, Color c)
	{
		this.x = x;
		this.y = y;

		this.width = w;
		this.height = h;

		this.color = c;
	}

	public RenderRect(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;

		this.width = w;
		this.height = h;

		this.color = Color.RED;
	}

	public void setPosition(Vector2f v)
	{
		this.x = Math.round(v.x);
		this.y = Math.round(v.y);
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setLengths(int w, int h)
	{
		this.width = w;
		this.height = h;
	}


	public void setColor(Color c)
	{
		this.color = c;
	}

	final public Color getColor()
	{
		return this.color;
	}

	final public int getWidth()
	{
		return width;
	}

	final public int getHeight()
	{
		return height;
	}
}
