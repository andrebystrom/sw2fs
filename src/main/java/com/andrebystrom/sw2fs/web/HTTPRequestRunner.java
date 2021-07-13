package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.FileWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;
import com.andrebystrom.sw2fs.web.interfaces.*;

import java.io.*;
import java.text.ParseException;

public class HTTPRequestRunner implements Runnable
{
    private final ISocketWrapper socketWrapper;
    private final RequestParser parser;
    private Request request;
    private Response response;
    private final RequestReader reader;
    private final RequestWriter writer;
    private final ResponseBuilder builder;
    private final String root;

    public HTTPRequestRunner(ISocketWrapper socketWrapper,
                             RequestParser parser,
                             RequestReader reader,
                             RequestWriter writer,
                             ResponseBuilder builder,
                             String root)
    {
        this.socketWrapper = socketWrapper;
        this.parser = parser;
        this.root = root;
        this.reader = reader;
        this.writer = writer;
        this.builder = builder;
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
            this.response = builder
                    .buildResponse(this.request, new FileWrapper(this.root + this.request.getPath()));
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
}
