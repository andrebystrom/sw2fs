package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.IFileWrapper;
import com.andrebystrom.sw2fs.web.interfaces.Request;
import com.andrebystrom.sw2fs.web.interfaces.Response;
import com.andrebystrom.sw2fs.web.interfaces.ResponseBuilder;

import java.io.IOException;
import java.util.List;

public class HTTPResponseBuilder implements ResponseBuilder
{
    public static final String HTTP_VERSION = "HTTP/1.0";
    private final Response response;

    public HTTPResponseBuilder(Response response)
    {
        this.response = response;
        this.response.setVersion(HTTP_VERSION);
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
                    createFileResponse(fileWrapper);
                }
                else if(fileWrapper.isDirectory())
                {
                    createDirectoryResponse(request, fileWrapper);
                }
                break;
            default:
                // Not implemented yet.
                response.setStatus(HTTPResponseStatus.NOTFOUND);
        }
        return response;
    }

    private void createFileResponse(IFileWrapper fileWrapper) throws IOException
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

    private void createDirectoryResponse(Request request, IFileWrapper fileWrapper)
    {
        StringBuilder sb = new StringBuilder();
        List<IFileWrapper> fileWrappers = fileWrapper.getDirectories();
        for(int i = 0; i < fileWrappers.size(); i++)
        {
            createFileHTML(request, sb, fileWrappers, i);
        }
        response.setBody(sb.toString());
    }

    private void createFileHTML(Request request, StringBuilder sb, List<IFileWrapper> fileWrappers, int i)
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
}
