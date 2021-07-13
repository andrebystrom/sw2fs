package com.andrebystrom.sw2fs.web.interfaces;

import com.andrebystrom.sw2fs.file.IFileWrapper;

import java.io.IOException;

public interface ResponseBuilder
{
    Response buildResponse(Request request, IFileWrapper fileWrapper)
            throws IllegalArgumentException, IOException;
}
