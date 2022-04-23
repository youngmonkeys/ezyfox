package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.function.EzySupplier;

import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;

public final class EzyReturner {

    private EzyReturner() {}

    public static <T> T returnNotNull(T rvalue, T svalue) {
        return rvalue != null ? rvalue : svalue;
    }

    public static <T> T returnAndApply(T rvalue, Runnable applier) {
        applier.run();
        return rvalue;
    }

    public static <T> T returnWithException(EzySupplier<T> supplier) {
        return returnWithException(supplier, IllegalStateException::new);
    }

    public static <T> T returnWithException(
        EzySupplier<T> supplier,
        Function<Throwable, RuntimeException> handler
    ) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw handler.apply(e);
        }
    }

    public static <T> T returnWithIllegalArgumentException(EzySupplier<T> supplier) {
        return returnWithException(supplier, e -> new IllegalArgumentException(e));
    }

    public static <T> T returnWithSync(Supplier<T> supplier, Object context) {
        synchronized (context) {
            return supplier.get();
        }
    }

    public static <T> T returnWithLock(Supplier<T> supplier, Lock lock) {
        lock.lock();
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }
}
