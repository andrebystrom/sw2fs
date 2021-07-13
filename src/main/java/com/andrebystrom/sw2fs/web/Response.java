package com.andrebystrom.sw2fs.web;

public interface Response
{
    void setVersion(String version);

    String getVersion();

    void setStatus(HTTPResponseStatus status);

    HTTPResponseStatus getStatus();

    void addHeader(String name, String val);

    String getHeader(String name);

    void setBody(String body);

    String getBody();

    String getResponseMessage();
}
