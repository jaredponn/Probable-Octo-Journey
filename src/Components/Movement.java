package Components;

/**
 * Movement. MovementComponent with speed and velocity(mutuable)
 *
 * Date: March 10, 2019
 * @author Jared
 * @version 1.0
 */

import poj.Component.Component;
import poj.linear.Vector2f;

public class Movement implements Component
{

	private float speed;
	private Vector2f vel;

	/**
	 * Set speed and create a new velocity vector
	 * @param n: the speed to set this entity to
	 */
	public Movement(float n)
	{
		this.speed = n;
		this.vel = new Vector2f(0f, 0f);
	}

	/**
	 * Get the current speed of this entity
	 * @return the current speed as a float
	 */
	public float getSpeed()
	{
		return this.speed;
	}

	/**
	 * Set the speed of this entity
	 * @param n: the float to set the speed to
	 */
	public void setSpeed(float n)
	{
		this.speed = n;
	}

	/**
	 * Get the current velocity vector of this entity
	 * @return the current velocity vector
	 */
	public Vector2f getVelocity()
	{
		return this.vel;
	}

	public Vector2f getDistanceDelta(double dt)
	{
		return this.vel.pureMul((float)dt);
	}

	/**
	 * Set the velocity vector from another vector
	 * @param n: the vector to copy
	 */
	public void setVelocity(Vector2f n)
	{
		setVelocity(n.x, n.y);
	}

	/**
	 * Set the velocity from a pair of floats
	 */
	public void setVelocity(float x, float y)
	{
		this.vel.x = x;
		this.vel.y = y;
	}

	/**
	 * Print the current components of this vector to console
	 */
	public void print()
	{
		System.out.println("Speed Component: speed = " + this.speed
				   + " vel ( x = " + vel.x + " , y = " + vel.y);
	}
}
