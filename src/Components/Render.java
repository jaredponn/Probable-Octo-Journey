package Components;

import poj.Component.Component;
import poj.Render.*;
import poj.linear.Vector2f;
import java.awt.image.*;
import poj.Render.ImageRenderObject;

public class Render implements Component
{
	private ImageRenderObject graphic;
	// not really used currently -- intetion was to move the object if the
	// objet has an animation and it still needs to move to have the
	// position line up with it
	private Vector2f position_translation;

	public Render(ImageRenderObject a)
	{
		this(a, new Vector2f(0, 0));
	}


	public Render(ImageRenderObject a, Vector2f t)
	{
		this.graphic = a;
		this.position_translation = t;
	}

	// constructor assumes the width and the height of the image, and the
	// start of the image is in the top left corner is the same as model
	// shown. Will not work for animations which need their own specified
	// width and height. To make this show only a portion of the image,
	// consider setting an ImageWindow
	public Render(BufferedImage a)
	{
		this(a, new Vector2f(0, 0));
	}

	public Render(BufferedImage a, Vector2f t)
	{
		this.graphic = new ImageRenderObject(0, 0, a);
		this.position_translation = t;
	}

	public ImageRenderObject getGraphic()
	{
		return this.graphic;
	}

	public void render(Renderer renderer)
	{
		renderer.pushRenderObject(graphic);
	}

	public void setImageWindow(ImageWindow iw)
	{
		this.graphic.setImageWindow(iw);
	}

	public void addTranslation()
	{
		int x = this.graphic.getX() + (int)position_translation.x;
		int y = this.graphic.getY() + (int)position_translation.y;

		this.graphic.setX(x);
		this.graphic.setY(y);
	}

	public ImageWindow getImageWindow()
	{
		return this.graphic.getImageWindow();
	}

	public void setTopLeftCornerPosition(int x, int y)
	{
		this.graphic.setTopLeftCornerPosition(x, y);
	}

	public void print()
	{
		System.out.println("Render Component: topLeftXPosition = "
				   + graphic.getX()
				   + " topLeftYPosition = " + graphic.getY());
	}
}
