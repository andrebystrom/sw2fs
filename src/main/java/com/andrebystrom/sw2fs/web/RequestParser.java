package com.andrebystrom.sw2fs.web;

import java.text.ParseException;

public interface RequestParser
{
    Request parse(String request) throws ParseException;
}
