package com.jivesoftware.boundaries.restz.multipart;

import java.io.InputStream;

/**
 * Created by bmoshe on 8/31/14.
 */
public class InputStreamPart
extends BinaryPart<InputStream>
{
    public InputStreamPart(String name, InputStream content, String fileName, String contentType)
    {
        super(name, content, fileName, contentType);
    }
}
