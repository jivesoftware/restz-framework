package com.jivesoftware.boundaries.restz.multipart;

/**
 * Created by bmoshe on 8/31/14.
 */
public class ByteArrayPart
extends BinaryPart<byte[]>
{
    public ByteArrayPart(String name, byte[] content, String fileName, String contentType)
    {
        super(name, content, fileName, contentType);
    }
}
