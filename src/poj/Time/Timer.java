package poj.Time;

/**
 *  Timer -- a way to get the time.
 * Date: February 20, 2019
 * @author      Jared
 * @version      1.0
 */

public class Timer
{
	private Timer()
	{
	}

	/**
	 *  gets the time in nano seconds
	 *
	 * @return      Time in nano seconds
	 */
	static public double getTimeInNanoSeconds()
	{
		return (double)System.nanoTime();
	}


	/**
	 *  gets the time in milliseconds seconds
	 *
	 * @return      Time in milliseconds seconds
	 */
	static public double getTimeInMilliSeconds()
	{
		return convertNanoSecondsToMilliseconds(getTimeInNanoSeconds());
	}


	/**
	 *  gets the time in seconds
	 *
	 * @return      Time in seconds
	 */
	static public double getTimeInSeconds()
	{
		return convertNanoSecondsToSeconds(getTimeInNanoSeconds());
	}

	/**
	 *  converts time from nano to milli
	 *
	 * @param n time in nano seconds
	 * @return      in milliseconds
	 */
	static public double convertNanoSecondsToMilliseconds(double n)
	{
		return n / 1000000d;
	}

	/**
	 *  converts time from nano to seconds
	 *
	 * @param n time in nano seconds
	 * @return      in seconds
	 */
	static public double convertNanoSecondsToSeconds(double n)
	{
		return n / 1000000000d;
	}

	/**
	 *  sleepsthe thread n milliseconds
	 *
	 * @param n time in milliseconds seconds
	 */
	static public void sleepNMilliseconds(long n)
	{
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Sleeps game for the game thread. E.g. fps is an long, so 64 frames
	 * per second wouldhave 64 dtms is the dt in milliseconds.  DEPRECTAED
	 * @param fps frames per second
	 * @param dtms current delta time in ms
	 */
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
