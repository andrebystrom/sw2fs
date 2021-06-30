package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPResponse;
import com.andrebystrom.sw2fs.web.HTTPResponseStatus;
import org.junit.jupiter.api.Test;

public class HTTPResponseTest
{
    @Test
    void shouldSetVersion()
    {
        HTTPResponse response = new HTTPResponse();
        response.setVersion("HTTP/1.0");

        assertEquals("HTTP/1.0", response.getVersion());
    }

    @Test
    void shouldSetStatus()
    {
        HTTPResponse response = new HTTPResponse();
        response.setStatus(HTTPResponseStatus.OK);

        assertEquals(HTTPResponseStatus.OK, response.getStatus());
    }

    @Test
    void shouldAddHeader()
    {
        HTTPResponse response = new HTTPResponse();
        response.addHeader("NAME", "VAL");

        assertEquals("VAL", response.getHeader("NAME"));
    }

    @Test
    void addingHeaderShouldNotRemoveExistingHeader()
    {
        HTTPResponse response = new HTTPResponse();
        response.addHeader("H1", "val1");
        response.addHeader("H2", "val2");

        assertEquals("val1", response.getHeader("H1"));
        assertEquals("val2", response.getHeader("H2"));
    }

    @Test
    void shouldSetBody()
    {
        HTTPResponse response = new HTTPResponse();
        response.setBody("Response Body");

        assertEquals("Response Body", response.getBody());
    }

    @Test
    void shouldReturnValidHTTPResponseMessage()
    {
        HTTPResponse response = new HTTPResponse();
        response.setStatus(HTTPResponseStatus.OK);
        response.setVersion("HTTP/1.0");
        response.addHeader("content-type", "plain/text");
        response.setBody("<p>hello</p>");

        assertEquals("HTTP/1.0 200 OK\r\ncontent-type: plain/text\r\n\r\n<p>hello</p>",
                response.getResponseMessage());
    }

    @Test
    void shouldReturnValidHTTPResponseMessageWhenResponseHasNoHeaders()
    {
        HTTPResponse response = new HTTPResponse();
        response.setVersion("HTTP/1.0");
        response.setStatus(HTTPResponseStatus.NOTFOUND);
        response.setBody("<h1>Title</h1>");

        assertEquals("HTTP/1.0 404 Not Found\r\n\r\n<h1>Title</h1>", response.getResponseMessage());
    }

    @Test
    void shouldReturnValidHTTPResponseMessageWhenResponseHasNoBody()
    {
        HTTPResponse response = new HTTPResponse();
        response.setVersion("HTTP/1.0");
        response.setStatus(HTTPResponseStatus.NOTFOUND);

        assertEquals("HTTP/1.0 404 Not Found\r\n\r\n", response.getResponseMessage());
    }
}
