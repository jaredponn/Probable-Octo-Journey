package poj.Render;

/**
 * ImageWindow - a "window" to view a partial point of the image
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared
 */
import java.awt.image.*;

public class ImageWindow
{
	private int x;
	private int y;
	private int width;
	private int height;

	/**
	 * Image window Constructor
	 *
	 * @param  x  top left corner position
	 * @param  y top left corner position
	 * @param  w width of the window
	 * @param  h height of the window
	 */
	public ImageWindow(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	/**
	 * Image window copy constructor
	 */
	public ImageWindow(ImageWindow n)
	{
		this(n.getX(), n.getY(), n.getWidth(), n.getHeight());
	}

	public void shiftPosition(int x, int y)
	{
		this.x += x;
		this.y += y;
	}

	public void setX(int n)
	{
		this.x = n;
	}
	public void setY(int n)
	{
		this.y = n;
	}

	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}

	public void setWidth(int n)
	{
		this.width = n;
	}
	public void setHeight(int n)
	{
		this.height = n;
	}

	public int getWidth()
	{
		return this.width;
	}
	public int getHeight()
	{
		return this.height;
	}

	public static ImageWindow
	createFullSizeImageWindowOfImage(BufferedImage img)
	{

		return new ImageWindow(0, 0, img.getWidth(), img.getHeight());
	}
}
