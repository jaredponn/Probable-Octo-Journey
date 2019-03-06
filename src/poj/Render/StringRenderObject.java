package poj.Render;

/**
 * StringRenderObject -- a data type to render a string to the screen.
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared
 */

import java.awt.*;

public class StringRenderObject extends RenderObject
{
	private String str;
	private Color color;
	private Font font;

	/**
	 * StringRenderObject constructor
	 *
	 * @param  s  string to render
	 * @param  x  top left x coord
	 * @param  y  top left y coord
	 * @param  c  color
	 */
	public StringRenderObject(String s, int x, int y, Color c)
	{
		this(s, x, y, c, new Font("TimesRoman", Font.PLAIN, 10));
	}

	/**
	 * StringRenderObject constructor
	 *
	 * @param  s  string to render
	 * @param  x  top left x coord
	 * @param  y  top left y coord
	 * @param  c  color
	 * @param  f  font
	 */
	public StringRenderObject(String s, int x, int y, Color c, Font f)
	{

		setStr(s);
		setPosition(x, y);
		setColor(c);
		setFont(f);
	}

	/**
	 * Set font
	 *
	 * @param  f  font
	 */
	public void setFont(Font f)
	{
		this.font = f;
	}

	/**
	 * Set string
	 *
	 * @param  s  string
	 */
	public void setStr(String s)
	{
		this.str = s;
	}

	/**
	 * Set color
	 *
	 * @param  c  color
	 */
	public void setColor(Color c)
	{
		this.color = c;
	}


	/**
	 * Get color
	 *
	 * @return  color
	 */
	final public Color getColor()
	{
		return this.color;
	}

	/**
	 * Get String
	 *
	 * @return  string
	 */
	public String getStr()
	{
		return this.str;
	}

	/**
	 * Get font
	 *
	 * @return  font
	 */
	final public Font getFont()
	{
		return this.font;
	}
}
