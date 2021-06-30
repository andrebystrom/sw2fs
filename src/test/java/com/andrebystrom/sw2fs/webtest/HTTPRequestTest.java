package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPMethod;
import com.andrebystrom.sw2fs.web.HTTPRequest;
import org.junit.jupiter.api.Test;

public class HTTPRequestTest
{
    @Test
    void shouldSetPath()
    {
        HTTPRequest request = new HTTPRequest();
        request.setPath("/path/");
        assertEquals("/path/", request.getPath());
    }

    @Test
    void shouldSetMethod()
    {
        HTTPRequest request = new HTTPRequest();
        request.setMethod(HTTPMethod.PUT);
        assertEquals(HTTPMethod.PUT, request.getMethod());
    }

    @Test
    void shouldSetVersion()
    {
        HTTPRequest request = new HTTPRequest();
        request.setVersion("HTTP/1.0");
        assertEquals("HTTP/1.0", request.getVersion());
    }

    @Test
    void shouldSetBody()
    {
        HTTPRequest request = new HTTPRequest();
        request.setBody("HTTP BODY");
        assertEquals("HTTP BODY", request.getBody());
    }

    @Test
    void shouldAddHeader()
    {
        HTTPRequest request = new HTTPRequest();
        request.addHeader("con", "val");
        assertEquals("val", request.getHeader("con"));
    }

    @Test
    void addingHeaderShouldNotRemoveExistingHeader()
    {
        HTTPRequest request = new HTTPRequest();
        request.addHeader("H1", "val1");
        request.addHeader("H2", "val2");
        assertEquals("val1", request.getHeader("H1"));
        assertEquals("val2", request.getHeader("H2"));
    }
}
