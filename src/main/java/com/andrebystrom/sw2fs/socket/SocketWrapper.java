package com.andrebystrom.sw2fs.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWrapper implements ISocketWrapper
{
    private final Socket socket;
    public SocketWrapper(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return this.socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException
    {
        return this.socket.getOutputStream();
    }

    @Override
    public void close() throws IOException
    {
        this.socket.close();
    }
}
