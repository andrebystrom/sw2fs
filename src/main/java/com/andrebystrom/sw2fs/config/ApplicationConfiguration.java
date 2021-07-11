package com.andrebystrom.sw2fs.config;

/**
 * Class for representing configuration data used throughout the application.
 */
public class ApplicationConfiguration implements Configuration
{
    private String HTTPRootPath;

    /**
     * Get the HTTP root path without "/" at the end.
     *
     * @return the HTTP root path.
     */
    public String getHTTPRoot()
    {
        return this.HTTPRootPath;
    }

    /**
     * Set the HTTP root path, should not end with a '/' char.
     *
     * @param rootPath the root path to set.
     */
    public void setHTTPRoot(String rootPath)
    {
        this.HTTPRootPath = rootPath;
    }
}
