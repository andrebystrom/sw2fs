package com.andrebystrom.sw2fs;

import com.andrebystrom.sw2fs.web.HTTPServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

public class Sw2fs
{
    public static void main(String[] args)
    {
        var httpServer = new HTTPServer(new File("."));
        httpServer.run();
    }

    public static void old() throws IOException
    {
        ServerSocket socket = new ServerSocket(1800);
        while(true)
        {
            Socket client = socket.accept();
            InputStream reader = client.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(reader);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            for(int i = 0; i < 15; System.out.println(bufferedReader.readLine()), i++)
            {
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(client.getOutputStream());
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write("HTTP/1.0 200 OK\r\n\r\nOKOK");
            writer.close();
        }
    }
}