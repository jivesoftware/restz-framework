package com.jivesoftware.boundaries.restz.usecase.box.api.models;

/**
 * Created by bmoshe on 4/2/14.
 */
public class BoxCreateFolderRequestObject
{
    public String name;
    public BoxObject parent;

    public BoxCreateFolderRequestObject()
    {
    }

    public BoxCreateFolderRequestObject(String name, String parentId)
    {
        this.name = name;
        this.parent = new BoxObject(parentId);
    }

}
