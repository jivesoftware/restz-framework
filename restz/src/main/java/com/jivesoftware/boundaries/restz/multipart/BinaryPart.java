package com.jivesoftware.boundaries.restz.multipart;

/**
 * Created by bmoshe on 8/31/14.
 */
public class BinaryPart<T>
extends Part
{
    private T content;

    private String fileName;
    private String contentType;

    public BinaryPart(String name, T content, String fileName, String contentType)
    {
        super(name);

        this.content = content;

        this.fileName = fileName;
        this.contentType = contentType;
    }

    public T getContent()
    {
        return content;
    }

    public void setContent(T content)
    {
        this.content = content;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
}