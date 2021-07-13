package com.andrebystrom.sw2fs.webtest;

import static org.junit.jupiter.api.Assertions.*;

import com.andrebystrom.sw2fs.web.HTTPMethod;
import com.andrebystrom.sw2fs.web.HTTPRequest;
import com.andrebystrom.sw2fs.web.HTTPRequestParser;
import com.andrebystrom.sw2fs.web.Request;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class HTTPRequestParserTest
{
    private final static String NEW_LINE = "\r\n";

    @Test
    void shouldParseFirstLineCorrectly() throws ParseException
    {
        HTTPRequestParser httpRequestParser = new HTTPRequestParser(new HTTPRequest());
        Request request = httpRequestParser.parse("GET / HTTP/1.1");
        assertEquals(HTTPMethod.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("HTTP/1.1", request.getVersion());
    }

    @Test
    void shouldParseHeaders() throws ParseException
    {
        HTTPRequestParser httpRequestParser = new HTTPRequestParser(new HTTPRequest());
        Request request = httpRequestParser.parse("GET / HTTP/1.1"
                + NEW_LINE
                + "Connection: local"
                + NEW_LINE
                + "test: yes");
        assertEquals("local", request.getHeader("Connection"));
        assertEquals("yes", request.getHeader("test"));
    }

    @Test
    void shouldParseBody() throws ParseException
    {
        HTTPRequestParser httpRequestParser = new HTTPRequestParser(new HTTPRequest());
        Request request = httpRequestParser.parse("GET / HTTP/1.1"
                + NEW_LINE
                + "Connection: local"
                + NEW_LINE
                + "test: yes"
                + NEW_LINE
                + NEW_LINE
                + "BODY");
        assertEquals("BODY", request.getBody());
    }

    @Test
    void shouldThrowWhenRequestIsNull()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse(null));
    }

    @Test
    void shouldThrowWhenFirstLineIsTooLongOrShort()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse("GET /"));
        assertThrows(ParseException.class, () -> parser.parse("GET / HTTP/1.1 EXTRA"));
    }

    @Test
    void shouldThrowWhenMethodIsInvalid()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse("GOT / HTTP/1.1"));
    }

    @Test
    void shouldThrowWhenRequestIsEmpty()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    void shouldThrowWhenHeadersAreToShort()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse("GET / HTTP/1.1"
                + NEW_LINE
                + "conn:"
                + NEW_LINE
                + "test: yes"));
    }

    @Test
    void shouldThrowWhenHeaderNameIsEmpty()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse("GET / HTTP/1.1" + NEW_LINE + ": val"));
    }

    @Test
    void shouldThrowWhenHeaderValIsEmpty()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse("GET / HTTP/1.1" + NEW_LINE + "name: "));
    }

    @Test
    void shouldThrowWhenNewLineIsIncorrect()
    {
        HTTPRequestParser parser = new HTTPRequestParser(new HTTPRequest());
        assertThrows(ParseException.class, () -> parser.parse("GET / HTTP/1.1\nname: andre\n\n"));
    }
}
