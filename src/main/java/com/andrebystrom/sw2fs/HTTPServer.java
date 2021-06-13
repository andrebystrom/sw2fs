package com.andrebystrom.sw2fs;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class HTTPServer
{
    public static final int MAX_CONNECTIONS = 20;
    public static final int PORT = 8080;
    private final File root;

    public HTTPServer(File root)
    {
        if(root == null || !root.isDirectory())
        {
            throw new IllegalArgumentException("Web server root cannot be null or not a directory.");
        }
        this.root = root;
    }

    public void run() throws IOException, InterruptedException
    {
        var serverSocket = new ServerSocket(PORT);
        while(true)
        {
            var client = serverSocket.accept();
            new HTTPRequestHandler().handleRequest(client);
        }
    }
}
