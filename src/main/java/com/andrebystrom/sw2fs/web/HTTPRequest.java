package com.andrebystrom.sw2fs.web;

import java.util.HashMap;

public class HTTPRequest implements Request
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

    @Override
    public HTTPMethod getMethod()
    {
        return this.method;
    }

    @Override
    public String getPath()
    {
        return this.path;
    }

    @Override
    public String getVersion()
    {
        return this.version;
    }

    @Override
    public void setMethod(HTTPMethod method)
    {
        this.method = method;
    }

    @Override
    public void setPath(String path)
    {
        this.path = path;
    }

    @Override
    public void setVersion(String version)
    {
        this.version = version;
    }

    @Override
    public String getHeader(String name)
    {
        return this.headers.get(name);
    }

    @Override
    public void addHeader(String name, String val)
    {
        this.headers.put(name, val);
    }

    @Override
    public String getBody()
    {
        return this.body;
    }

    @Override
    public void setBody(String body)
    {
        this.body = body;
    }
}
