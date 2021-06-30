package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPResponseBuilder;
import org.junit.jupiter.api.Test;

public class HTTPResponseBuilderTest
{
    @Test
    void shouldThrowWhenHTTPRequestIsNull()
    {
        HTTPResponseBuilder responseBuilder = new HTTPResponseBuilder();
        assertThrows(IllegalArgumentException.class, () -> responseBuilder.buildResponse(null));
    }
}
