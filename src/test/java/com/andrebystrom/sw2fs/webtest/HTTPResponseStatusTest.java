package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPResponseStatus;
import org.junit.jupiter.api.Test;

public class HTTPResponseStatusTest
{
    @Test
    void notFoundShouldReturnCorrectHTTPResponseStatus()
    {
        var notFound = HTTPResponseStatus.NOTFOUND;
        assertEquals("404 Not Found", notFound.getFriendlyName());
    }

    @Test
    void okShouldReturnCorrectHTTPResponseStatus()
    {
        var ok = HTTPResponseStatus.OK;
        assertEquals("200 OK", ok.getFriendlyName());
    }
}
