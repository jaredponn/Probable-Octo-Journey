package Components;
import poj.linear.*;
import poj.Component.*;
import poj.Collisions.*;

public class PHitBox extends PCollisionBody
{
	/**
	 * Constructs a PHitBox object that is used for collision
	 * detection ONLY
	 *
	 * @param  d the displacemnt added to the object just
	 *         before setting its position
	 * @param  pts ... the collision body
	 */
	public PHitBox(Vector2f d, Vector2f... pts)
	{
		super(d, pts);
	}
}
