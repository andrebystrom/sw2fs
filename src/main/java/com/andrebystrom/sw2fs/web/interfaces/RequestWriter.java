package com.andrebystrom.sw2fs.web.interfaces;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestWriter
{
    void write(Response response, OutputStream outputStream) throws IOException;
}
