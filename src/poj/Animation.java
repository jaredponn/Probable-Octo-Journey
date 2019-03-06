package poj;
/**
 * Animation -- for maintaining the state of the image window upon an image.
 * Date: February 10, 2019
 * @author  Jared, and parts of the code from the following sources:
 * https://jaredponn.github.io/posts/2018-06-07-Write-Me-A-FlappyBird-In-Haskell.html
 * @version      1.0
 */
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

	/**
	 * Constructs an animation object
	 *
	 * @param  w image window
	 * @param  fdms frameduration in ms
	 * @param  xstride stride in x direction
	 * @param  ystride stride in y direction
	 * @param  xmin minimum x value
	 * @param  ymin minimum y value
	 * @param  xmax max x value
	 * @param  xmax max x value
	 * @return      void
	 */
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


	/**
	 * Constructs an animation object
	 *
	 * @param  width width of image window
	 * @param  height height of image window
	 * @param  fdms frameduration in ms
	 * @param  xstride stride in x direction
	 * @param  ystride stride in y direction
	 * @param  xmin minimum x value
	 * @param  ymin minimum y value
	 * @param  xmax max x value
	 * @param  xmax max x value
	 * @return      void
	 */
	public Animation(int width, int height, double fdms, int xstride,
			 int ystride, int xmin, int ymin, int xmax, int ymax)
	{
		this(new ImageWindow(xmin, ymin, width, height), fdms, xstride,
		     ystride, xmin, ymin, xmax, ymax);
	}


	/**
	 * Copy constructor
	 * @param  a animation object
	 * @return      void
	 */
	public Animation(Animation a)
	{
		this(new ImageWindow(a.getImageWindow()),
		     a.getFrameDurationms(), a.getXStride(), a.getYStride(),
		     a.getXMin(), a.getYMin(), a.getXMax(), a.getYMax());
	}


	/**
	 * updates the animation window
	 * @param  xms duration of ms that has passed
	 * @return      void
	 */
	public void updateAnimationWindow(double xms)
	{
		addToAccTime(xms);

		if (this.accTimems >= this.frameDurationms) {
			slideImageWindow();
			this.accTimems = 0;
		}
	}


	/**
	 * gets acctime in ms
	 * @param
	 * @return      acctime
	 */
	public double getAccTimems()
	{
		return this.accTimems;
	}

	/**
	 * get the frame duration in ms
	 * @param
	 * @return      frameDuration
	 */
	public double getFrameDurationms()
	{
		return this.frameDurationms;
	}


	/**
	 * returns a pointer to the currently focused image window
	 * @return      focused window
	 */
	final public ImageWindow getImageWindow()
	{
		return this.focusedWindow;
	}


	/**
	 * adds time to the acctime. The value should be positive
	 * @param  xms duration of ms to increase it by
	 * @return      void
	 */
	private void addToAccTime(double xms)
	{
		this.accTimems += xms;
	}

	/**
	 * slides the image window. Will reset the window window iff the current
	 * focus is outside of the max (exclusive)
	 * @return      void
	 */
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


	/**
	 * gets the XStride
	 * @return      xstride
	 */
	public int getXStride()
	{
		return this.xstride;
	}

	/**
	 * gets the YStride
	 * @return      ystride
	 */
	public int getYStride()
	{

		return this.ystride;
	}


	/**
	 * gets the xmin
	 * @return      xmin
	 */
	public int getXMin()
	{
		return this.xmin;
	}

	/**
	 * gets the ymin
	 * @return      ymin
	 */
	public int getYMin()
	{
		return this.ymin;
	}

	/**
	 * gets the xmax
	 * @return      xmax
	 */
	public int getXMax()
	{
		return this.xmax;
	}

	/**
	 * gets the ymax
	 * @return      ymax
	 */
	public int getYMax()
	{
		return this.ymax;
	}
}
