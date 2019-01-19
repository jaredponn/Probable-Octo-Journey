package poj.Render;
import java.awt.*;

public class GameCanvas extends Canvas
{
	private int width;
	private int height;

	public GameCanvas(int w, int h)
	{
		super();
		this.setIgnoreRepaint(true);
		this.setSize(w, h);

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
