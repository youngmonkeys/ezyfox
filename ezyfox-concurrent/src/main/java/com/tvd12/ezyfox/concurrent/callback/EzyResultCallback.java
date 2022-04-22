package com.tvd12.ezyfox.concurrent.callback;

public interface EzyResultCallback<T> {

    void onResponse(T response);

    default void onException(Exception e) {};
}