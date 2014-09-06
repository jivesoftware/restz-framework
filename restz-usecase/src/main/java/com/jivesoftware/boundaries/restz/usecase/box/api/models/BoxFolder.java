package com.jivesoftware.boundaries.restz.usecase.box.api.models;

/**
 * Created by bmoshe on 4/2/14.
 */
public class BoxFolder
extends BoxObject
{
    final String type = "folder";

    private String sequence_id;
    private String etag;
    private String name;

    private BoxFolder parent;

    public String getType()
    {
        return type;
    }

    public String getSequence_id()
    {
        return sequence_id;
    }

    public void setSequence_id(String sequence_id)
    {
        this.sequence_id = sequence_id;
    }

    public String getEtag()
    {
        return etag;
    }

    public void setEtag(String etag)
    {
        this.etag = etag;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BoxFolder getParent()
    {
        return parent;
    }

    public void setParent(BoxFolder parent)
    {
        this.parent = parent;
    }
}
