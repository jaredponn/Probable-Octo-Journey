package Components;

/**
 * PhysicsPCollisionBody. PhysicsCollisionBody
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */
import poj.Component.*;
import poj.linear.*;
import poj.Collisions.*;

public class PhysicsPCollisionBody extends PCollisionBody
{

	/**
	 * Constructs a PhysicsPCollisionBody object that is used for collision
	 * detection ONLY. Alias of the PhysicsPCollisionBody type -- same
	 * functionality but different higher level type.
	 *
	 * @param  d the displacemnt added to the object just
	 *         before setting its position
	 * @param  pts ... the collision body
	 */

	public PhysicsPCollisionBody(Vector2f d, Vector2f c, Vector2f... pts)
	{
		super(d, c, pts);
	}

	/**
	 * copy constructor
	 * @param pb -- to copy
	 */
	public PhysicsPCollisionBody(PCollisionBody pb)
	{
		super(pb);
	}
}
