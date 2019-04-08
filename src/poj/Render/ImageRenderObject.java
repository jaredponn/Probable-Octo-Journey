package poj.Render;

/**
 * ImageRenderObject -- an image that is rednerable on the screen.
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared
 */
import java.awt.image.*;
import java.awt.Color;


public class ImageRenderObject extends RenderObject
{
	protected BufferedImage img;
	public final static Color DEFAULT_DEBUG_BORDER_COLOR = Color.RED;
	protected Color debugBorderColor;

	protected ImageWindow imageWindow;


	/**
	 Constructs an ImageRenderObject -- an object with a bitmap image to
	 render
	 *
	 * @param  x  top left corner position
	 * @param  y  top left corner position
	 * @param  img image to draw
	 */
	public ImageRenderObject(final int x, final int y,
				 final BufferedImage img)
	{
		this(x, y, img,
		     ImageWindow.createFullSizeImageWindowOfImage(img),
		     DEFAULT_DEBUG_BORDER_COLOR);
	}

	public ImageRenderObject(ImageRenderObject a)
	{
		this(a.x, a.y, a.img, new ImageWindow(a.getImageWindow()));
	}

	/**
	 Constructs an ImageRenderObject -- an object with a bitmap image to
	 render
	 *
	 * @param  x  top left corner position
	 * @param  y  top left corner position
	 * @param  img image to draw
	 * @param  dbgbordercolor debug border color
	 */
	public ImageRenderObject(final int x, final int y,
				 final BufferedImage img,
				 final Color dbgbordercolor)
	{
		this(x, y, img,
		     ImageWindow.createFullSizeImageWindowOfImage(img),
		     dbgbordercolor);
	}

	/**
	 Constructs an ImageRenderObject -- an object with a bitmap image to
	 render
	 *
	 * @param  x  top left corner position
	 * @param  y  top left corner position
	 * @param  img image to draw
	 * @param  imgw image window to display
	 */
	public ImageRenderObject(final int x, final int y,
				 final BufferedImage img,
				 final ImageWindow imgw)
	{

		this(x, y, img, imgw,
		     ImageRenderObject.DEFAULT_DEBUG_BORDER_COLOR);
	}

	/**
	 Constructs an ImageRenderObject -- an object with a bitmap image to
	 render
	 *
	 * @param  x  top left corner position
	 * @param  y  top left corner position
	 * @param  img image to draw
	 * @param  imgw image window to display
	 * @param  debugBorderColor debug color
	 */
	public ImageRenderObject(final int x, final int y,
				 final BufferedImage img,
				 final ImageWindow imgw,
				 final Color dbgbordercolor)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(imgw);
		debugBorderColor = dbgbordercolor;
	}

	/**
	 * Sets the image window
	 *
	 * @param  n imagewindow to set
	 * @return  this -- for composition
	 */
	public ImageRenderObject setImageWindow(final ImageWindow n)
	{
		this.imageWindow = n;
		return this;
	}

	/**
	 * Sets the image window
	 *
	 * @param  n imagewindow to set
	 * @return  this -- for composition
	 */
	final public ImageWindow getImageWindow()
	{
		return this.imageWindow;
	}

	/**
	 * Sets the image
	 *
	 * @param  n image to set
	 */
	public void setImage(final BufferedImage n)
	{
		this.img = n;
	}

	/**
	 * gets the image
	 *
	 * @return  this.img
	 */
	final public BufferedImage getImage()
	{
		return this.img;
	}

	/**
	 * sets the image
	 *
	 * @param  n debug border color
	 */
	public void setDebugBorderColor(Color n)
	{
		this.debugBorderColor = n;
	}

	/**
	 * gets the image
	 *
	 * @return  debugBorderColor
	 */
	final public Color getDebugBorderColor()
	{
		return this.debugBorderColor;
	}


	public void setTopLeftCornerPosition(int x, int y)
	{
		super.setPosition(x, y);
	}
	public int getHeight()
	{
		return this.imageWindow.getHeight();
	}

	public int getWidth()
	{
		return this.imageWindow.getWidth();
	}
}
