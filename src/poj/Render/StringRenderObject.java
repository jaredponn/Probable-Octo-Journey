package poj.Render;

import java.awt.*;

public class StringRenderObject extends RenderObject
{
	private String str;
	private Color color;
	private Font font;

	public StringRenderObject(String s, int x, int y, Color c)
	{
		setStr(s);
		setPosition(x, y);
		setColor(c);

		setFont(new Font("TimesRoman", Font.PLAIN, 10));
	}

	public StringRenderObject(String s, int x, int y, Color c, Font f)
	{
		setStr(s);
		setPosition(x, y);
		setColor(c);
		setFont(f);
	}

	public void setFont(Font f)
	{
		this.font = f;
	}

	public void setStr(String s)
	{
		this.str = s;
	}

	public void setColor(Color c)
	{
		this.color = c;
	}


	final public Color getColor()
	{
		return this.color;
	}
	public String getStr()
	{
		return this.str;
	}

	final public Font getFont()
	{
		return this.font;
	}
}
