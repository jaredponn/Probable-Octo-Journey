package Components;

import poj.Animation;
import poj.Render.ImageWindow;
import poj.Component.Component;

public class HasAnimation implements Component
{
	Animation animation;

	public HasAnimation(Animation n)
	{
		this.animation = n;
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
