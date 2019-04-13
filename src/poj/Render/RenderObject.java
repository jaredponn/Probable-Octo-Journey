package poj.Render;

/**
 * RenderObject -- Generic object for the Renderer
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared
 */

import poj.linear.Vector2f;

public class RenderObject
{
	protected int x;
	protected int y;
	protected int renderSortModifier;

	/**
	 *  set the x position
	 *
	 * @param  x new x position
	 */
	public void setX(final int x)
	{
		this.x = x;
	}

	/**
	 *  set the y position
	 *
	 * @param  y new y position
	 */
	public void setY(final int y)
	{
		this.y = y;
	}

	/**
	 *  get the x position
	 *
	 * @return  x current position
	 */
	final public int getX()
	{
		return this.x;
	}

	/**
	 *  get the y position
	 *
	 * @return  y current position
	 */
	final public int getY()
	{
		return this.y;
	}


	/**
	 *  gets the height -- filler method that should be overrided
	 *
	 * @return  height
	 */
	public int getHeight()
	{
		return 0;
	}

	/**
	 *  gets the width -- filler method that should be overrided
	 *
	 * @return  width
	 */
	public int getWidth()
	{
		return 0;
	}

	/**
	 *  sets the position
	 *
	 * @param  x new x position
	 * @param  y new x position
	 */
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 *  Alias for setPosition but for Vector2f
	 *
	 * @param  v new position
	 */
	public void setPosition(Vector2f v)
	{
		this.x = Math.round(v.x);
		this.y = Math.round(v.y);
	}

	/**
	 *  gets the render object type
	 *
	 * @return  render object type
	 */
	final public Class<?> getRenderObjectType()
	{
		return this.getClass();
	}

	/**
	 *  get render sort modifier
	 *
	 * @return  sort modifier
	 */
	public int getRenderSortModifier()
	{
		return this.renderSortModifier;
	}

	/**
	 *  set render sort modifier
	 *
	 * @param  n new sort modifier
	 */
	public void setRenderSortModifier(int n)
	{
		this.renderSortModifier = n;
	}
}
