package com.andrebystrom.sw2fs;

public class HTTPRequest
{
    private final HTTPMethod method;
    private final String path;
    private final String content;

    public HTTPRequest(HTTPMethod method, String path, String content)
    {
        if(method == null || path == null)
        {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
        this.method = method;
        this.path = path;
        this.content = content;
    }

    public HTTPMethod getMethod()
    {
        return this.method;
    }

    public String getPath()
    {
        return this.path;
    }

    public String getContent()
    {
        return this.content;
    }
}
