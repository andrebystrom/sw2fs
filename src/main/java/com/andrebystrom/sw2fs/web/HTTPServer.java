package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.factory.Factory;
import com.andrebystrom.sw2fs.socket.IServerSocketWrapper;
import com.andrebystrom.sw2fs.socket.ISocketWrapper;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer
{
    public static final int MAX_THREADS = 20;
    private final ExecutorService executorService;
    public HTTPServer()
    {
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public void start(IServerSocketWrapper serverSocket, String root)
    {
        while(true)
        {
            try
            {
                ISocketWrapper client = serverSocket.accept();
                HTTPRequestRunner runner = new HTTPRequestRunner(client,
                        Factory.getRequestParser(),
                        Factory.getRequestReader(),
                        Factory.getRequestWriter(),
                        Factory.getResponseBuilder(),
                        Factory.getLogger(),
                        root);
                executorService.execute(runner);
            }
            catch(IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }
}