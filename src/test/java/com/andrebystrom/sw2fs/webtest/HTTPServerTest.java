package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.socket.IServerSocketWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;
import com.andrebystrom.sw2fs.web.HTTPServer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

public class HTTPServerTest
{
    @Disabled("Dependencies cannot be mocked")
    @Test
    void shouldStartServer()
    {
        HTTPServer server = new HTTPServer(getServerSocketWrapper());
        server.start();
        assertTrue(server.isRunning());
    }

    @Disabled("Dependencies cannot be mocked")
    @Test
    void shouldStopServer()
    {
        HTTPServer server = new HTTPServer(getServerSocketWrapper());
        server.start();
        server.stop();
        assertFalse(server.isRunning());
    }

    IServerSocketWrapper getServerSocketWrapper()
    {
        return new IServerSocketWrapper()
        {
            @Override
            public ISocketWrapper accept() throws IOException
            {
                return getValidRequestSocket();
            }

            @Override
            public void close() throws IOException
            {

            }
        };
    }

    private ISocketWrapper getValidRequestSocket()
    {
        return new ISocketWrapper()
        {

            @Override
            public InputStream getInputStream() throws IOException
            {
                return new ByteArrayInputStream("GET / HTTP/1.0\r\ncontent-length: 3\r\n\r\nhej".getBytes());
            }

            @Override
            public OutputStream getOutputStream() throws IOException
            {
                return new ByteArrayOutputStream();
            }

            @Override
            public void close() throws IOException
            {

            }
        };
    }
}
