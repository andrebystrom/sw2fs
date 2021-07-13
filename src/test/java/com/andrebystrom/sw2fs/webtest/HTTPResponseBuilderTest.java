package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.file.IFileWrapper;
import com.andrebystrom.sw2fs.web.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class HTTPResponseBuilderTest
{
    @Test
    void shouldThrowWhenHTTPRequestIsNull()
    {
        HTTPResponseBuilder responseBuilder = new HTTPResponseBuilder(new HTTPResponse());
        assertThrows(IllegalArgumentException.class, () -> responseBuilder.buildResponse(null, null));
    }

    @Test
    void shouldBuildCorrectResponseForValidGetRequestForFile() throws IOException
    {
        IFileWrapper wrapper = getValidMockFile();
        HTTPRequest request = new HTTPRequest();
        request.setMethod(HTTPMethod.GET);
        request.setVersion("HTTP/1.0");
        request.setPath("/file.txt/");

        HTTPResponseBuilder builder = new HTTPResponseBuilder(new HTTPResponse());
        Response response = builder.buildResponse(request, wrapper);
        assertEquals("HTTP/1.0 200 OK\r\ncontent-type: application/octet-stream\r\n\r\nthis\nis\na\nfile",
                response.getResponseMessage());
    }

    @Test
    void shouldBuildCorrectResponseForValidGetRequestForFolder() throws IOException
    {
        IFileWrapper wrapper = getValidMockDir();
        HTTPRequest request = new HTTPRequest();
        request.setMethod(HTTPMethod.GET);
        request.setVersion("HTTP/1.0");
        request.setPath("/web/file/");

        HTTPResponseBuilder builder = new HTTPResponseBuilder(new HTTPResponse());
        Response response = builder.buildResponse(request, wrapper);
        assertEquals("HTTP/1.0 200 OK\r\n\r\n<p><a href=\"/web/file/file.txt\">file.txt</a></p>",
                response.getResponseMessage());
    }

    @Test
    void shouldReturnNotFoundWhenResourceDoesNotExist() throws IOException
    {
        IFileWrapper wrapper = getInvalidMockFile();
        HTTPRequest request = new HTTPRequest();
        request.setMethod(HTTPMethod.GET);
        request.setVersion("HTTP/1.0");
        request.setPath("/file/fail.txt");

        HTTPResponseBuilder builder = new HTTPResponseBuilder(new HTTPResponse());
        Response response = builder.buildResponse(request, wrapper);
        assertEquals("HTTP/1.0 404 Not Found\r\n\r\n404 not found",
                response.getResponseMessage());
    }

    private IFileWrapper getValidMockFile()
    {
        return new IFileWrapper()
        {
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
                return "/web/file/file.txt";
            }

            @Override
            public String getName()
            {
                return "file.txt";
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

    private IFileWrapper getInvalidMockFile()
    {
        return new IFileWrapper()
        {
            @Override
            public boolean exists()
            {
                return false;
            }

            @Override
            public boolean isFile()
            {
                return false;
            }

            @Override
            public boolean isDirectory()
            {
                return false;
            }

            @Override
            public String getAbsolutePath()
            {
                return null;
            }

            @Override
            public String getName()
            {
                return null;
            }

            @Override
            public List<String> getAllLines() throws IOException
            {
                return null;
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
                return "/web/file/";
            }

            @Override
            public String getName()
            {
                return "/web/file/";
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
