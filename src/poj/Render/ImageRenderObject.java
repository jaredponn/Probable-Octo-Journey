package poj.Render;

/**
 * ImageRenderObject -- an image that is rednerable on the screen.
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared
 */
import java.awt.image.*;
import java.awt.Color;

import java.awt.image.RescaleOp;

public class ImageRenderObject extends RenderObject
{
	private BufferedImage img;
	public final static Color DEFAULT_DEBUG_BORDER_COLOR = Color.RED;
	private Color debugBorderColor;

	private ImageWindow imageWindow;

	private RescaleOp rescaleOp;

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

		setDefaultRGBAScaleFactors();
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

		setDefaultRGBAScaleFactors();
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
		setDefaultRGBAScaleFactors();
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

		setDefaultRGBAScaleFactors();
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

	/**
	 * gets the Rescale op
	 *
	 * @return  debugBorderColor
	 */
	final public RescaleOp getRescaleOp()
	{
		return this.rescaleOp;
	}

	// returns array of float of: {r, g, b, a}
	final public float[] getRGBAScaleFactors()
	{
		// why does java have C like io parameters.
		float[] tmp = new float[4];
		return this.rescaleOp.getScaleFactors(tmp);
	}

	public ImageRenderObject setRGBAScaleFactors(float r, float g, float b,
						     float a)
	{
		rescaleOp =
			new RescaleOp(new float[] {r, g, b, a},
				      new float[] {0.f, 0.f, 0.f, 0.f}, null);

		return this;
	}

	public ImageRenderObject setRGBScaleFactors(float r, float g, float b)
	{
		rescaleOp =
			new RescaleOp(new float[] {r, g, b, 1.f},
				      new float[] {0.f, 0.f, 0.f, 0.f}, null);

		return this;
	}

	// sets to be a darker shade. 0 < x < 1 = darker image
	// sets to be a darker shade. 1 < x     = brighter image
	public ImageRenderObject setImageShade(float n)
	{
		setRGBScaleFactors(n, n, n);
		return this;
	}

	public ImageRenderObject setDefaultRGBAScaleFactors()
	{
		setRGBAScaleFactors(1.f, 1.f, 1.f, 1.f);
		return this;
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
