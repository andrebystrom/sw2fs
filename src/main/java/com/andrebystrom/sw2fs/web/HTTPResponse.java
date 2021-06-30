package com.andrebystrom.sw2fs.web;

import java.util.HashMap;

public class HTTPResponse
{
    private String version;
    private HTTPResponseStatus status;
    private final HashMap<String, String> headers;
    private String body;
    private static final String NEW_LINE = "\r\n";

    public HTTPResponse()
    {
        this.headers = new HashMap<>();
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setStatus(HTTPResponseStatus status)
    {
        this.status = status;
    }

    public HTTPResponseStatus getStatus()
    {
        return this.status;
    }

    public void addHeader(String name, String val)
    {
        this.headers.put(name, val);
    }

    public String getHeader(String name)
    {
        return this.headers.get(name);
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getBody()
    {
        return this.body;
    }

    public String getResponseMessage()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.version);
        sb.append(" ");
        sb.append(this.status.getFriendlyName());
        sb.append(NEW_LINE);
        for(String key : headers.keySet())
        {
            sb.append(key);
            sb.append(": ");
            sb.append(this.headers.get(key));
            sb.append(NEW_LINE);
        }
        sb.append(NEW_LINE);
        if(this.body != null)
        {
            sb.append(this.body);
        }

        return sb.toString();
    }
}
