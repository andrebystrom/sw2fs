package com.andrebystrom.sw2fs.web;

import java.util.HashMap;

/**
 * Represents an HTTP response.
 */
public class HTTPResponse
{
    private String firstLine;
    private final HashMap<String, String> headers;
    private String body;

    /**
     * Constructs a new HTTP response.
     */
    public HTTPResponse()
    {
        headers = new HashMap<>();
    }

    /**
     * Sets the HTTP response status.
     *
     * @param statusCode
     */
    public void setResponseStatus(HTTPResponseStatus statusCode)
    {
        firstLine = "HTTP/1.0 " + statusCode.getFriendlyName();
    }

    /**
     * Adds headers to the HTTP response.
     *
     * @param name  the name of the header.
     * @param value the value of the header.
     */
    public void addHeaders(String name, String value)
    {
        headers.put(name, value);
    }

    /**
     * Set the body of the HTTP response.
     *
     * @param body the body of the HTTP response.
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * Returns a String representation of the HTTP response that's formatted as a valid HTTP response.
     *
     * @return a String representation of the HTTP response.
     */
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
