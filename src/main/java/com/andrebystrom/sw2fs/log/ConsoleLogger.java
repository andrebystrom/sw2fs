package com.andrebystrom.sw2fs.log;

/**
 * A console implementation of the logger.
 */
public class ConsoleLogger implements Logger
{
    /**
     * Logs an ordinary message.
     * @param text the message to log.
     */
    @Override
    public void logMessage(String text)
    {
        System.out.println(text);
    }

    /**
     * Logs an informational message.
     * @param text the message to log.
     */
    @Override
    public void logInformational(String text)
    {
        System.out.println("INFO: " + text);
    }

    /**
     * Logs a warning message.
     * @param text the message to log.
     */
    @Override
    public void logWarning(String text)
    {
        System.out.println("WARNING: " + text);
    }

    /**
     * Logs an error message.
     * @param text the message to log.
     */
    @Override
    public void logError(String text)
    {
        System.out.println("ERROR: " + text);
    }
}
