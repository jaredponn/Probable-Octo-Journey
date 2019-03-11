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

	public PhysicsPCollisionBody(Vector2f d, Vector2f... pts)
	{
		super(d, pts);
	}

	public PhysicsPCollisionBody(PCollisionBody pb)
	{
		super(pb);
	}
}
