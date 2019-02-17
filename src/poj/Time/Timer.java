package poj.Time;

public class Timer
{
	private Timer()
	{
	}

	static public double getTimeInNanoSeconds()
	{
		return (double)System.nanoTime();
	}


	static public double getTimeInMilliSeconds()
	{
		return convertNanoSecondsToMilliseconds(getTimeInNanoSeconds());
	}


	static public double getTimeInSeconds()
	{
		return convertNanoSecondsToSeconds(getTimeInNanoSeconds());
	}

	static public double convertNanoSecondsToMilliseconds(double n)
	{
		return n / 1000000d;
	}
	static public double convertNanoSecondsToSeconds(double n)
	{
		return n / 1000000000d;
	}

	static public void sleepNMilliseconds(long n)
	{
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
		}
	}

	// fps is an long, so 64 frames per second wouldhave 64
	// dtms is the dt in milliseconds
	static public void dynamicSleepToFrameRate(long fps, long dtms)
	{
		final long fpms = fps;

		try {
			final long sleepDuration = Math.max(fpms - dtms, 0l);
			Thread.sleep(sleepDuration);
		} catch (InterruptedException e) {
		}
	}
}
