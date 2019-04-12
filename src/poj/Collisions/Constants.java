package poj.Collisions;

/**
 * Costants for the collision system.
 *
 * date March 10, 2019
 * @author Jared Pon
 * @version 1.0
 */

import poj.linear.Vector2f;

public class Constants
{
	protected final static Vector2f N = new Vector2f(0, 1);
	protected final static Vector2f S = new Vector2f(0, -1);
	protected final static Vector2f E = new Vector2f(1, 0);
	protected final static Vector2f W = new Vector2f(-1, 0);

	protected final static Vector2f TOP_RIGHT_DIR = new Vector2f(1, 1);
	protected final static Vector2f BOTTOM_LEFT_DIR = new Vector2f(-1, -1);
}
