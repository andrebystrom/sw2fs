package com.andrebystrom.sw2fs.log;

/**
 * A console implementation of the logger.
 */
public class ConsoleLogger implements Logger
{
    /**
     * Logs a message to the console.
     * @param text the message to log.
     */
    @Override
    public void log(String text)
    {
        System.out.println(text);
    }
}
