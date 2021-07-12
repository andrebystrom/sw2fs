package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.socket.IServerSocketWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;

import java.io.IOException;

public class HTTPServer
{
    private final IServerSocketWrapper serverSocket;
    private final String root;
    public HTTPServer(IServerSocketWrapper serverSocket, String root)
    {
        this.serverSocket = serverSocket;
        this.root = root;
    }

    public void start()
    {
        while(true)
        {
            try
            {
                ISocketWrapper client = this.serverSocket.accept();
                HTTPRequestRunner runner = new HTTPRequestRunner(client, new HTTPRequestParser(), this.root);
                Thread t = new Thread(runner);
                t.start();
            }
            catch(IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }
}