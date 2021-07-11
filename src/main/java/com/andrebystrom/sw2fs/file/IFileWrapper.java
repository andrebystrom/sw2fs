package com.andrebystrom.sw2fs.file;

import java.io.IOException;
import java.util.List;

public interface IFileWrapper
{
    boolean exists();
    boolean isFile();
    boolean isDirectory();
    String getAbsolutePath();
    String getName();
    List<String> getAllLines() throws IOException;
    List<IFileWrapper> getDirectories();
}
