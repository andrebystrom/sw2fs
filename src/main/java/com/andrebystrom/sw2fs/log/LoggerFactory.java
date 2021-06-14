package com.andrebystrom.sw2fs.log;

/**
 * Factory for the logger.
 */
public class LoggerFactory
{
    private static Logger logger;

    /**
     * Get the logger.
     *
     * @return the logger.
     */
    public static Logger getLogger()
    {
        if(logger == null)
        {
            logger = new ConsoleLogger();
        }
        return logger;
    }
}
