package com.jivesoftware.boundaries.restz.multipart;

/**
 * Created by bmoshe on 8/31/14.
 */
public abstract class Part
{
    private String name;

    public Part(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
