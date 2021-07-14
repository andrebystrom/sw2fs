package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.FileWrapper;
import com.andrebystrom.sw2fs.log.Logger;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;
import com.andrebystrom.sw2fs.web.interfaces.*;

import java.io.*;
import java.text.ParseException;

/**
 * Handles an incoming HTTP Request.
 */
public class HTTPRequestRunner implements Runnable
{
    private final ISocketWrapper socketWrapper;
    private final RequestParser parser;
    private final RequestReader reader;
    private final RequestWriter writer;
    private final ResponseBuilder builder;
    private final Logger logger;
    private final String root;

    /**
     * Constructs a new HTTPRequestRunner.
     *
     * @param socketWrapper the socket containing the request.
     * @param parser        the parser to parse the request with.
     * @param reader        the reader to read the request with.
     * @param writer        the writer to write a response with.
     * @param builder       the builder to build a response with.
     * @param logger        the logger to log information.
     * @param root          the HTTP root directory.
     */
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

    /**
     * Receives and responds to an HTTP request.
     */
    @Override
    public void run()
    {
        try
        {
            Request request = getRequest();
            Response response = getRequestResponse(request);
            this.writer.write(response, socketWrapper.getOutputStream());
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
        finally
        {
            closeSocket();
        }
    }

    private Request getRequest() throws IOException, ParseException
    {
        String headers = this.reader.readHeaders(this.socketWrapper.getInputStream());
        Request request = this.parser.parse(headers);

        this.logger.logMessage("Received request for " + request.getPath());

        String contentLength = request.getHeader("content-length");
        if(contentLength != null)
        {
            int length = Integer.parseInt(contentLength.trim());
            request.setBody(this.reader.readBody(this.socketWrapper.getInputStream(), length));
        }

        return request;
    }

    private Response getRequestResponse(Request request) throws IOException
    {
        return builder.buildResponse(request, new FileWrapper(this.root + request.getPath()));
    }

    private void closeSocket()
    {
        try
        {
            this.socketWrapper.close();
        }
        catch(IOException e)
        {
            logger.logError("Failed to close connection: " + e.getMessage());
        }
    }
}
