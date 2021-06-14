package com.andrebystrom.sw2fs.config;

/**
 * Factory for the configuration.
 */
public class ConfigurationFactory
{
    private static Configuration config;

    /**
     * Get the Configuration.
     *
     * @return the configuration.
     */
    public static Configuration getConfiguration()
    {
        if(config == null)
        {
            config = new Configuration();
        }
        return config;
    }
}
