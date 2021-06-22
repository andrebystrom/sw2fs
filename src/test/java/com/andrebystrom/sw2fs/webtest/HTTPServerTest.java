package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPServer;
import org.junit.jupiter.api.Test;

import java.io.File;

public class HTTPServerTest
{
    @Test
    void shouldThrowWhenRootIsNull()
    {
        assertThrows(IllegalArgumentException.class, () -> new HTTPServer(null));
    }

    @Test
    void shouldThrowWhenRootIsNotADirectory()
    {
        File f = new File("");
        assertThrows(IllegalArgumentException.class, () -> new HTTPServer(f));
    }

    @Test
    void shouldNotThrowWhenRootIsValid()
    {
        File f = new File(".");
        assertDoesNotThrow(() -> new HTTPServer(f));
    }
}
