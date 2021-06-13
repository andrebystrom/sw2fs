package com.andrebystrom.sw2fs.log;

public class LoggerFactory
{
    private static Logger logger;

    public static Logger getLogger()
    {
        if(logger == null)
        {
            logger = new ConsoleLogger();
        }
        return logger;
    }
}
