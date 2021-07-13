package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.web.interfaces.RequestWriter;
import com.andrebystrom.sw2fs.web.interfaces.Response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class HTTPRequestWriter implements RequestWriter
{
    @Override
    public void write(Response response, OutputStream outputStream) throws IOException
    {
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(outputStream));
        br.write(response.getResponseMessage());
        br.flush();
    }
}
