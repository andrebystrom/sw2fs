package com.andrebystrom.sw2fs.config;

/**
 * Class for representing configuration data used throughout the application.
 */
public class Configuration
{
    private String HTTPRootPath;

    /**
     * Get the HTTP root path without "/" at the end.
     *
     * @return the HTTP root path.
     */
    public String getHTTPRootPath()
    {
        return this.HTTPRootPath;
    }

    /**
     * Set the HTTP root path, should not end with a '/' char.
     *
     * @param rootPath the root path to set.
     */
    public void setHTTPRootPath(String rootPath)
    {
        if(rootPath.endsWith("/.") && rootPath.length() >= 2)
        {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
        this.HTTPRootPath = rootPath;
    }
}
