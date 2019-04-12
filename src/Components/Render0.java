package Components;

import poj.Component.Component;
import poj.Logger.Logger;
import poj.Render.*;
import poj.linear.Vector2f;

import java.awt.image.*;

import poj.Render.ImageRenderObject;

// another render layer
public class Render0 extends Render
{

	public Render0(ImageRenderObject a)
	{
		super(a);
	}


	public Render0(ImageRenderObject a, Vector2f t)
	{
		super(a, t);
	}

	// constructor assumes the width and the height of the image, and the
	// start of the image is in the top left corner is the same as model
	// shown. Will not work for animations which need their own specified
	// width and height. To make this show only a portion of the image,
	// consider setting an ImageWindow
	public Render0(BufferedImage a)
	{
		super(a, new Vector2f(0, 0));
	}

	public Render0(BufferedImage a, Vector2f t)
	{
		super(new ImageRenderObject(0, 0, a), t);
	}
}
