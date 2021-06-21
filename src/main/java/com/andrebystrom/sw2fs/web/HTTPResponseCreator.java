package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.config.Configuration;
import com.andrebystrom.sw2fs.config.ConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Builds and returns an HTTPResponse for a particular request.
 */
public class HTTPResponseCreator
{
    private final Configuration configuration;
    private final HTMLGenerator htmlGenerator;

    /**
     * Constructs a new HTTPResponseCreator.
     */
    public HTTPResponseCreator()
    {
        configuration = ConfigurationFactory.getConfiguration();
        htmlGenerator = new HTMLGenerator();
    }

    /**
     * Creates a HTTP response to an HTTP request.
     *
     * @param httpRequest the request respond to.
     * @return the HTTP response.
     * @throws IOException if there's an issue reading the resource requested.
     */
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
                    response.setBody(htmlGenerator.getNotFoundResponse());
                }
                else if(f.isDirectory())
                {
                    response.setBody(htmlGenerator.getDirectoryResponse(f, httpRequest.getPath()));
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
        return (baseFolder + requestPath.replaceAll(File.separatorChar + "{2,}", "/"));
    }
}
