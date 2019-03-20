package Components;

import poj.linear.*;
import poj.Component.*;
import poj.Collisions.*;

/**
 * A collision object to tell mobs when to attack
 * @author Alex
 * @version 1.0
 */
public class AggroRange extends PCollisionBody
{
	/**
	 * Constructs an AggroRange object that is used to start
	 * attack animations only. Alias of the PCollisionBody type -- same
	 * functionality but different higher level type.
	 *
	 * @param  d the displacement added to the object just
	 *         before setting its position
	 * @param  pts ... the collision body
	 */
	public AggroRange(Vector2f d, Vector2f c, Vector2f... pts)
	{
		super(d, c, pts);
	}

	public AggroRange(PCollisionBody pb)
	{
		super(pb);
	}
}
