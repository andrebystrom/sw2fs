package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.web.interfaces.RequestReader;

import java.io.*;

/**
 * Responsible for reading a HTTP request from an input stream into a String.
 */
public class HTTPRequestReader implements RequestReader
{
    private int bytesRead;

    /**
     * Reads the headers in the HTTP request.
     *
     * @param inputStream the input stream to read from.
     * @return the headers of the request.
     * @throws IOException if there's an issue reading the request.
     */
    @Override
    public String readHeaders(InputStream inputStream) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null && !line.isBlank())
        {
            sb.append(line);
            sb.append("\r\n");
        }
        sb.append("\r\n");
        String headers = sb.toString();
        bytesRead += headers.getBytes().length;
        return headers;
    }

    /**
     * Reads the body of an HTTP request. NOTE: This has to be called after readHeaders.
     *
     * @param inputStream the input stream to read from.
     * @param size        the number of bytes to read.
     * @return the body of the HTTP request.
     * @throws IOException if there's an issue reading the request.
     */
    @Override
    public String readBody(InputStream inputStream, int size) throws IOException
    {
        InputStreamReader reader = new InputStreamReader(inputStream);
        reader.skip(this.bytesRead);
        int[] body = new int[size];
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < body.length; i++)
        {
            int readChar = reader.read();
            if(readChar == -1)
            {
                break;
            }
            sb.append(Character.toChars(readChar));
        }
        return sb.toString();
    }
}
