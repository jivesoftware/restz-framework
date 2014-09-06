package com.jivesoftware.boundaries.restz.multipart;

import java.io.File;

/**
 * Created by bmoshe on 8/31/14.
 */
public class FilePart
extends BinaryPart<File>
{
    public FilePart(String name, File content, String fileName, String contentType)
    {
        super(name, content, fileName, contentType);
    }
}
