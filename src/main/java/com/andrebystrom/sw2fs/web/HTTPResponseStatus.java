package com.andrebystrom.sw2fs.web;

public enum HTTPResponseStatus
{
    OK(200, "OK"), NOTFOUND(404, "Not Found");
    private final int status;
    private final String friendlyName;

    HTTPResponseStatus(int status, String friendlyName)
    {
        this.status = status;
        this.friendlyName = friendlyName;
    }

    public int getStatus()
    {
        return this.status;
    }

    public String getFriendlyName()
    {
        return this.friendlyName + " " + this.status;
    }
}
