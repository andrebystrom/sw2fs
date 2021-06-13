package com.andrebystrom.sw2fs;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class HTTPRequestHandler
{
    private final HTTPRequestParser parser;

    public HTTPRequestHandler()
    {
        this.parser = new HTTPRequestParser();
    }

    public void handleRequest(Socket socket) throws IOException
    {
        String requestContents = readStream(socket.getInputStream());
        var httpRequest = parser.parseRequest(requestContents);
        var httpResponse = new HTTPResponseCreator().createResponse(httpRequest);
        writeResponse(socket.getOutputStream(), httpResponse);
        socket.close();
    }

    private String readStream(InputStream stream) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String firstLine = br.readLine();
        // Read remainder of the request.
        while(!br.readLine().isBlank())
        {
            ;
        }

        return firstLine;
    }

    private void writeResponse(OutputStream outputStream, HTTPResponse httpResponse) throws IOException
    {
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(outputStream));
        br.write(httpResponse.toString());
        br.flush();
    }
}
