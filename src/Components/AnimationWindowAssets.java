package Components;

import poj.Component.Component;
import poj.Animation;

import java.util.ArrayList;
import poj.Pair;

public class AnimationWindowAssets implements Component
{
	// change to use ocotanimation buffer
	private static final int DEFAULT_ASSET_BUFFER_SIZE = 100;
	Animation assets[];

	public AnimationWindowAssets(ArrayList<Pair<Animation, Integer>> arr)
	{
		assets = new Animation[DEFAULT_ASSET_BUFFER_SIZE];

		for (Pair<Animation, Integer> p : arr) {
			assets[p.snd()] = p.fst();
		}
	}

	public AnimationWindowAssets(AnimationWindowAssets a)
	{
		assets = new Animation[DEFAULT_ASSET_BUFFER_SIZE];

		for (int i = 0; i < a.assets.length; ++i) {
			if (a.unsafeGetAnimation(i) == null)
				continue;

			assets[i] = new Animation(a.unsafeGetAnimation(i));
		}
	}

	public Animation unsafeGetAnimation(int i)
	{
		return assets[i];
	}

	public Animation getAnimation(int i)
	{
		return unsafeGetAnimation(i);
	}

	public void print()
	{
		System.out.println("AnimationWindowAssets");
	}
}
