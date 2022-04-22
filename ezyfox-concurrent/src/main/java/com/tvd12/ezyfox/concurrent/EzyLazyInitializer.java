package com.tvd12.ezyfox.concurrent;

import com.tvd12.ezyfox.function.EzyInitialize;

public class EzyLazyInitializer<T> extends LazyInitializer<T> {

    private EzyInitialize<T> initializer;

    public EzyLazyInitializer(EzyInitialize<T> initializer) {
        this.initializer = initializer;
    }

    @Override
    protected T initialize() {
        return initializer.init();
    }
}