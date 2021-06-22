package com.andrebystrom.sw2fs.web;

/**
 * Represents HTTP response statuses.
 */
public enum HTTPResponseStatus
{
    OK(200, "OK"), NOTFOUND(404, "Not Found");
    private final int status;
    private final String friendlyName;

    /**
     * @param status       the status code.
     * @param friendlyName the friendly name to add to the response message.
     */
    HTTPResponseStatus(int status, String friendlyName)
    {
        this.status = status;
        this.friendlyName = friendlyName;
    }

    /**
     * Get the status code.
     *
     * @return the status code.
     */
    public int getStatus()
    {
        return this.status;
    }

    /**
     * Get the friendly name for the HTTP status as it should be displayed in a response.
     *
     * @return the friendly name for the HTTP status.
     */
    public String getFriendlyName()
    {
        return this.status + " " + this.friendlyName;
    }
}
