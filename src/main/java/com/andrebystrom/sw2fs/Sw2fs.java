package com.andrebystrom.sw2fs;

import com.andrebystrom.sw2fs.socket.ServerSocketWrapper;
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
    public static void main(String[] args) throws IOException
    {
        if(validateArgs(args))
        {
            HTTPServer server = new HTTPServer(new ServerSocketWrapper(8080));
            server.start();
        }
        else
        {
            printUsage();
        }
    }

    private static boolean validateArgs(String[] args)
    {
        return args.length > 0 && new File(args[0]).isDirectory();
    }

    private static void printUsage()
    {

    }
}