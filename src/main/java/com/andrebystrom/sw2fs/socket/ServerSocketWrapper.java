package com.andrebystrom.sw2fs.socket;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWrapper implements IServerSocketWrapper
{
    private final ServerSocket serverSocket;

    public ServerSocketWrapper(int port) throws IOException
    {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public ISocketWrapper accept() throws IOException
    {
        return new SocketWrapper(this.serverSocket.accept());
    }

    @Override
    public void close() throws IOException
    {
        this.serverSocket.close();
    }
}
