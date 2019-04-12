package Components;

/**
 * AnimationWindowAssets
 * @author Jared Pon
 * March 1, 2019
 * v 0.0
 */

import poj.Component.Component;
import poj.Animation;

import java.util.ArrayList;
import poj.Pair;

public class AnimationWindowAssets implements Component
{
	private static final int DEFAULT_ASSET_BUFFER_SIZE = 100;
	OctoAnimationBuffer assets[];

	/**
	 * constructor
	 * @param arr : pair of octoanimations and ints (ints are the flags of
	 *         the old animation getter)
	 */
	public AnimationWindowAssets(Pair<OctoAnimationBuffer, Integer>... arr)
	{
		assets = new OctoAnimationBuffer[DEFAULT_ASSET_BUFFER_SIZE];

		for (Pair<OctoAnimationBuffer, Integer> p : arr) {
			assets[p.snd()] = p.fst();
		}
	}

	/**
	 * copy constructor
	 * @param a : AnimationWindowAssets
	 */
	public AnimationWindowAssets(AnimationWindowAssets a)
	{
		assets = new OctoAnimationBuffer[DEFAULT_ASSET_BUFFER_SIZE];

		for (int i = 0; i < a.assets.length; ++i) {
			if (a.getOctoAnimationBufferAt(i) == null)
				continue;

			assets[i] = new OctoAnimationBuffer(
				a.getOctoAnimationBufferAt(i));
		}
	}

	/**
	 * get OctoAnimationBuffer
	 * @param i :index (corrosponding to the one in the constructor)
	 * @return octo animation buffer
	 */
	protected OctoAnimationBuffer getOctoAnimationBufferAt(int i)
	{
		return assets[i];
	}

	/**
	 * unsafe get OctoAnimationBuffer
	 * @param i :index (corrosponding to the one in the constructor)
	 * @return octo animation buffer
	 */
	public Animation unsafeGetAnimation(CardinalDirections d, int i)
	{
		return getOctoAnimationBufferAt(i).getAnimation(d);
	}

	/**
	 * get OctoAnimationBuffer
	 * @param i :index (corrosponding to the one in the constructor)
	 * @return octo animation buffer
	 */
	public Animation getAnimation(CardinalDirections d, int i)
	{
		return unsafeGetAnimation(d, i);
	}

	/**
	 * print
	 */
	public void print()
	{
		System.out.println("AnimationWindowAssets");
	}
}
