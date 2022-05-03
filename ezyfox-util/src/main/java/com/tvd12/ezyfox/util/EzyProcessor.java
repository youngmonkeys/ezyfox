package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.constant.EzyLogLevel;
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
        } catch (Exception ignored) {
            // do nothing
        }
    }

    public static void processWithException(EzyExceptionVoid applier) {
        processWithException(applier, IllegalStateException::new);
    }

    public static void processWithException(
        EzyExceptionVoid applier,
        Function<Throwable, RuntimeException> handler
    ) {
        try {
            applier.apply();
        } catch (Exception e) {
            throw handler.apply(e);
        }
    }

    public static void processWithIllegalArgumentException(EzyExceptionVoid applier) {
        processWithException(applier, IllegalArgumentException::new);
    }

    public static void processWithLogException(EzyExceptionVoid applier) {
        processWithLogException(applier, false);
    }

    public static void processWithLogException(EzyExceptionVoid applier, boolean warn) {
        processWithLogException(
            applier,
            warn ? EzyLogLevel.WARN : EzyLogLevel.INFO
        );
    }

    public static void processWithLogException(
        EzyExceptionVoid applier,
        EzyLogLevel logLevel
    ) {
        try {
            applier.apply();
        } catch (Exception e) {
            if (logLevel == EzyLogLevel.TRACE) {
                LOGGER.trace("can't process " + applier, e);
            } else if (logLevel == EzyLogLevel.DEBUG) {
                LOGGER.debug("can't process " + applier, e);
            } else if (logLevel == EzyLogLevel.INFO) {
                LOGGER.info("can't process " + applier, e);
            } else if (logLevel == EzyLogLevel.WARN) {
                LOGGER.warn("can't process " + applier, e);
            } else {
                LOGGER.error("can't process " + applier, e);
            }
        }
    }

    public static void processWithSync(EzyVoid applier, Object context) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
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
        EzyVoid applier,
        Lock lock,
        long time
    ) throws InterruptedException {
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
}
