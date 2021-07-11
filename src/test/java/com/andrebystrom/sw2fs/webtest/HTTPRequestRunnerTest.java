package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.file.IFileWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;
import com.andrebystrom.sw2fs.web.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;

public class HTTPRequestRunnerTest
{
    @Test
    void shouldParseRequestCorrectly()
    {
        ISocketWrapper socketWrapper = getValidRequestSocket();
        HTTPRequestRunner runner = new HTTPRequestRunner(socketWrapper, new HTTPRequestParser(), getValidMockDir());
        runner.run();
        HTTPRequest request = runner.getHTTPRequest();
        assertEquals(HTTPMethod.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("HTTP/1.0", request.getVersion());
        assertEquals("3", request.getHeader("content-length"));
        assertEquals("hej", request.getBody());
    }

    @Test
    void shouldBuildCorrectResponseToRequest()
    {
        ISocketWrapper socketWrapper = getValidRequestSocket();
        HTTPRequestRunner runner = new HTTPRequestRunner(socketWrapper, new HTTPRequestParser(), getValidMockDir());
        runner.run();
        HTTPResponse response = runner.getHTTPResponse();
        assertEquals("HTTP/1.0 200 OK\r\n" + "\r\n" + "<p>file.txt</p>", response.getResponseMessage());
    }

    private HTTPResponseBuilder getBuilder()
    {
        return new HTTPResponseBuilder(getValidMockDir());
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

    private IFileWrapper getValidMockFile()
    {
        return new IFileWrapper()
        {
            private String path;

            @Override
            public void setFile(String path)
            {
                this.path = path;
            }

            @Override
            public boolean exists()
            {
                return true;
            }

            @Override
            public boolean isFile()
            {
                return true;
            }

            @Override
            public boolean isDirectory()
            {
                return false;
            }

            @Override
            public String getAbsolutePath()
            {
                return path == null ? "/web/file.txt" : this.path;
            }

            @Override
            public String getName()
            {
                return path == null ? "file.txt" : this.path;
            }

            @Override
            public List<String> getAllLines() throws IOException
            {
                return List.of("this", "is", "a", "file");
            }

            @Override
            public List<IFileWrapper> getDirectories()
            {
                return null;
            }
        };
    }

    private IFileWrapper getValidMockDir()
    {
        return new IFileWrapper()
        {
            private String path;

            @Override
            public void setFile(String path)
            {
                this.path = path;
            }

            @Override
            public boolean exists()
            {
                return true;
            }

            @Override
            public boolean isFile()
            {
                return false;
            }

            @Override
            public boolean isDirectory()
            {
                return true;
            }

            @Override
            public String getAbsolutePath()
            {
                return this.path == null ? "/web/file/" : this.path;
            }

            @Override
            public String getName()
            {
                return this.path == null ? "/web/file/" : this.path;
            }

            @Override
            public List<String> getAllLines() throws IOException
            {
                return null;
            }

            @Override
            public List<IFileWrapper> getDirectories()
            {
                return List.of(getValidMockFile());
            }
        };
    }
}
