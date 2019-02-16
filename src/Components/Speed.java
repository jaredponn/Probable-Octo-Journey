package Components;

import poj.Component.Component;

public class Speed implements Component
{

	private float speed;

	public Speed(float n)
	{
		this.speed = n;
	}

	public float getSpeed()
	{
		return this.speed;
	}

	public void setSpeed(float n)
	{
		this.speed = n;
	}

	public void print()
	{
		System.out.println("Speed Component: speed = " + this.speed);
	}
}
