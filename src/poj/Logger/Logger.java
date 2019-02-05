package poj.Logger;

class LOG_LEVEL_CAST
{
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

	public static final void lassert(boolean b, String str)
	{
		if (b) {
			logMessage(str, LogLevels.MAJOR_CRITICAL);
		}
	}
}

