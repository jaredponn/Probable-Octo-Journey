package Components;

import poj.Animation;
import poj.Render.ImageWindow;
import poj.Component.Component;

public class HasAnimation implements Component
{
	Animation animation;

	public HasAnimation(ImageWindow w, long fdms, int xstride, int ystride,
			    int xmin, int ymin, int xmax, int ymax)
	{
		this.animation = new Animation(w, fdms, xstride, ystride, xmin,
					       ymin, xmax, ymax);
	}

	public ImageWindow getImageWindow()
	{
		return this.animation.getImageWindow();
	}

	public void updateAnimation(long dtms)
	{
		this.animation.updateAnimationWindow(dtms);
	}

	public void print()
	{
		System.out.println("Animation Component");
		System.out.println();
		System.out.println("END Animation Component");
	}
}
