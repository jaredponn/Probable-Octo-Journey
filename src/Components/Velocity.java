package Components;

import poj.Component.Component;

public class Velocity implements Component
{

	private float vel;

	public Velocity(float n)
	{
		this.vel = n;
	}

	public float getVel()
	{
		return this.vel;
	}

	public void setVel(float n)
	{
		this.vel = n;
	}

	public void print()
	{
		System.out.println("Velocity Component");
		System.out.println("END Velocity Component");
	}
}
