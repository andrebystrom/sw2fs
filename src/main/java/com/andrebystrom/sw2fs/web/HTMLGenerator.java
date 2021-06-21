package com.andrebystrom.sw2fs.web;

import java.io.File;

public class HTMLGenerator
{
    /**
     * Creates a HTML response for a directory containing the files and links to them.
     *
     * @param requestDirectory the directory that has been requested.
     * @param webDirectoryPath the relative path from the web root to the directory, ending with a file
     *                         separator char.
     * @return a HTML response as a String.
     * @throws IllegalArgumentException if requestDirectory is not a directory or the path is incorrect.
     */
    public String getDirectoryResponse(File requestDirectory, String webDirectoryPath) throws IllegalArgumentException
    {
        if(!requestDirectory.isDirectory())
        {
            throw new IllegalArgumentException("requestDirectory is not a directory.");
        }
        if(webDirectoryPath.length() > File.separator.length() && !webDirectoryPath.endsWith(File.separator))
        {
            throw new IllegalArgumentException("Invalid web directory path.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        sb.append("<tr>\n<th>Name</th>\n<th>Size(mb)</th>\n");
        for(var file : requestDirectory.listFiles())
        {
            sb.append("<tr>\n");
            sb.append("<td>" + "<a href=\"");
            sb.append(webDirectoryPath + file.getName() + "/");
            sb.append("\">");
            sb.append(file.getName());
            sb.append("</a></td>");
            sb.append("<td>" + file.getTotalSpace() * 10E-6 + "</td>");
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        sb.append("<a href=\"..\">\n");
        sb.append("Back\n");
        sb.append("</a>\n");

        return sb.toString();
    }

    /**
     * Returns a 404 not found HTML response.
     * @return a 404 not found HTML response.
     */
    public String getNotFoundResponse()
    {
        return "<h1>404 not found.</h1>";
    }
}
