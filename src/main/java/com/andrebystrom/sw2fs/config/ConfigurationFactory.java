package com.andrebystrom.sw2fs.config;

public class ConfigurationFactory
{
    private static Configuration config;

    public static Configuration getConfiguration()
    {
        if(config == null)
        {
            config = new Configuration();
        }
        return config;
    }
}
