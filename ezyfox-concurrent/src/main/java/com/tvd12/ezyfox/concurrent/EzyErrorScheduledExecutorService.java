package com.tvd12.ezyfox.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@SuppressWarnings("ALL")
public class EzyErrorScheduledExecutorService implements ScheduledExecutorService {

    private final String errorMessage;

    public EzyErrorScheduledExecutorService(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public boolean isShutdown() {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public boolean isTerminated() {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public Future<?> submit(Runnable task) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public <T> List<Future<T>> invokeAll(
        Collection<? extends Callable<T>> tasks
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public <T> List<Future<T>> invokeAll(
        Collection<? extends Callable<T>> tasks,
        long timeout,
        TimeUnit unit
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public <T> T invokeAny(
        Collection<? extends Callable<T>> tasks
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public <T> T invokeAny(
        Collection<? extends Callable<T>> tasks,
        long timeout,
        TimeUnit unit
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public void execute(Runnable command) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public ScheduledFuture<?> schedule(
        Runnable command,
        long delay,
        TimeUnit unit
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(
        Callable<V> callable,
        long delay,
        TimeUnit unit
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(
        Runnable command,
        long initialDelay,
        long period,
        TimeUnit unit
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(
        Runnable command,
        long initialDelay,
        long delay,
        TimeUnit unit
    ) {
        throw new UnsupportedOperationException(errorMessage);
    }
}
