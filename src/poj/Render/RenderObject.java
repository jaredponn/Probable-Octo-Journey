package poj.Render;

import poj.linear.Vector2f;

public class RenderObject
{
	protected int x;
	protected int y;

	public void setX(final int x)
	{
		this.x = x;
	}

	public void setY(final int y)
	{
		this.y = y;
	}

	final public int getX()
	{
		return this.x;
	}

	final public int getY()
	{
		return this.y;
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setPosition(Vector2f v)
	{
		this.x = Math.round(v.x);
		this.y = Math.round(v.y);
	}

	final public Class<?> getRenderObjectType()
	{
		return this.getClass();
	}
}
