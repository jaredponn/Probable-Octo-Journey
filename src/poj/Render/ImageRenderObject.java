package poj.Render;

import java.awt.image.*;
import java.awt.Color;

import java.awt.image.RescaleOp;

public class ImageRenderObject extends RenderObject
{
	private BufferedImage img;
	private Color debugBorderColor = Color.RED;

	private ImageWindow imageWindow;

	private RescaleOp rescaleOp;

	// x, y -- top left corner position
	// img -> image
	public ImageRenderObject(final int x, final int y,
				 final BufferedImage img)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(
			new ImageWindow(0, 0, img.getWidth(), img.getHeight()));

		setDefaultRGBAScaleFactors();
	}

	public ImageRenderObject(final int x, final int y,
				 final BufferedImage img,
				 final Color dbgbordercolor)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(
			new ImageWindow(0, 0, img.getWidth(), img.getHeight()));
		debugBorderColor = dbgbordercolor;

		setDefaultRGBAScaleFactors();
	}

	public ImageRenderObject(final int x, final int y,
				 final BufferedImage img,
				 final ImageWindow imgw)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(imgw);

		setDefaultRGBAScaleFactors();
	}

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

	public ImageRenderObject setImageWindow(final ImageWindow n)
	{
		this.imageWindow = n;
		return this;
	}

	final public ImageWindow getImageWindow()
	{
		return this.imageWindow;
	}

	public void setImage(final BufferedImage n)
	{
		this.img = n;
	}

	final public BufferedImage getImage()
	{
		return this.img;
	}

	public void setDebugBorderColor(Color n)
	{
		this.debugBorderColor = n;
	}

	final public Color getDebugBorderColor()
	{
		return this.debugBorderColor;
	}

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
}
