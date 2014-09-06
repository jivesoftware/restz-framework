package com.jivesoftware.boundaries.restz.usecase.box.api.models;

/**
 * Created with IntelliJ IDEA.
 * User: bmoshe
 * Date: 2/5/13
 * Time: 10:24 AM
 *
 */
public class BoxFile
extends BoxObject
{
    private String name;
    private String description;
    private long size;

    private String etag;
    private String sequence_id;

    private BoxFolder parent;

    private BoxUser created_by;
    private BoxUser modified_by;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String getEtag()
    {
        return etag;
    }

    public void setEtag(String etag)
    {
        this.etag = etag;
    }

    public String getSequence_id()
    {
        return sequence_id;
    }

    public void setSequence_id(String sequence_id)
    {
        this.sequence_id = sequence_id;
    }

    public BoxFolder getParent()
    {
        return parent;
    }

    public void setParent(BoxFolder parent)
    {
        this.parent = parent;
    }

    public BoxUser getCreated_by()
    {
        return created_by;
    }

    public void setCreated_by(BoxUser created_by)
    {
        this.created_by = created_by;
    }

    public BoxUser getModified_by()
    {
        return modified_by;
    }

    public void setModified_by(BoxUser modified_by)
    {
        this.modified_by = modified_by;
    }

}
