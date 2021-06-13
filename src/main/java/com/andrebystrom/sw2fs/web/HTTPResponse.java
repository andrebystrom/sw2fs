package com.andrebystrom.sw2fs.web;

import java.util.HashMap;

public class HTTPResponse
{
    private String firstLine;
    private final HashMap<String, String> headers;
    private String body;

    public HTTPResponse()
    {
        headers = new HashMap<>();
    }

    public void setResponseStatus(HTTPResponseStatus statusCode)
    {
        firstLine = "HTTP/1.0 " + statusCode.getFriendlyName();
    }

    public void addHeaders(String name, String value)
    {
        headers.put(name, value);
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.firstLine + "\r\n");
        for(String k : headers.keySet())
        {
            sb.append(k + ": " + headers.get(k) + "\r\n");
        }
        sb.append("\r\n");
        if(body != null)
        {
            sb.append(body);
        }
        return sb.toString();
    }
}
