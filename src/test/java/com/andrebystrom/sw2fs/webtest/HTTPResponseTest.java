package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPResponse;
import com.andrebystrom.sw2fs.web.HTTPResponseStatus;
import org.junit.jupiter.api.Test;

public class HTTPResponseTest
{
    @Test
    void shouldSetStatus()
    {
        var httpResponse = new HTTPResponse();
        httpResponse.setResponseStatus(HTTPResponseStatus.OK);
        assertEquals("HTTP/1.0 200 OK\r\n\r\n", httpResponse.toString());
    }

    @Test
    void shouldAddHeaders()
    {
        var httpResponse = new HTTPResponse();
        httpResponse.setResponseStatus(HTTPResponseStatus.NOTFOUND);
        httpResponse.addHeaders("server", "sw2fs");

        assertEquals("HTTP/1.0 404 Not Found\r\nserver: sw2fs\r\n\r\n", httpResponse.toString());
    }

    @Test
    void shouldAddBody()
    {
        var httpResponse = new HTTPResponse();
        httpResponse.setResponseStatus(HTTPResponseStatus.NOTFOUND);
        httpResponse.addHeaders("server", "sw2fs");
        httpResponse.setBody("<p>hello</p>");

        assertEquals("HTTP/1.0 404 Not Found\r\nserver: sw2fs\r\n\r\n<p>hello</p>", httpResponse.toString());
    }
}
