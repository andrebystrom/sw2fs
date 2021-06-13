package com.andrebystrom.sw2fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTTPResponseCreator
{
    public HTTPResponse createResponse(HTTPRequest httpRequest) throws IOException
    {
        var response = new HTTPResponse();

        switch(httpRequest.getMethod())
        {
            case GET:
                System.out.println("GET " + httpRequest.getPath());
                File f = new File("." + httpRequest.getPath());
                response.setResponseStatus(HTTPResponseStatus.OK);

                if(!f.exists())
                {
                    response.setResponseStatus(HTTPResponseStatus.NOTFOUND);
                    response.setBody("404 not found!!");
                }
                else if(f.isDirectory())
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<table>\n");
                    sb.append("<tr>\n<th>Name</th>\n<th>Size(mb)</th>\n");
                    for(var file : f.listFiles())
                    {
                        sb.append("<tr>\n");
                        sb.append("<td>" + "<a href=\"." + file.getPath() + "\">" + file.getName() + "</a></td>");
                        sb.append("<td>" + file.getTotalSpace() * 10E-6 + "</td>");
                        sb.append("</tr>\n");
                    }
                    sb.append("</table>\n");
                    response.setBody(sb.toString());
                }
                else if(f.isFile())
                {
                    response.addHeaders("Content-Type", "application/octet-stream");
                    response.addHeaders("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                    var lines = Files.readAllLines(f.toPath());
                    StringBuilder sb = new StringBuilder();
                    for(var line : lines)
                    {
                        sb.append(line);
                        sb.append("\n");
                    }
                    response.setBody(sb.toString());
                }
                break;
            case PUT:
                System.out.println("PUT");
                break;
            case POST:
                System.out.println("POST");
                break;
            case DELETE:
                System.out.println("DELETE");
                break;
        }
        return response;
    }
}