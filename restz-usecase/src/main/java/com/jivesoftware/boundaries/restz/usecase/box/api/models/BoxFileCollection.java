package com.jivesoftware.boundaries.restz.usecase.box.api.models;

/**
 * Created with IntelliJ IDEA.
 * User: varkel
 * Date: 2/6/13
 * Time: 4:25 PM
 */
public class BoxFileCollection
{
    int total_count;
    BoxFile[] entries;

    public int getTotal_count()
    {
        return total_count;
    }

    public void setTotal_count(int total_count)
    {
        this.total_count = total_count;
    }

    public BoxFile[] getEntries()
    {
        return entries;
    }

    public void setEntries(BoxFile[] entries)
    {
        this.entries = entries;
    }
}
