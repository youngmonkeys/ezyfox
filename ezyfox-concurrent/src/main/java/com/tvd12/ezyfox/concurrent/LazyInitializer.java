package com.tvd12.ezyfox.concurrent;

public abstract class LazyInitializer<T> {

    protected T object;

    public T get() {
        if(object == null)
            return synGet();
        return object;
    }

    protected T synGet() {
        synchronized (this) {
            if(object == null)
                object = initialize();
        }
        return object;
    }

    protected abstract T initialize();
}