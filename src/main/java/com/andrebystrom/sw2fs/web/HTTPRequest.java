package com.andrebystrom.sw2fs.web;

import java.util.HashMap;

public class HTTPRequest
{
    private HTTPMethod method;
    private String path;
    private String version;
    private String body;
    private final HashMap<String, String> headers;

    public HTTPRequest()
    {
        this.headers = new HashMap<>();
    }

    public HTTPMethod getMethod()
    {
        return this.method;
    }

    public String getPath()
    {
        return this.path;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setMethod(HTTPMethod method)
    {
        this.method = method;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getHeader(String connection)
    {
        return this.headers.get(connection);
    }

    public void addHeader(String name, String val)
    {
        this.headers.put(name, val);
    }

    public String getBody()
    {
        return this.body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
}
