package com.andrebystrom.sw2fs.web;

import com.andrebystrom.sw2fs.web.interfaces.Request;
import com.andrebystrom.sw2fs.web.interfaces.RequestParser;

import java.text.ParseException;
import java.util.Arrays;

/**
 * Class responsible for parsing a String into an HTTP request.
 */
public class HTTPRequestParser implements RequestParser
{
    private static final String NEW_LINE = "\r\n";
    private final Request request;

    /**
     * Constructs a new HTTPRequestParser
     *
     * @param request the request to parse to.
     */
    public HTTPRequestParser(Request request)
    {
        this.request = request;
    }

    /**
     * Parses a String containing an HTTP request into a Request. If the request has no body only the headers will be
     * parsed.
     *
     * @param request the HTTP request to parse.
     * @return a HTTP request.
     * @throws ParseException if there's an error parsing the request.
     */
    @Override
    public Request parse(String request) throws ParseException
    {
        validateArgs(request);

        String[] requestParts = request.split(NEW_LINE + NEW_LINE);
        validateRequestParts(requestParts);
        String[] headers = requestParts[0].split(NEW_LINE);
        parseFirstLine(headers[0]);
        parseHeaders(headers, 1, headers.length);

        this.request.setBody(requestParts.length >= 2 ? requestParts[1] : null);

        return this.request;
    }

    private void validateArgs(String request) throws ParseException
    {
        if(request == null || request.length() < 1)
        {
            throw new ParseException("request cannot be null or empty", -1);
        }
    }

    private void validateRequestParts(String[] requestParts) throws ParseException
    {
        if(requestParts.length < 1)
        {
            throw new ParseException("Invalid HTTP request, no header/body separation", -1);
        }
    }

    private void parseFirstLine(String firstLine) throws ParseException
    {
        String[] words = firstLine.split(" ");
        if(words.length != 3)
        {
            throw new ParseException("Invalid first line of HTTP request.", 1);
        }
        this.request.setMethod(Arrays.stream(HTTPMethod.values())
                .filter(method -> words[0].startsWith(method.name()))
                .findFirst()
                .orElseThrow(() -> new ParseException("Not a valid HTTP Request method", 1)));

        this.request.setPath(words[1]);
        this.request.setVersion(words[2]);
    }

    private void parseHeaders(String[] headerLines, int start, int end) throws ParseException
    {
        final int LINE_OFFSET = start + 1;
        for(int i = start; i < end; i++)
        {
            String[] words = headerLines[i].split(" ");
            if(words.length < 2)
            {
                throw new ParseException("Invalid header length", i + LINE_OFFSET);
            }
            words[0] = words[0].replaceAll(":", "");
            if(words[0].equals(""))
            {
                throw new ParseException("Invalid header name", i + LINE_OFFSET);
            }
            this.request.addHeader(words[0], words[1]);
        }
    }
}