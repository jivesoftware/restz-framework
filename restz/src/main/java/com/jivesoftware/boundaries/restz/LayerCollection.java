package com.jivesoftware.boundaries.restz;

import com.jivesoftware.boundaries.restz.layers.ExecutionWrapperLayer;
import com.jivesoftware.boundaries.restz.layers.RecoverableFailureLayer;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by bmoshe on 4/8/14.
 */
public class LayerCollection
{
    private Collection<ExecutionWrapperLayer> wrappers;
    private Collection<RecoverableFailureLayer> recoverables;

    public LayerCollection()
    {
        wrappers = new LinkedList<>();
        recoverables = new LinkedList<>();
    }

    public LayerCollection(Layer... layers)
    {
        this();
        add(layers);
    }

    public void add(Layer... layers)
    {
        for(Layer layer : layers)
        {
            if (layer instanceof ExecutionWrapperLayer)
                wrappers.add((ExecutionWrapperLayer) layer);

            if (layer instanceof RecoverableFailureLayer)
                recoverables.add((RecoverableFailureLayer) layer);
        }
    }

    public void remove(Layer... layers)
    {
        for(Layer layer : layers)
        {
            if (layer instanceof ExecutionWrapperLayer)
                wrappers.remove(layer);

            if (layer instanceof RecoverableFailureLayer)
                recoverables.remove(layer);
        }
    }

    public Collection<ExecutionWrapperLayer> getWrappers()
    {
        return wrappers;
    }

    public Collection<RecoverableFailureLayer> getRecoverables()
    {
        return recoverables;
    }
}
