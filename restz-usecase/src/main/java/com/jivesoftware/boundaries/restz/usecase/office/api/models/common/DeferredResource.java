package com.jivesoftware.boundaries.restz.usecase.office.api.models.common;

/**
* Created by bmoshe on 8/24/14.
*/
public class DeferredResource
{
    private Deferred __deferred;

    public Deferred get__deferred()
    {
        return __deferred;
    }

    public void set__deferred(Deferred __deferred)
    {
        this.__deferred = __deferred;
    }

    public class Deferred
    {
        private String uri;

        public String getUri()
        {
            return uri;
        }

        public void setUri(String uri)
        {
            this.uri = uri;
        }
    }
}
