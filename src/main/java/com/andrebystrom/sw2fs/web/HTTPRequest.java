package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.web.interfaces.Request;

import java.util.HashMap;

/**
 * Class representing an HTTP request.
 */
public class HTTPRequest implements Request
{
    private HTTPMethod method;
    private String path;
    private String version;
    private String body;
    private final HashMap<String, String> headers;

    /**
     * Constructs a new HTTP request.
     */
    public HTTPRequest()
    {
        this.headers = new HashMap<>();
    }

    /**
     * Get the HTTP request method.
     * @return the HTTP request method.
     */
    @Override
    public HTTPMethod getMethod()
    {
        return this.method;
    }

    /**
     * Get the HTTP request path.
     * @return the HTTP request path.
     */
    @Override
    public String getPath()
    {
        return this.path;
    }

    /**
     * Get the HTTP request version.
     * @return the HTTP request version.
     */
    @Override
    public String getVersion()
    {
        return this.version;
    }

    /**
     * Set the HTTP request method.
     * @param method the method to set to.
     */
    @Override
    public void setMethod(HTTPMethod method)
    {
        this.method = method;
    }

    /**
     * Set the HTTP request path.
     * @param path the path to set to.
     */
    @Override
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * Set the HTTP request version.
     * @param version the version to set to.
     */
    @Override
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * Get a HTTP request header from the HTTP request.
     * @param name the name of the header.
     * @return the value of the header or null if it doesn't exist.
     */
    @Override
    public String getHeader(String name)
    {
        return this.headers.get(name);
    }

    /**
     * Adds a header to the HTTP request.
     * @param name the name of the header.
     * @param val the value of the header.
     */
    @Override
    public void addHeader(String name, String val)
    {
        this.headers.put(name, val);
    }

    /**
     * Get the body of the HTTP request.
     * @return the body of the HTTP request.
     */
    @Override
    public String getBody()
    {
        return this.body;
    }

    /**
     * Set the body of the HTTP request.
     * @param body the body to set to.
     */
    @Override
    public void setBody(String body)
    {
        this.body = body;
    }
}
