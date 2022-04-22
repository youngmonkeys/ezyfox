package com.tvd12.ezyfox.function;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public final class EzyFunctions {

    public static final Function<Object, Lock> NEW_REENTRANT_LOCK_FUNC = p -> new ReentrantLock();

    private EzyFunctions() {
    }

    public static Function<Object, Lock> newReentrantLockFunc() {
        return NEW_REENTRANT_LOCK_FUNC;
    }

}
