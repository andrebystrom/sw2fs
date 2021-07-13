package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.IFileWrapper;

import java.io.IOException;
import java.util.List;

public class HTTPResponseBuilder implements ResponseBuilder
{
    private final Response response;

    public HTTPResponseBuilder(Response response)
    {
        this.response = response;
        this.response.setVersion("HTTP/1.0");
    }

    @Override
    public Response buildResponse(Request request, IFileWrapper fileWrapper)
            throws IllegalArgumentException, IOException
    {
        if(request == null || fileWrapper == null)
        {
            throw new IllegalArgumentException("request or file cannot be null");
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
                        sb.append("<p><a href=\"");
                        sb.append(request.getPath());
                        if(!request.getPath().endsWith("/"))
                        {
                            sb.append("/");
                        }
                        sb.append(fileWrappers.get(i).getName());
                        sb.append("\">");
                        sb.append(fileWrappers.get(i).getName());
                        sb.append("</a></p>");
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
