package poj.Logger;

/**
 * Logger -- wrapper around Java's IO system for logging.
 * Date: February 20, 2019
 * @author: Jared
 * @version: 1.0
 */

class LOG_LEVEL_CAST
{
	/**
	 * Casts log levels to an int
	 *
	 * @param  lvl log level to cast
	 * @return      the log level
	 */
	public static int castToInt(LogLevels lvl)
	{
		int n = 0;

		for (LogLevels i : LogLevels.values()) {
			if (lvl == i)
				break;
			++n;
		}

		return n;
	}
}

public class Logger
{
	public static LogLevels m_logging_level = LogLevels.VERBOSE;

	/**
	 * Logs the message to the screen with a message.
	 *
	 * @param  str message
	 * @param  lvl logging level
	 */
	public static final void logMessage(String str, LogLevels lvl)
	{

		if (LOG_LEVEL_CAST.castToInt(lvl)
		    >= LOG_LEVEL_CAST.castToInt(m_logging_level)) {
			System.out.println(str);
		}

		if (LOG_LEVEL_CAST.castToInt(lvl)
		    == LOG_LEVEL_CAST.castToInt(LogLevels.MAJOR_CRITICAL)) {
			System.exit(0);
			return;
		}
	}

	/**
	 * Alias for logMessage
	 */
	public static final void logMessage(LogLevels lvl, String str)
	{
		Logger.logMessage(str, lvl);
	}

	/**
	 * Alias for logMessage -- assuming verbose logging
	 */
	public static final void logMessage(String str)
	{
		Logger.logMessage(str, LogLevels.VERBOSE);
	}

	/**
	 * Alias for logMessage -- with a boolean and assumes
	 * LogLevels.MAJOR_CRITICAL level
	 * @param b boolean for condition to be asserted
	 * @param str message
	 */
	public static final void lassert(boolean b, String str)
	{
		if (b) {
			logMessage(str, LogLevels.MAJOR_CRITICAL);
		}
	}

	/**
	 * Alias for logMessage -- with a default boolean of true
	 * LogLevels.MAJOR_CRITICAL level
	 * @param str message
	 */
	public static final void lassert(String str)
	{
		lassert(true, str);
	}
}
