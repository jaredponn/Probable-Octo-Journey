package Components;

import poj.Component.Component;
import poj.Animation;

import java.util.ArrayList;
import poj.Pair;

public class AnimationWindowAssets implements Component
{
	// change to use ocotanimation buffer
	private static final int DEFAULT_ASSET_BUFFER_SIZE = 100;
	OctoAnimationBuffer assets[];

	public AnimationWindowAssets(
		ArrayList<Pair<OctoAnimationBuffer, Integer>> arr)
	{
		assets = new OctoAnimationBuffer[DEFAULT_ASSET_BUFFER_SIZE];

		for (Pair<OctoAnimationBuffer, Integer> p : arr) {
			assets[p.snd()] = p.fst();
		}
	}

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
	protected OctoAnimationBuffer getOctoAnimationBufferAt(int i)
	{
		return assets[i];
	}

	public Animation unsafeGetAnimation(CardinalDirections d, int i)
	{
		return assets[i].getAnimation(d);
	}

	public Animation getAnimation(CardinalDirections d, int i)
	{
		return unsafeGetAnimation(d, i);
	}

	public void print()
	{
		System.out.println("AnimationWindowAssets");
	}
}
