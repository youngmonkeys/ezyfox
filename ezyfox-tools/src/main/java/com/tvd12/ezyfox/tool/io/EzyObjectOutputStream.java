package com.tvd12.ezyfox.tool.io;

import com.tvd12.ezyfox.util.EzyCloseable;

public interface EzyObjectOutputStream<T> extends EzyCloseable {

    void write(T t);

    @Override
    default void close() {
    }
}