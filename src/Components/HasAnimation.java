package Components;
/**
 * HasAnimation. Animation component
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.Animation;
import poj.Render.ImageWindow;
import poj.Component.Component;

public class HasAnimation implements Component
{
	Animation animation;

	/**
	 * Constructor with defined animation
	 * @param n: the animation for this entity
	 */
	public HasAnimation(Animation n)
	{
		this.animation = n;
	}

	/**
	 * gets the image window for this animation
	 * @return
	 */
	public ImageWindow getImageWindow()
	{
		return this.animation.getImageWindow();
	}

	/**
	 * updates this animation
	 * @param dtms
	 */
	public void updateAnimation(double dtms)
	{
		this.animation.updateAnimationWindow(dtms);
	}
	/**
	 * changes this animation to a different animation
	 * @param n
	 */
	public void setAnimation(Animation n)
	{
		this.animation = n;
	}

	/**
	 * prints info about this animation to console
	 */
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
