package Components;
/**
 * PAggroRegion. Alias for PCollisionBody
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.linear.*;


public class PAggroRegion extends PhysicsPCollisionBody
{
	public PAggroRegion(Vector2f d, Vector2f c, Vector2f... pts)
	{
		super(d, c, pts);
	}
}
