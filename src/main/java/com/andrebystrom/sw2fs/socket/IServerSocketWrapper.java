package com.andrebystrom.sw2fs.socket;

import java.io.IOException;

public interface IServerSocketWrapper
{
    ISocketWrapper accept() throws IOException;

    void close() throws IOException;
}
