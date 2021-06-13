package com.andrebystrom.sw2fs.web;

import java.text.ParseException;

public class HTTPRequestParser
{
    public HTTPRequest parseRequest(String request) throws ParseException
    {
        HTTPMethod method;
        String path;

        String[] lines = request.split("\n");
        method = getHTTPMethod(lines[0]);
        path = getHTTPPath(lines[0]);

        return new HTTPRequest(method, path, null);
    }

    private HTTPMethod getHTTPMethod(String firstLine) throws ParseException
    {
        for(var method : HTTPMethod.values())
        {
            if(firstLine.startsWith(method.name()))
            {
                return method;
            }
        }
        throw new ParseException("Invalid HTTP request!", 1);
    }

    private String getHTTPPath(String firstLine) throws ParseException
    {
        String[] words = firstLine.split(" ");
        if(words.length < 2)
        {
            throw new ParseException("Invalid HTTP Request!", 1);
        }
        return words[1];
    }
}
