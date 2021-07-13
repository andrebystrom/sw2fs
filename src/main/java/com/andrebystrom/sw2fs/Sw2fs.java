package com.andrebystrom.sw2fs;

import com.andrebystrom.sw2fs.factory.Factory;
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
            HTTPServer server = new HTTPServer();
            server.start(new ServerSocketWrapper(8080), trimRoot(args[0]));
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

    private static String trimRoot(String root)
    {
        if(root.endsWith("."))
        {
            root = root.substring(0, root.length() - 1);
        }
        if(root.endsWith(File.separator))
        {
            root = root.substring(0, root.length() - 1);
        }
        return root;
    }

    private static void printUsage()
    {
        Factory.getLogger().logMessage("Usage: Sw2fs <web/root/dir>");
    }
}