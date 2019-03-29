package Components;
/**
 * Octo animation buffer.
 * Expects an argument of 8 in the ordering: N, NE, NW, S, SE, SW, W, E
 *
 * Date: March 10, 2019
 * @author Jared Pon
 * @version 1.0
 */

import poj.Animation;
import poj.Logger.*;

public class OctoAnimationBuffer
{
	Animation[] buffer;

	private final static int OCTO_BUF_LENGTH = 8;

	/**
	 * Octo animation buffer.
	 * Expects an argument of 8 in the ordering: N, NE, NW, S, SE, SW, W, E
	 *
	 * Date: March 10, 2019
	 * @author Jared Pon
	 * @version 1.0
	 */
	public OctoAnimationBuffer(Animation... args)
	{
		Logger.lassert(
			args.length != OCTO_BUF_LENGTH,
			"Error in OcotAnimationBuffer -- must have 8 animations");
		buffer = new Animation[OCTO_BUF_LENGTH];

		int i = 0;

		for (Animation a : args) {
			buffer[i] = a;
		}
	}

	public OctoAnimationBuffer(OctoAnimationBuffer o)
	{

		this.buffer = new Animation[OCTO_BUF_LENGTH];
		for (int i = 0; i < OCTO_BUF_LENGTH; ++i) {
			this.buffer[i] = new Animation(o.buffer[i]);
		}
	}
}
