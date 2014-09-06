package com.jivesoftware.boundaries.restz.usecase.box.api.models;

/**
 * Created by bmoshe on 4/2/14.
 */
public class BoxObject
{
    private String id;

    public BoxObject()
    {
    }

    public BoxObject(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
