package Components;

import poj.Component.Component;

/**
 * A component for entities that despawn after a
 * certain amount of game time
 * @author Jared
 */
public class DespawnTimer implements Component
{
	private double timeLeft;

	/**
	 * Constructor with defined lifespan
	 * @param t: time in ms
	 */
	public DespawnTimer(double t)
	{
		timeLeft = t;
	}


	/**
	 * decrease time remaining
	 * @param t: time in ms
	 */
	public void decrementTimerBy(double t)
	{
		timeLeft -= t;
	}

	/**
	 * check if entity is out of time
	 * @return: true if time left < 0
	 */
	public boolean isOutOfTime()
	{
		return timeLeft <= 0;
	}


	/**
	 * Prints the current time remaining to console
	 */
	public void print()
	{
		System.out.println("DespawnTimer Component: timeLeft: "
				   + timeLeft);
	}
}
