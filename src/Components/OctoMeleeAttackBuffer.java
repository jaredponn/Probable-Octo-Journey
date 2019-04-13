package Components;
/**
 * Octo melee buffer.
 *
 * Date: March 10, 2019
 * @author Jared Pon
 * @version 1.0
 */

import poj.Logger.*;
import poj.Component.Component;

public class OctoMeleeAttackBuffer implements Component
{

	PCollisionBody[] buffer;

	private final static int OCTO_BUF_LENGTH = 8;

	/**
	 * Constructor
	 * @param args -- p collision bodies
	 */
	public OctoMeleeAttackBuffer(PCollisionBody... args)
	{
		Logger.lassert(args.length != OCTO_BUF_LENGTH,
			       "Error in octo length buf");
		buffer = new PCollisionBody[OCTO_BUF_LENGTH];


		for (int i = 0; i < args.length; ++i) {
			buffer[i] = args[i];
		}
	}


	/**
	 * copy constructor
	 * @param o -- toCopy OctoMeleeAttackBuffer
	 */
	public OctoMeleeAttackBuffer(OctoMeleeAttackBuffer o)
	{

		this.buffer = new PCollisionBody[OCTO_BUF_LENGTH];
		for (int i = 0; i < OCTO_BUF_LENGTH; ++i) {
			this.buffer[i] = new PCollisionBody(o.buffer[i]);
		}
	}


	/**
	 * get the p collision body from the direction
	 * @param d direction
	 */
	public PCollisionBody getPCollisionBody(CardinalDirections d)
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


	/**
	 * print
	 */
	public void print()
	{
	}
}
