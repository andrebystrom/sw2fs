package com.andrebystrom.sw2fs.web;public interface Request
{
    HTTPMethod getMethod();

    String getPath();

    String getVersion();

    void setMethod(HTTPMethod method);

    void setPath(String path);

    void setVersion(String version);

    String getHeader(String name);

    void addHeader(String name, String val);

    String getBody();

    void setBody(String body);
}
