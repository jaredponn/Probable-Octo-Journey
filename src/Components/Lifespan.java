package Components;

import poj.Component.Component;
import Game.PlayGame;
import poj.EngineState;

/**
 * A component for telling how long an entity can survive in the game
 * @author Alex Stark
 * @version 1.0 - 03/10/19
 */
public class Lifespan implements Component
{

	private double lifespan;
	private double spawnTime;

	/**
	 * initialize the lifespan of this entity
	 * @param totalTime: how long this entity can be spawned for
	 * @param initialTime: the time that this entity spawned
	 */
	public Lifespan(double totalTime, double initialTime)
	{
		this.lifespan = totalTime;
		this.spawnTime = initialTime;
	}

	/**
	 * gets the total amount of time this entity can be spawned for
	 * @return how long this entity can be spawned for
	 */
	public double getLifespan()
	{
		return this.lifespan;
	}

	/**
	 * get the time that this entity spawned at
	 * @return the time that this entity spawned at
	 */
	public double getSpawnTime()
	{
		return this.spawnTime;
	}
	
	/**
	 * check to see if this entity has exceeded its lifespan
	 * and de-spawn it if it has
	 * @param g: the main game state
	 * @param focus: the EngineState index of this entity
	 */
	public void checkLifeSpan(PlayGame g , int index) {
		EngineState e = g.getEngineState();
		if(g.getPlayTime() > spawnTime + lifespan ) {
			e.deleteAllComponentsAt(index);
			e.markIndexAsFree(index);
		}
	}

	/**
	 * prints the total amount of time that this entity can be spawned for
	 */
	public void print()
	{
		System.out.println("Lifespan is " + this.lifespan);
	}
}
