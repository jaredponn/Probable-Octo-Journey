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

	public void updateAnimation(double dtms)
	{
		this.animation.updateAnimationWindow(dtms);
	}
	public void setAnimation(Animation n)
	{
		this.animation = n;
	}

	public void print()
	{
		System.out.println(
			"Animation Component { top left X: "
			+ animation.getImageWindow().getX() + ", top left Y: "
			+ animation.getImageWindow().getY() + ", accTimems: "
			+ animation.getAccTimems() + ", frameDurationms: "
			+ animation.getFrameDurationms());
	}
}
