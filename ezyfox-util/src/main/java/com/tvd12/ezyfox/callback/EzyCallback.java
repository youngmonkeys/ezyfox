package com.tvd12.ezyfox.callback;

public interface EzyCallback<T> {

    void call(T data);

    default void call(T data, int dataSize) {
        call(data);
    }

}
