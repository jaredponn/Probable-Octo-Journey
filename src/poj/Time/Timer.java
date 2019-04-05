package poj.Time;

import poj.Logger.Logger;

/**
 *  Timer -- a way to get the time.
 * Date: February 20, 2019
 * @author      Jared
 * @version      1.0
 */

public class Timer
{

	private static double START_BENCH;
	private static double END_BENCH;

	private static double ACC_BENCH;
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


	static public void START_BENCH()
	{
		START_BENCH = getTimeInNanoSeconds();
	}

	static public void END_BENCH()
	{
		END_BENCH = getTimeInNanoSeconds();
		ACC_BENCH += BENCH_DELTA();
	}

	static public void CLEAR_ACC_BENCH()
	{
		ACC_BENCH = 0;
	}

	static private double BENCH_DELTA()
	{
		return END_BENCH - START_BENCH;
	}

	static public void LOG_BENCH_ACC()
	{
		Logger.logMessage("BENCH"
				  + ": " + ACC_BENCH);
	}

	static public void LOG_BENCH_DELTA()
	{
		LOG_BENCH_DELTA("BENCH");
	}

	static public void LOG_BENCH_DELTA(String str)
	{
		Logger.logMessage(str + ": " + BENCH_DELTA());
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
