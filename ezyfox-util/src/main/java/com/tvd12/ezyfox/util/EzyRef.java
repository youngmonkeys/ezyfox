package com.tvd12.ezyfox.util;

public class EzyRef<T> {

    protected final T value;
    protected volatile int referenceCount;

    public EzyRef(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void retain() {
        ++this.referenceCount;
    }

    public void release() {
        --this.referenceCount;
    }

    public boolean isReleasable() {
        return this.referenceCount == 0;
    }

}
