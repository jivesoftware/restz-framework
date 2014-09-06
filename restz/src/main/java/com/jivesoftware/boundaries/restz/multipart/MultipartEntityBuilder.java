package com.jivesoftware.boundaries.restz.multipart;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bmoshe on 5/24/14.
 */
public class MultipartEntityBuilder
{
    private static final Gson gson = new Gson();

    private List<Part> parts;

    public MultipartEntityBuilder()
    {
        this.parts = new LinkedList<>();
    }

    public List<Part> getParts()
    {
        return parts;
    }

    public MultipartEntityBuilder addBinaryPart(String name, byte[] bytes, String fileName)
    {
        return addBinaryPart(name, bytes, fileName, "application/octet-stream");
    }

    public MultipartEntityBuilder addBinaryPart(String name, byte[] bytes, String fileName, String contentType)
    {
        final ByteArrayPart byteArrayPart = new ByteArrayPart(name, bytes, fileName, contentType);
        parts.add(byteArrayPart);

        return this;
    }

    public MultipartEntityBuilder addBinaryPart(String name, File file)
	{
        String fileName = file.getName();
        return addBinaryPart(name, file, fileName);
	}

    public MultipartEntityBuilder addBinaryPart(String name, File file, String fileName)
    {
        String contentType = extractContentType(file);
        return addBinaryPart(name, file, fileName, contentType);
    }

    private String extractContentType(File file)
	{
        try
        {
            final Path path = Paths.get(file.toURI());

            final String contentType = Files.probeContentType(path);
            return contentType;
        }
        catch (IOException e)
        {
            return "application/octet-stream";
        }
	}

    public MultipartEntityBuilder addBinaryPart(String name, File file, String fileName, String contentType)
	{
        final FilePart filePart = new FilePart(name, file, fileName, contentType);
        parts.add(filePart);

        return this;
	}

    public MultipartEntityBuilder addBinaryPart(String name, InputStream in)
	{
        final String fileName = UUID.randomUUID().toString();
        return addBinaryPart(name, in, fileName);
	}

    public MultipartEntityBuilder addBinaryPart(String name, InputStream in, String fileName)
	{
        return addBinaryPart(name, in, fileName, "application/octet-stream");
	}

    public MultipartEntityBuilder addBinaryPart(String name, InputStream in, String fileName, String contentType)
	{
        final InputStreamPart inPart = new InputStreamPart(name, in, fileName, contentType);
        parts.add(inPart);

        return this;
	}

    public MultipartEntityBuilder addTextPart(String name, String text)
	{
        return addTextPart(name, text, "text/plain");
	}

    public MultipartEntityBuilder addTextPart(String name, String text, String contentType)
	{
        final TextPart textPart = new TextPart(name, text, contentType);
        parts.add(textPart);

        return this;
	}


    public MultipartEntityBuilder addObjectPart(String name, Object obj)
	{
        return addObjectPart(name, obj, "application/json");
	}

    public MultipartEntityBuilder addObjectPart(String name, Object obj, String contentType)
	{
        final String objAsJson = gson.toJson(obj);
        return addTextPart(name, objAsJson, contentType);
	}
}
