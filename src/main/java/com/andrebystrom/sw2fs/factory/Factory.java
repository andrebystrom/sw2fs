package com.andrebystrom.sw2fs.factory;

import com.andrebystrom.sw2fs.web.*;
import com.andrebystrom.sw2fs.web.interfaces.*;

public class Factory
{
    public static Request getRequest()
    {
        return new HTTPRequest();
    }

    public static Response getResponse()
    {
        return new HTTPResponse();
    }

    public static ResponseBuilder getResponseBuilder()
    {
        return new HTTPResponseBuilder(getResponse());
    }

    public static RequestParser getRequestParser()
    {
        return new HTTPRequestParser(getRequest());
    }

    public static RequestReader getRequestReader()
    {
        return new HTTPRequestReader();
    }

    public static RequestWriter getRequestWriter()
    {
        return new HTTPRequestWriter();
    }
}
