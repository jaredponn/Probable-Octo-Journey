package Components;

/**
 * FacingDirection. Facing direction
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.Component.Component;
import poj.linear.*;

public class FacingDirection extends MovementDirection
{


	/**
	 * constructor
	 * @param n -- cardinal direction
	 */
	public FacingDirection(CardinalDirections n)
	{
		super(n);
	}
}
