package Components;

import poj.Component.Component;
import poj.Render.*;
import poj.linear.Vector2f;

public class Render implements Component
{
	private ImageRenderObject graphic;
	// not really used currently -- intetion was to move the object if the
	// objet has an animation and it still needs to move to have the
	// position line up with it
	private Vector2f position_translation;

	// constructor assumes the width and the height of the image is the same
	// as model shown. Will not work for animations which need their own
	// specified width and height. To make this show only a portion of the
	// image, consider setting an ImageWindow
	public Render(ImageRenderObject a)
	{
		this.graphic = a;
		this.position_translation = new Vector2f(0, 0);
	}

	public ImageRenderObject getGraphic()
	{
		return this.graphic;
	}
	// render layer
	// load things in, give render component

	public void render(Renderer renderer)
	{
		renderer.pushRenderObject(graphic);
	}

	public void setImageWindow(ImageWindow iw)
	{
		this.graphic.setImageWindow(iw);
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
		System.out.println("Render Component");
		System.out.println();
		System.out.println("END Render Component");
	}
}
