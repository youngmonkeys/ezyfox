package com.tvd12.ezyfox.util;

import java.util.concurrent.atomic.AtomicInteger;

public class EzyRef<T> {

    protected final T value;
    protected final AtomicInteger referenceCount = new AtomicInteger();

    public EzyRef(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void retain() {
        this.referenceCount.incrementAndGet();
    }

    public void release() {
        this.referenceCount.decrementAndGet();
    }

    public boolean isReleasable() {
        return this.referenceCount.get() == 0;
    }
}
