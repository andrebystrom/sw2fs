package com.andrebystrom.sw2fs.web;

/**
 * Represents an HTTP request.
 */
public class HTTPRequest
{
    private final HTTPMethod method;
    private final String path;
    private final String content;

    /**
     * Constructs a new HTTP request.
     *
     * @param method  the HTTP method.
     * @param path    the HTTP request path.
     * @param content the HTTP content.
     * @throws IllegalArgumentException if the method or path is null.
     */
    public HTTPRequest(HTTPMethod method, String path, String content) throws IllegalArgumentException
    {
        if(method == null || path == null)
        {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
        this.method = method;
        this.path = path;
        this.content = content;
    }

    /**
     * Get the HTTP method for the request.
     *
     * @return the HTTP method.
     */
    public HTTPMethod getMethod()
    {
        return this.method;
    }

    /**
     * Get the request path for the request.
     *
     * @return the request path.
     */
    public String getPath()
    {
        return this.path;
    }

    /**
     * Get the contents of the request.
     *
     * @return the contents of the request.
     */
    public String getContent()
    {
        return this.content;
    }
}
