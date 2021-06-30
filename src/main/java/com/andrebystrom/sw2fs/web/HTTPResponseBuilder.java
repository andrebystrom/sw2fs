package com.andrebystrom.sw2fs.web;

public class HTTPResponseBuilder
{
    public HTTPResponse buildResponse(HTTPRequest request) throws IllegalArgumentException
    {
        if(request == null)
        {
            throw new IllegalArgumentException("request cannot be null");
        }
        return null;
    }
}
