package poj.Time;

public class Timer
{
	private Timer()
	{
	}

	static public long getTimeInNanoSeconds()
	{
		return System.nanoTime();
	}


	static public long getTimeInMilliSeconds()
	{
		return convertNanoSecondsToMilliseconds(System.nanoTime());
	}

	static public long convertNanoSecondsToMilliseconds(long n)
	{
		return n / 1000000;
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
			Thread.sleep(Math.max(fpms - dtms, 0l));
		} catch (InterruptedException e) {
		}
	}
}
