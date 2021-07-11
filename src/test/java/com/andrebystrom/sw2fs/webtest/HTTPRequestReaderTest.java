package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPRequestReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HTTPRequestReaderTest
{
    @Test
    void shouldReadValidHeaders() throws IOException
    {
        String request = "GET / HTTP/1.0\r\nconnection: local\r\n\r\n";
        String requestBody = "This is the \n body";
        HTTPRequestReader reader = new HTTPRequestReader();
        String result = reader.readHeaders(new ByteArrayInputStream((request + requestBody).getBytes()));
        assertEquals(request, result);
    }

    @Test
    void shouldReadBody() throws IOException
    {
        String request = "GET / HTTP/1.0\r\nconnection: local\r\n\r\n";
        String requestBody = "This is the \n body";
        HTTPRequestReader reader = new HTTPRequestReader();
        String headers = reader.readHeaders(new ByteArrayInputStream((request + requestBody).getBytes()));
        String result = reader.readBody(new ByteArrayInputStream((request + requestBody).getBytes()),
                requestBody.getBytes().length);
        assertEquals("This is the \n body", result);
    }
}
