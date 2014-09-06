package com.jivesoftware.boundaries.restz;

/**
 * Created by bmoshe on 9/1/14.
 */
public interface ExecutorFactory
{
    Executor get();

    void debug();
}
