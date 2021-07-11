package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.FileWrapper;
import com.andrebystrom.sw2fs.file.IFileWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;

import java.io.*;
import java.text.ParseException;

public class HTTPRequestRunner implements Runnable
{
    private final ISocketWrapper socketWrapper;
    private final HTTPRequestParser parser;
    private HTTPRequest request;
    private HTTPResponse response;
    private IFileWrapper file;
    private final HTTPRequestReader reader;
    private final HTTPRequestWriter writer;

    public HTTPRequestRunner(ISocketWrapper socketWrapper, HTTPRequestParser parser, IFileWrapper file)
    {
        this.socketWrapper = socketWrapper;
        this.parser = parser;
        this.file = file;
        this.reader = new HTTPRequestReader();
        this.writer = new HTTPRequestWriter();
    }

    @Override
    public void run()
    {
        try
        {
            String headers = this.reader.readHeaders(this.socketWrapper.getInputStream());
            this.request = this.parser.parse(headers);
            String contentLength = this.request.getHeader("content-length");
            if(contentLength != null)
            {
                this.request.setBody(this.reader.readBody(this.socketWrapper.getInputStream(),
                        Integer.parseInt(contentLength.trim())));
            }
            this.file.setFile(this.request.getPath());
            this.response = new HTTPResponseBuilder(this.file).buildResponse(this.request);
            this.writer.write(response, socketWrapper.getOutputStream());
            this.socketWrapper.close();
        }
        catch(ParseException parseException)
        {
            parseException.printStackTrace();
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
        catch(NumberFormatException numberFormatException)
        {
            numberFormatException.printStackTrace();
        }
    }

    public HTTPRequest getHTTPRequest()
    {
        return this.request;
    }

    public HTTPResponse getHTTPResponse()
    {
        return this.response;
    }
}
