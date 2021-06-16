package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.log.Logger;
import com.andrebystrom.sw2fs.log.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;

/**
 * Handles incoming HTTP requests and responds to them.
 */
public class HTTPRequestHandler
{
    private static final int MAX_REQUEST_SIZE = 8192;
    private final HTTPRequestParser parser;
    private final Logger logger;

    /**
     * Constructs a new HTTPRequestHandler.
     */
    public HTTPRequestHandler()
    {
        this.parser = new HTTPRequestParser();
        this.logger = LoggerFactory.getLogger();
    }

    /**
     * Handles the incoming HTTP request.
     *
     * @param socket the socket containing the incoming HTTP request.
     */
    public void handleRequest(Socket socket)
    {
        try
        {
            String requestContents = readStream(socket.getInputStream());

            var httpRequest = parser.parseRequest(requestContents);
            logRequestInfo(socket, httpRequest);
            var httpResponse = new HTTPResponseCreator().createResponse(httpRequest);

            writeResponse(socket.getOutputStream(), httpResponse);
            socket.close();
        }
        catch(IOException ioException)
        {
            logger.logError(ioException.getMessage());
        }
        catch(ParseException parseException)
        {
            logger.logError(parseException.getMessage() + " at line " + parseException.getErrorOffset());
        }
    }

    private String readStream(InputStream stream) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String line;
        StringBuilder sb = new StringBuilder();

        while((line = br.readLine()) != null && !line.isBlank() && sb.length() < MAX_REQUEST_SIZE)
        {
            sb.append(line + "\n");
        }

        return sb.toString();
    }

    private void writeResponse(OutputStream outputStream, HTTPResponse httpResponse) throws IOException
    {
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(outputStream));
        br.write(httpResponse.toString());
        br.flush();
    }

    private void logRequestInfo(Socket socket, HTTPRequest httpRequest)
    {
        logger.logInformational("Received request from "
                + socket.getInetAddress()
                + " " + httpRequest.getMethod()
                + " for "
                + httpRequest.getPath());
    }
}
