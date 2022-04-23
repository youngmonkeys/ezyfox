package com.tvd12.ezyfox.concurrent.exception;

import com.tvd12.ezyfox.concurrent.EzyFuture;

public class EzyFutureExistedException extends IllegalArgumentException {
    private static final long serialVersionUID = 4258315197030448654L;

    public EzyFutureExistedException(Object key, EzyFuture old) {
        super("future with key: " + key + " existed: " + old);
    }
}
