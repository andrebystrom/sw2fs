package com.andrebystrom.sw2fs.web.interfaces;

import java.text.ParseException;

public interface RequestParser
{
    Request parse(String request) throws ParseException;
}
