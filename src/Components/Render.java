package Components;

/**
 * Render. Render Component
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */
import poj.Component.Component;
import poj.Logger.Logger;
import poj.Render.*;
import poj.linear.Vector2f;

import java.awt.image.*;

import poj.Render.ImageRenderObject;


public class Render implements Component
{
	protected ImageRenderObject graphic;
	// not really used currently -- intetion was to move the object if the
	// objet has an animation and it still needs to move to have the
	// position line up with it
	protected Vector2f position_translation;

	/**
	 * Constructor
	 * @param a -- image render object
	 */
	public Render(ImageRenderObject a)
	{
		this(a, new Vector2f(0, 0));
	}


	/**
	 * Constructor
	 * @param a -- image render object
	 * @param t -- translation vector
	 */
	public Render(ImageRenderObject a, Vector2f t)
	{
		this.graphic = a;
		this.position_translation = t;
	}


	/**
	 * Constructor.
	 * This constructor assumes the width and the height of the image, and
	 * the start of the image is in the top left corner is the same as model
	 * shown. Will not work for animations which need their own specified
	 * width and height. To make this show only a portion of the image,
	 * consider setting an ImageWindow
	 * @param a -- buffered image
	 */
	public Render(BufferedImage a)
	{
		this(a, new Vector2f(0, 0));
	}


	/**
	 * Constructor
	 * @param a -- buffered image
	 * @param t -- translation vector
	 */
	public Render(BufferedImage a, Vector2f t)
	{
		this.graphic = new ImageRenderObject(0, 0, a);
		this.position_translation = t;
	}

	/**
	 * Constructor
	 * @param a -- buffered image
	 * @param x -- x translation
	 * @param y -- y translation
	 */
	public Render(BufferedImage a, float x, float y)
	{
		this.graphic = new ImageRenderObject(0, 0, a);
		this.position_translation = new Vector2f(x, y);
	}

	/**
	 * Copy Constructor
	 * @param r -- to copy
	 */
	public Render(Render r)
	{
		this(r.getGraphic(), r.pureGetTranslation());
	}

	/**
	 * gets the graphic (image render object)
	 * @return ImageRenderObject of the render
	 */
	public ImageRenderObject getGraphic()
	{
		return this.graphic;
	}

	/**
	 * renders -- DEPRECATED
	 * @param renderer -- renderer
	 */
	public void render(Renderer renderer)
	{
		Logger.logMessage(
			"LONG DEPRECATED DO NOT USE Render::render()");
		renderer.pushRenderObject(graphic);
	}


	/**
	 * set image window
	 * @param iw -- image window to set
	 */
	public void setImageWindow(ImageWindow iw)
	{
		this.graphic.setImageWindow(iw);
	}


	/**
	 * add translation to image render object
	 */
	public void addTranslation()
	{
		int x = this.graphic.getX() + (int)position_translation.x;
		int y = this.graphic.getY() + (int)position_translation.y;

		this.graphic.setX(x);
		this.graphic.setY(y);
	}

	/**
	 * pure get translation
	 * @return copy of the tranlation vector
	 */
	public Vector2f pureGetTranslation()
	{
		return new Vector2f(this.position_translation);
	}

	/**
	 * get image window
	 * @return image window
	 */
	public ImageWindow getImageWindow()
	{
		return this.graphic.getImageWindow();
	}

	/**
	 * sets top left corner position of the image render object
	 * @param x x position
	 * @param y y position
	 */
	public void setTopLeftCornerPosition(int x, int y)
	{
		this.graphic.setTopLeftCornerPosition(x, y);
	}

	/**
	 * sets the position translation
	 * @param x x position
	 * @param y y position
	 */
	public void setTranslation(int x, int y)
	{
		position_translation.x = x;
		position_translation.y = y;
	}

	/**
	 * print
	 */
	public void print()
	{
		System.out.println("Render Component: topLeftXPosition = "
				   + graphic.getX()
				   + " topLeftYPosition = " + graphic.getY());
	}
}
