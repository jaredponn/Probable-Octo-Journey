package Components;

import poj.Component.Component;
import Game.PlayGame;
import poj.EngineState;

/**
 * A component for telling how long an entity has existed
 * @author Alex Stark
 * @version 1.0 - 03/10/19
 */
public class Lifespan implements Component
{

	private double accTime;

	/**
	 * initialize the lifespan of this entity
	 */
	public Lifespan()
	{
	}


	/**
	 * gets the total amount of time this entity can be spawned for
	 * @return how long this entity can be spawned for
	 */
	public void addAccTime(double n)
	{
		this.accTime += n;
	}

	/**
	 * gets the total amount of time this entity can be spawned for
	 * @return how long this entity can be spawned for
	 */
	public double getAccTime()
	{
		return this.accTime;
	}

	/**
	 * prints the total amount of time that this entity can be spawned for
	 */
	public void print()
	{
		System.out.println("Lifespan Component: accTime: "
				   + this.accTime);
	}
}
