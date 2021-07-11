package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.file.FileWrapper;
import com.andrebystrom.sw2fs.socket.IServerSocketWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;

import java.io.IOException;

public class HTTPServer
{
    private final IServerSocketWrapper serverSocket;
    private Thread serverThread;
    private boolean isRunning;

    public HTTPServer(IServerSocketWrapper serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void start()
    {
        this.serverThread = new Thread(() -> this.run());
        this.serverThread.start();
        this.isRunning = true;
    }

    public boolean isRunning()
    {
        return this.isRunning;
    }

    public void stop()
    {
        this.isRunning = false;
        try
        {
            this.serverThread.join();
        }
        catch(InterruptedException interruptedException)
        {
            interruptedException.printStackTrace();
        }

    }

    private void run()
    {
        while(isRunning)
        {
            try
            {
                ISocketWrapper client = this.serverSocket.accept();
                HTTPRequestRunner runner = new HTTPRequestRunner(client, new HTTPRequestParser(), new FileWrapper("/"));
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