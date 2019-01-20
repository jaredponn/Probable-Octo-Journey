package poj.Render;

import java.awt.image.*;
import java.awt.Color;

public class ImageRender extends RenderObject
{
	private BufferedImage img;
	private Color debugBorderColor = Color.RED;

	private ImageWindow imageWindow;

	public ImageRender(int x, int y, BufferedImage img)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(
			new ImageWindow(0, 0, img.getWidth(), img.getHeight()));
	}

	public ImageRender(int x, int y, BufferedImage img,
			   Color dbgbordercolor)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(
			new ImageWindow(0, 0, img.getWidth(), img.getHeight()));
		debugBorderColor = dbgbordercolor;
	}

	public ImageRender(int x, int y, BufferedImage img, ImageWindow imgw)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(imgw);
	}

	public ImageRender(int x, int y, BufferedImage img, ImageWindow imgw,
			   Color dbgbordercolor)
	{
		setX(x);
		setY(y);
		setImage(img);
		setImageWindow(imgw);
		debugBorderColor = dbgbordercolor;
	}

	public void setImageWindow(final ImageWindow n)
	{
		this.imageWindow = n;
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
}
