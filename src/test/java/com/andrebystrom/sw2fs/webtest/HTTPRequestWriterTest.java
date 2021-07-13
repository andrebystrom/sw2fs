package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPRequestWriter;
import com.andrebystrom.sw2fs.web.HTTPResponse;
import com.andrebystrom.sw2fs.web.HTTPResponseStatus;
import com.andrebystrom.sw2fs.web.interfaces.Response;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTTPRequestWriterTest
{
    @Test
    void shouldWriteCorrectResponse() throws IOException
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HTTPRequestWriter writer = new HTTPRequestWriter();
        Response response = new HTTPResponse();
        response.setVersion("HTTP/1.0");
        response.setStatus(HTTPResponseStatus.OK);
        response.addHeader("connection", "local");
        response.setBody("This is the \n body");
        writer.write(response, stream);
        assertEquals(response.getResponseMessage(), new String(stream.toByteArray()));
    }
}
