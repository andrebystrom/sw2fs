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
    private int bytesRead;

    public HTTPRequestRunner(ISocketWrapper socketWrapper, HTTPRequestParser parser, IFileWrapper file)
    {
        this.socketWrapper = socketWrapper;
        this.parser = parser;
        this.file = file;
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
            this.file.setFile(this.request.getPath());
            this.response = new HTTPResponseBuilder(this.file).buildResponse(this.request);
            this.writeResponse();
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

    private String readHeaders() throws ParseException, IOException
    {
        final int UNREAD_LINE_SIZE = 2;
        BufferedReader br = new BufferedReader(new InputStreamReader(this.socketWrapper.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null && !line.isBlank())
        {
            sb.append(line);
            sb.append("\r\n");
        }
        String headers = sb.toString();
        bytesRead += headers.getBytes().length + UNREAD_LINE_SIZE;
        return headers;
    }

    private String readBody(int size) throws IOException
    {
        InputStreamReader reader = new InputStreamReader(this.socketWrapper.getInputStream());
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

    private void writeResponse() throws IOException
    {
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(this.socketWrapper.getOutputStream()));
        br.write(this.response.getResponseMessage());
        br.flush();
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
