package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.config.Configuration;
import com.andrebystrom.sw2fs.config.ConfigurationFactory;
import com.andrebystrom.sw2fs.log.Logger;
import com.andrebystrom.sw2fs.log.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Multi-threaded HTTP server.
 */
public class HTTPServer
{
    public static final int MAX_CONNECTIONS = 20;
    public static final int PORT = 8080;
    private final File root;
    private final Logger logger;
    private final Configuration configuration;

    /**
     * Constructs a new HTTP server serving from the root.
     *
     * @param root the root to serve from.
     * @throws IllegalArgumentException if root is null or not a directory.
     */
    public HTTPServer(File root) throws IllegalArgumentException
    {
        if(root == null || !root.isDirectory())
        {
            throw new IllegalArgumentException("Web server root cannot be null or not a directory.");
        }
        this.root = root;
        this.logger = LoggerFactory.getLogger();
        this.configuration = ConfigurationFactory.getConfiguration();
        configuration.setHTTPRootPath(root.getAbsolutePath());
    }

    /**
     * Starts the HTTP server.
     */
    public void run()
    {
        logger.log("Starting server on " + configuration.getHTTPRootPath());
        try
        {
            var serverSocket = new ServerSocket(PORT);
            while(true)
            {
                var client = serverSocket.accept();
                var handler = new HTTPRequestHandler();
                var thread = new Thread(() -> handler.handleRequest(client));
                thread.start();
            }
        }
        catch(IOException ioException)
        {
            logger.log(ioException.getMessage());
        }
    }
}
