package com.andrebystrom.sw2fs.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISocketWrapper
{
    InputStream getInputStream() throws IOException;
    OutputStream getOutputStream() throws IOException;
    void close() throws IOException;
}
