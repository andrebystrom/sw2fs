package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.IFileWrapper;

import java.io.IOException;
import java.util.List;

public class HTTPResponseBuilder
{
    private IFileWrapper fileWrapper;
    private HTTPResponse response;

    public HTTPResponseBuilder(IFileWrapper fileWrapper)
    {
        this.fileWrapper = fileWrapper;
        this.response = new HTTPResponse();
        this.response.setVersion("HTTP/1.0");
    }

    public HTTPResponse buildResponse(HTTPRequest request) throws IllegalArgumentException, IOException
    {
        if(request == null)
        {
            throw new IllegalArgumentException("request cannot be null");
        }

        switch(request.getMethod())
        {
            case GET:
                response.setStatus(HTTPResponseStatus.OK);
                if(!fileWrapper.exists())
                {
                    response.setStatus(HTTPResponseStatus.NOTFOUND);
                    response.setBody("404 not found");
                }
                else if(fileWrapper.isFile())
                {
                    StringBuilder sb = new StringBuilder();
                    List<String> lines = fileWrapper.getAllLines();
                    for(int i = 0; i < lines.size(); i++)
                    {
                        sb.append(lines.get(i));
                        if(i != lines.size() - 1)
                        {
                            sb.append("\n");
                        }
                    }
                    response.setBody(sb.toString());
                    response.addHeader("content-type", "application/octet-stream");
                }
                else if(fileWrapper.isDirectory())
                {
                    StringBuilder sb = new StringBuilder();
                    List<IFileWrapper> fileWrappers = fileWrapper.getDirectories();
                    for(int i = 0; i < fileWrappers.size(); i++)
                    {
                        sb.append("<p>");
                        sb.append(fileWrappers.get(i).getName());
                        sb.append("</p>");
                        if(i != fileWrappers.size() - 1)
                        {
                            sb.append("\n");
                        }
                    }
                    response.setBody(sb.toString());
                }
                break;
            default:
                throw new IllegalArgumentException("Not implemented");
        }
        return response;
    }
}
