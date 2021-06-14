package com.andrebystrom.sw2fs;

import com.andrebystrom.sw2fs.web.HTTPServer;

import java.io.*;

/**
 * Class containing the entry point for the application.
 */
public class Sw2fs
{
    /**
     * Starts the HTTP server.
     *
     * @param args the commandline arguments.
     */
    public static void main(String[] args)
    {
        var httpServer = new HTTPServer(new File("."));
        httpServer.run();
    }
}