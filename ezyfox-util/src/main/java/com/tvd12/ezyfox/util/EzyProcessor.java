package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.function.EzyExceptionVoid;
import com.tvd12.ezyfox.function.EzyVoid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

public final class EzyProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EzyProcessor.class);

    private EzyProcessor() {}

    public static void processSilently(EzyExceptionVoid applier) {
        try {
            applier.apply();
        } catch (Exception e) {
        }
    }

    public static void processWithException(EzyExceptionVoid applier) {
        processWithException(applier, e -> new IllegalStateException(e));
    }

    public static void processWithIllegalArgumentException(EzyExceptionVoid applier) {
        processWithException(applier, e -> new IllegalArgumentException(e));
    }

    public static void processWithException(
        EzyExceptionVoid applier, Function<Throwable, RuntimeException> handler) {
        try {
            applier.apply();
        } catch (Exception e) {
            throw handler.apply(e);
        }
    }

    public static void processWithLogException(EzyExceptionVoid applier) {
        processWithLogException(applier, false);
    }

    public static void processWithLogException(EzyExceptionVoid applier, boolean warn) {
        try {
            applier.apply();
        } catch (Exception e) {
            if (warn) {
                warn("can't process " + applier, e);
            } else {
                debug("can't process " + applier, e);
            }
        }
    }

    public static void processWithSync(EzyVoid applier, Object context) {
        synchronized (context) {
            applier.apply();
        }
    }

    public static void processWithLock(EzyVoid applier, Lock lock) {
        lock.lock();
        try {
            applier.apply();
        } finally {
            lock.unlock();
        }
    }

    public static void processWithTryLock(EzyVoid applier, Lock lock) {
        if (!lock.tryLock()) {
            return;
        }
        try {
            applier.apply();
        } finally {
            lock.unlock();
        }
    }

    public static void processWithTryLock(
        EzyVoid applier, Lock lock, long time) throws InterruptedException {
        if (!tryLock(lock, time)) {
            return;
        }
        try {
            applier.apply();
        } finally {
            lock.unlock();
        }
    }

    private static boolean tryLock(Lock lock, long time) throws InterruptedException {
        return lock.tryLock(time, TimeUnit.MILLISECONDS);
    }

    private static void debug(String message, Throwable throwable) {
        LOGGER.debug(message, throwable);
    }

    private static void warn(String message, Throwable throwable) {
        LOGGER.warn(message, throwable);
    }
}
