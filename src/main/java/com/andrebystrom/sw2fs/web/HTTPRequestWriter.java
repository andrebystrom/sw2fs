package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.web.interfaces.RequestWriter;
import com.andrebystrom.sw2fs.web.interfaces.Response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Writes an HTTP Response to an output stream.
 */
public class HTTPRequestWriter implements RequestWriter
{
    /**
     * Writes the response to the output stream.
     *
     * @param response     the response to write.
     * @param outputStream the stream to write to.
     * @throws IOException if there's an issue writing the response.
     */
    @Override
    public void write(Response response, OutputStream outputStream) throws IOException
    {
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(outputStream));
        br.write(response.getResponseMessage());
        br.flush();
    }
}
