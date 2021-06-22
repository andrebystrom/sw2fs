package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPRequestHandler;
import org.junit.jupiter.api.Test;

import java.net.Socket;

public class HTTPRequestHandlerTest
{
    @Test
    void shouldNotThrowWhenSocketIsDisconnected()
    {
        assertDoesNotThrow(() -> new HTTPRequestHandler().handleRequest(new Socket()));
    }
}
