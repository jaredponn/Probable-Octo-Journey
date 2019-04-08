package EntitySets;

import java.awt.image.BufferedImage;

import Components.PCollisionBody;
import Components.Render;

import poj.EntitySet.EntitySet;
import poj.Render.ImageRenderObject;
import poj.linear.Vector2f;
public class MenuButton extends EntitySet
{

	public MenuButton(int windowWidth, int windowHeight,
			  float buttonWidthRatio, float buttonHeightRatio,
			  float buttonSizeWidthRatio,
			  float buttonSizeHeightRatio, BufferedImage image)
	{
		super();
		// clang-format off
			addComponent(new PCollisionBody(new Vector2f(0f, 0f), // displacement
			new Vector2f(0f, 0f), // center, dont matter for menu
			new Vector2f((int)(((float)windowWidth)/ buttonWidthRatio), (int)(((float)windowHeight) / buttonHeightRatio)),
			new Vector2f((int)(((float)windowWidth)/ buttonWidthRatio + ((float)windowWidth)/ buttonSizeWidthRatio), (int)(((float)windowHeight) / buttonHeightRatio)),
			new Vector2f((int)(((float)windowWidth)/ buttonWidthRatio) ,(int)( ((float)windowHeight) / buttonHeightRatio + ((float)windowHeight)/ buttonSizeHeightRatio)),
			new Vector2f((int)(((float)windowWidth)/ buttonWidthRatio + ((float)windowWidth)/ buttonSizeWidthRatio), (int)(((float)windowHeight) / buttonHeightRatio+ ((float)windowHeight)/ buttonSizeHeightRatio))
			));

			addComponent(new Render(new ImageRenderObject((int)(((float)windowWidth)/ buttonWidthRatio), (int)(((float)windowHeight) / buttonHeightRatio) , image)));
		// clang-format on
	}
	public void print()
	{
	}
}
