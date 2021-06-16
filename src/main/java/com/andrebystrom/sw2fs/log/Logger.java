package com.andrebystrom.sw2fs.log;

/**
 * Interface for logging operations.
 */
public interface Logger
{
    /**
     * Logs an ordinary message.
     * @param text the message to log.
     */
    void logMessage(String text);

    /**
     * Logs a information message.
     * @param text the message to log.
     */
    void logInformational(String text);

    /**
     * Logs a warning message.
     * @param text the message to log.
     */
    void logWarning(String text);

    /**
     * Logs an error message.
     * @param text the message to log.
     */
    void logError(String text);
}
