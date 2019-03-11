package Components;

import poj.linear.*;


/**
 * Alias for PCollisionBody -- a regions of collisions for the turret
 */
public class TargetRegion extends PCollisionBody
{
	public TargetRegion(Vector2f d, Vector2f... pts)
	{
		super(d, pts);
	}
}
