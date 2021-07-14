package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.web.interfaces.Response;

import java.util.HashMap;

/**
 * Class representing an HTTP response.
 */
public class HTTPResponse implements Response
{
    private String version;
    private HTTPResponseStatus status;
    private final HashMap<String, String> headers;
    private String body;
    private static final String NEW_LINE = "\r\n";

    /**
     * Constructs a new HTTP response.
     */
    public HTTPResponse()
    {
        this.headers = new HashMap<>();
    }

    /**
     * Set the HTTP response version.
     *
     * @param version the version to set to.
     */
    @Override
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * Get the HTTP response version.
     *
     * @return the HTTP response version.
     */
    @Override
    public String getVersion()
    {
        return this.version;
    }

    /**
     * Set the HTTP response status.
     *
     * @param status the status to set to.
     */
    @Override
    public void setStatus(HTTPResponseStatus status)
    {
        this.status = status;
    }

    /**
     * Get the HTTP response status.
     *
     * @return the HTTP response status.
     */
    @Override
    public HTTPResponseStatus getStatus()
    {
        return this.status;
    }

    /**
     * Add a HTTP response header.
     *
     * @param name the name of the header.
     * @param val  the value of the header.
     */
    @Override
    public void addHeader(String name, String val)
    {
        this.headers.put(name, val);
    }

    /**
     * Get the value of a HTTP response header.
     *
     * @param name the name of the header.
     * @return the value of the header or null if it doesn't exist.
     */
    @Override
    public String getHeader(String name)
    {
        return this.headers.get(name);
    }

    /**
     * Set the HTTP response body.
     *
     * @param body the body to set.
     */
    @Override
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * Get the HTTP response body.
     *
     * @return the HTTP response body.
     */
    @Override
    public String getBody()
    {
        return this.body;
    }

    /**
     * Get a String representation of the HTTP response that's a valid server response.
     *
     * @return a String representation of the HTTP response.
     */
    @Override
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
