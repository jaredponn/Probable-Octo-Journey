package Components;
/**
 * PHitBox. PHitBox
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.linear.*;
import poj.Component.*;
import poj.Collisions.*;

public class PHitBox extends PCollisionBody
{
	/**
	 * Constructs a PHitBox object that is used for collision
	 * detection ONLY. Alias of the PhysicsPCollisionBody type -- same
	 * functionality but different higher level type.
	 *
	 * @param  d the displacemnt added to the object just
	 *         before setting its position
	 * @param  pts ... the collision body
	 */
	public PHitBox(Vector2f d, Vector2f... pts)
	{
		super(d, pts);
	}

	public PHitBox(PCollisionBody pb)
	{
		super(pb);
	}
}
