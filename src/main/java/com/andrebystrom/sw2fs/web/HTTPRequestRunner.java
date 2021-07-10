package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.socket.ISocketWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class HTTPRequestRunner implements Runnable
{
    private final ISocketWrapper socketWrapper;
    private final HTTPRequestParser parser;
    private HTTPRequest request;

    public HTTPRequestRunner(ISocketWrapper socketWrapper, HTTPRequestParser parser)
    {
        this.socketWrapper = socketWrapper;
        this.parser = parser;
    }

    @Override
    public void run()
    {
        try
        {
            String headers = readHeaders();
            this.request = this.parser.parse(headers);
            String contentLength = this.request.getHeader("content-length");
            if(contentLength != null)
            {
                this.request.setBody(readBody(Integer.parseInt(contentLength.trim())));
            }
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

    private String readHeaders() throws ParseException, IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.socketWrapper.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != "")
        {
            sb.append(line);
            sb.append("\r\n");
        }
        return sb.toString();
    }

    private String readBody(int size) throws IOException
    {
        int[] body = new int[size];
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(this.socketWrapper.getInputStream());
        for(int i = 0; i < body.length; i++)
        {
            sb.append(Character.toChars(reader.read()));
        }
        return sb.toString();
    }
}
