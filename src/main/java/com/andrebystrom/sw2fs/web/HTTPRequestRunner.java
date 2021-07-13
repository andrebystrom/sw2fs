package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.FileWrapper;
import com.andrebystrom.sw2fs.log.Logger;
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
    private final Logger logger;
    private final String root;

    public HTTPRequestRunner(ISocketWrapper socketWrapper,
                             RequestParser parser,
                             RequestReader reader,
                             RequestWriter writer,
                             ResponseBuilder builder,
                             Logger logger,
                             String root)
    {
        this.socketWrapper = socketWrapper;
        this.parser = parser;
        this.reader = reader;
        this.writer = writer;
        this.builder = builder;
        this.logger = logger;
        this.root = root;
    }

    @Override
    public void run()
    {
        try
        {
            String headers = this.reader.readHeaders(this.socketWrapper.getInputStream());
            this.request = this.parser.parse(headers);
            this.logger.logMessage("Received request for " + this.request.getPath());
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
            this.logger.logError("Failed to parse request: " + parseException.getMessage());
        }
        catch(IOException ioException)
        {
            this.logger.logError("Connection error: " + ioException.getMessage());
        }
        catch(NumberFormatException numberFormatException)
        {
            this.logger.logError("Failed to get content-length from request.");
        }
    }
}
