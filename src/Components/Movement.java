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

	public Movement(float n)
	{
		this.speed = n;
		this.vel = new Vector2f(0f, 0f);
	}

	public float getSpeed()
	{
		return this.speed;
	}

	public void setSpeed(float n)
	{
		this.speed = n;
	}

	public Vector2f getVelocity()
	{
		return this.vel;
	}

	public Vector2f getDistanceDelta(double dt)
	{
		return this.vel.pureMul((float)dt);
	}

	public void setVelocity(Vector2f n)
	{
		this.vel = n;
	}

	public void print()
	{
		System.out.println("Speed Component: speed = " + this.speed
				   + " vel ( x = " + vel.x + " , y = " + vel.y);
	}
}
