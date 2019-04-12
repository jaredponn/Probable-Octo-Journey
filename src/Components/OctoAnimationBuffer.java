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


		for (int i = 0; i < args.length; ++i) {
			buffer[i] = args[i];
		}
	}

	/**
	 * copy constructor
	 * @param o to copy
	 */
	public OctoAnimationBuffer(OctoAnimationBuffer o)
	{

		this.buffer = new Animation[OCTO_BUF_LENGTH];
		for (int i = 0; i < OCTO_BUF_LENGTH; ++i) {
			this.buffer[i] = new Animation(o.buffer[i]);
		}
	}

	/**
	 * get aniatmion
	 * @param d directoin
	 * @param Animation animation
	 */
	public Animation getAnimation(CardinalDirections d)
	{

		switch (d) {
		case N:
			return buffer[0];
		case NE:
			return buffer[1];
		case NW:
			return buffer[2];
		case S:
			return buffer[3];
		case SE:
			return buffer[4];
		case SW:
			return buffer[5];
		case W:
			return buffer[6];
		case E:
			return buffer[7];
		default:
			Logger.logMessage(
				"Java is seriously a garbage language -- error in octo animation buffer");
			return buffer[7];
		}
	}
}
