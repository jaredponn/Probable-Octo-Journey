package Game;

/**
 * CardinalDirectionAnimationBuffer. DEPREACTED AND UNUSED
 * Date: February 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.Animation;
import Components.CardinalDirections;
import poj.Logger.*;

import java.util.ArrayList;

public class CardinalDirectionAnimationBuffer
{
	private ArrayList<Animation> animations;

	// expects it to be in the format of N, NE, E, SE, S, SW, W, NW
	public CardinalDirectionAnimationBuffer(Animation... as)
	{
		Logger.lassert(
			as.length == 8,
			"ERROR in CardinalDirectionAnimationBuffer. Exactly 8 animations needs tobe passed in -- one for each cardinal direction. Please ensure that you are doing this.");

		this.animations = new ArrayList<Animation>(8);

		for (Animation i : as) {
			this.animations.add(new Animation(i));
		}
	}

	public ArrayList<Animation> getAnimations()
	{
		return animations;
	}

	public Animation getAnimationFromCardinalDirection(CardinalDirections d)
	{
		switch (d) {
		case N:
			return animations.get(0);
		case NE:
			return animations.get(1);
		case E:
			return animations.get(2);
		case SE:
			return animations.get(3);
		case S:
			return animations.get(4);
		case SW:
			return animations.get(5);
		case W:
			return animations.get(6);
		case NW:
			return animations.get(7);
		default:
			Logger.logMessage(
				LogLevels.VERBOSE,
				"MINOR error in getAnimationFromCardinalDirection -- an invalid direction has been put in. System should continue to be runing normally.");
			return animations.get(0);
		}
	}
}
