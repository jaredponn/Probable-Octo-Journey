package poj;

enum LOG_LEVEL {
	VERBOSE,	// verbose error logging (1)
	MINOR_CRITICAL, // minor critical errors (2)
	MAJOR_CRITICAL, // major erros that crash the program (3)
}

class LOG_LEVEL_CAST
{
	public static int castToInt(LOG_LEVEL lvl)
	{
		int n = 0;

		for (LOG_LEVEL i : LOG_LEVEL.values()) {
			if (lvl == i)
				break;
			++n;
		}

		return n;
	}
}

public class Logger
{
	public static LOG_LEVEL m_logging_level = LOG_LEVEL.VERBOSE;

	public static final void logMessage(String str, LOG_LEVEL lvl)
	{

		if (LOG_LEVEL_CAST.castToInt(lvl)
		    >= LOG_LEVEL_CAST.castToInt(m_logging_level)) {
			System.out.println(str);
		}

		if (lvl == LOG_LEVEL.MAJOR_CRITICAL) {
			return;
			// System.exit(0);
		}
	}
}

