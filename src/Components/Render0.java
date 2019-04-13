package Components;
/**
 * Render0 -- another render component for more render layers.
 * Date: March 10, 2019
 * @author Jared Pon
 * @version v1.0
 */

import poj.Component.Component;
import poj.Logger.Logger;
import poj.Render.*;
import poj.linear.Vector2f;

import java.awt.image.*;

import poj.Render.ImageRenderObject;

public class Render0 extends Render
{


	/**
	 * Constructor
	 * @param a -- image render object
	 */
	public Render0(ImageRenderObject a)
	{
		super(a);
	}


	/**
	 * Constructor
	 * @param a -- image render object
	 * @param t -- translation vector
	 */
	public Render0(ImageRenderObject a, Vector2f t)
	{
		super(a, t);
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
	public Render0(BufferedImage a)
	{
		super(a, new Vector2f(0, 0));
	}

	/**
	 * Constructor
	 * @param a -- buffered image
	 * @param t -- translation vector
	 */
	public Render0(BufferedImage a, Vector2f t)
	{
		super(new ImageRenderObject(0, 0, a), t);
	}
}
