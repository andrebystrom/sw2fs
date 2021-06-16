package com.andrebystrom.sw2fs;

import com.andrebystrom.sw2fs.log.LoggerFactory;
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
        if(validateArgs(args))
        {
            var httpServer = new HTTPServer(new File(args[0]));
            httpServer.run();
        }
        else
        {
            printUsage();
        }
    }

    public static boolean validateArgs(String[] args)
    {
        return args.length > 0 && new File(args[0]).isDirectory();
    }

    public static void printUsage()
    {
        LoggerFactory.getLogger().logMessage("Usage: sw2fs <path/to/web/root/dir>");
    }
}