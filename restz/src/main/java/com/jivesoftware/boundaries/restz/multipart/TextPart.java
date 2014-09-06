package com.jivesoftware.boundaries.restz.multipart;

/**
 * Created by bmoshe on 8/31/14.
 */
public class TextPart
extends Part
{
    private String text;
    private String contentType;

    public TextPart(String name, String text)
    {
        this(name, text, "text/plain");
    }

    public TextPart(String name, String text, String contentType)
    {
        super(name);

        this.text = text;
        this.contentType = contentType;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
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
