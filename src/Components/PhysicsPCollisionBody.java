package Components;
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
