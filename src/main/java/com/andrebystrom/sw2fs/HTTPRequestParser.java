package com.andrebystrom.sw2fs;

import java.io.IOException;

public class HTTPRequestParser
{
    public HTTPRequest parseRequest(String request)
    {
        HTTPMethod method;
        String path;

        String[] lines = request.split("\n");
        method = getHTTPMethod(lines[0]);
        path = getHTTPPath(lines[0]);

        return new HTTPRequest(method, path, null);
    }

    private HTTPMethod getHTTPMethod(String firstLine)
    {
        for(var method : HTTPMethod.values())
        {
            if(firstLine.startsWith(method.name()))
            {
                return method;
            }
        }
        return null;
    }

    private String getHTTPPath(String firstLine)
    {
        String[] words = firstLine.split(" ");
        if(words.length < 2)
        {
            return null;
        }
        return words[1];
    }
}
