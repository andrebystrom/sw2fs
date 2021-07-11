package com.andrebystrom.sw2fs.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWrapper implements IFileWrapper
{
    private File file;

    public FileWrapper(String path)
    {
        this.file = new File(path);
    }

    @Override
    public boolean exists()
    {
        return file.exists();
    }

    @Override
    public boolean isFile()
    {
        return file.isFile();
    }

    @Override
    public boolean isDirectory()
    {
        return file.isDirectory();
    }

    @Override
    public String getAbsolutePath()
    {
        return file.getAbsolutePath();
    }

    @Override
    public String getName()
    {
        return file.getName();
    }

    @Override
    public List<String> getAllLines() throws IOException
    {
        return Files.readAllLines(file.toPath());
    }

    @Override
    public List<IFileWrapper> getDirectories()
    {
        ArrayList<IFileWrapper> fileWrappers = new ArrayList<>();
        for(File f : file.listFiles())
        {
            fileWrappers.add(new FileWrapper(f.getAbsolutePath()));
        }
        return fileWrappers;
    }
}
