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
            Request request = this.parser.parse(headers);
            this.logger.logMessage("Received request for " + request.getPath());
            String contentLength = request.getHeader("content-length");
            if(contentLength != null)
            {
                request.setBody(this.reader.readBody(this.socketWrapper.getInputStream(),
                        Integer.parseInt(contentLength.trim())));
            }
            Response response = builder
                    .buildResponse(request, new FileWrapper(this.root + request.getPath()));
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
