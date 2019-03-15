package Components;

import poj.Component.Component;

public class DespawnTimer implements Component
{
	private double timeLeft;

	public DespawnTimer(double t)
	{
		timeLeft = t;
	}

	public void decrementTimerBy(double t)
	{
		timeLeft -= t;
	}

	public boolean isOutOfTime()
	{
		return timeLeft <= 0;
	}


	public void print()
	{
		System.out.println("DespawnTimer Component: timeLeft: "
				   + timeLeft);
	}
}
