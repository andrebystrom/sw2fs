package com.andrebystrom.sw2fs.web;

import java.io.IOException;
import java.io.InputStream;

public interface RequestReader
{
    String readHeaders(InputStream inputStream) throws IOException;

    String readBody(InputStream inputStream, int size) throws IOException;
}
