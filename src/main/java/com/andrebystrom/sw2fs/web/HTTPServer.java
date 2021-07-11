package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.socket.IServerSocketWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;

import java.io.IOException;

public class HTTPServer
{
    private final IServerSocketWrapper serverSocket;

    public HTTPServer(IServerSocketWrapper serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void start()
    {
        while(true)
        {
            try
            {
                ISocketWrapper client = this.serverSocket.accept();
                HTTPRequestRunner runner = new HTTPRequestRunner(client, new HTTPRequestParser());
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