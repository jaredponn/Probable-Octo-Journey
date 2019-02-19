package poj;

import poj.Render.ImageWindow;

public class Animation
{
	private ImageWindow focusedWindow;

	private double accTimems;

	private double frameDurationms;
	private int xstride;
	private int ystride;

	// initial value of the top left corner position (inclusive)
	private int xmin;
	private int ymin;

	// max value of the top left corner position (inclusive)
	private int xmax;
	private int ymax;

	public Animation(ImageWindow w, double fdms, int xstride, int ystride,
			 int xmin, int ymin, int xmax, int ymax)
	{
		this.focusedWindow = w;
		this.frameDurationms = fdms;
		this.xstride = xstride;
		this.ystride = ystride;
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}


	public Animation(int width, int height, double fdms, int xstride,
			 int ystride, int xmin, int ymin, int xmax, int ymax)
	{
		this(new ImageWindow(xmin, ymin, width, height), fdms, xstride,
		     ystride, xmin, ymin, xmax, ymax);
	}

	public Animation(Animation a)
	{
		this(new ImageWindow(a.getImageWindow()),
		     a.getFrameDurationms(), a.getXStride(), a.getYStride(),
		     a.getXMin(), a.getYMin(), a.getXMax(), a.getYMax());
	}

	public void updateAnimationWindow(double xms)
	{
		addToAccTime(xms);

		if (this.accTimems >= this.frameDurationms) {
			slideImageWindow();
			this.accTimems = 0;
		}
	}

	public double getAccTimems()
	{
		return this.accTimems;
	}

	public double getFrameDurationms()
	{
		return this.frameDurationms;
	}

	final public ImageWindow getImageWindow()
	{
		return this.focusedWindow;
	}

	private void addToAccTime(double xms)
	{
		this.accTimems += xms;
	}

	// will reset the image window iff the current focus is outside of the
	// max (exclusive)
	private void slideImageWindow()
	{
		if (this.focusedWindow.getX() + xstride >= this.xmax
		    && this.focusedWindow.getY() + ystride >= this.ymax) {
			this.focusedWindow.setX(xmin);
			this.focusedWindow.setY(ymin);
			return;
		} else {
			this.focusedWindow.setX(focusedWindow.getX() + xstride);
			this.focusedWindow.setY(focusedWindow.getY() + ystride);
		}
	}

	public int getXStride()
	{
		return this.xstride;
	}
	public int getYStride()
	{

		return this.ystride;
	}

	public int getXMin()
	{
		return this.xmin;
	}
	public int getYMin()
	{
		return this.ymin;
	}

	public int getXMax()
	{
		return this.xmax;
	}
	public int getYMax()
	{
		return this.ymax;
	}
}
