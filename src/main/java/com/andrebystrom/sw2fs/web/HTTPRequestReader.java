package com.andrebystrom.sw2fs.web;

import java.io.*;

public class HTTPRequestReader implements RequestReader
{
    private int bytesRead;

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
