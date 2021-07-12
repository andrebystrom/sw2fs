package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.socket.IServerSocketWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer
{
    public static final int MAX_THREADS = 20;
    private final IServerSocketWrapper serverSocket;
    private final String root;
    private final ExecutorService executorService;
    public HTTPServer(IServerSocketWrapper serverSocket, String root)
    {
        this.serverSocket = serverSocket;
        this.root = root;
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public void start()
    {
        while(true)
        {
            try
            {
                ISocketWrapper client = this.serverSocket.accept();
                HTTPRequestRunner runner = new HTTPRequestRunner(client, new HTTPRequestParser(), this.root);
                executorService.execute(runner);
            }
            catch(IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }
}