package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.config.Configuration;
import com.andrebystrom.sw2fs.config.ConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTTPResponseCreator
{
    private final Configuration configuration;

    public HTTPResponseCreator()
    {
        configuration = ConfigurationFactory.getConfiguration();
    }

    public HTTPResponse createResponse(HTTPRequest httpRequest) throws IOException
    {
        var response = new HTTPResponse();

        switch(httpRequest.getMethod())
        {
            case GET:
                File f = new File(makeWebPath(configuration.getHTTPRootPath(), httpRequest.getPath()));
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
                        sb.append("<td>" + "<a href=\"");
                        sb.append(getWebServerPath(file.getAbsolutePath()));
                        sb.append("\">");
                        sb.append(file.getName());
                        sb.append("</a></td>");
                        sb.append("<td>" + file.getTotalSpace() * 10E-6 + "</td>");
                        sb.append("</tr>\n");
                    }
                    sb.append("</table>\n");
                    sb.append("<a href=\"");
                    sb.append(getParentPath(f));
                    sb.append("\">\n");
                    sb.append("Back\n");
                    sb.append("</a>\n");
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
                break;
            case POST:
                break;
            case DELETE:
                break;
        }
        return response;
    }

    private String makeWebPath(String baseFolder, String requestPath)
    {
        return baseFolder + requestPath;
    }

    private String getWebServerPath(String requestPath)
    {
        return requestPath.length() <= configuration.getHTTPRootPath().length() ?
                "/" : requestPath.substring(configuration.getHTTPRootPath().length());
    }

    private String getParentPath(File child)
    {
        return getWebServerPath(child.getParent());
    }
}
