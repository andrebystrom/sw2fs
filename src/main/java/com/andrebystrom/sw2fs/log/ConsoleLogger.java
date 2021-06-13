package com.andrebystrom.sw2fs.log;

public class ConsoleLogger implements Logger
{
    @Override
    public void log(String text)
    {
        System.out.println(text);
    }
}
