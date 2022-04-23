package com.tvd12.ezyfox.concurrent;

import com.tvd12.ezyfox.concurrent.exception.EzyFutureExistedException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class EzyFutureAbstractMap<K> implements EzyFutureMap<K> {

    protected final Map<K, EzyFuture> map;

    public EzyFutureAbstractMap() {
        this.map = newFutureMap();
    }

    protected abstract Map<K, EzyFuture> newFutureMap();

    @Override
    public EzyFuture addFuture(K key) {
        return addFuture(key, new EzyFutureTask());
    }

    @Override
    public EzyFuture addFuture(K key, EzyFuture future) {
        EzyFuture old = map.putIfAbsent(key, future);
        return old == null ? future : old;
    }

    @Override
    public EzyFuture putFuture(K key) {
        AtomicBoolean existed = new AtomicBoolean(true);
        EzyFuture future = map.computeIfAbsent(key, k -> {
            existed.set(false);
            return new EzyFutureTask();
        });
        if (existed.get()) {
            throw new EzyFutureExistedException(key, future);
        }
        return future;
    }

    @Override
    public EzyFuture getFuture(K key) {
        return map.get(key);
    }

    @Override
    public EzyFuture removeFuture(K key) {
        return map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Map<K, EzyFuture> clear() {
        Map<K, EzyFuture> answer = new HashMap<>(map);
        map.clear();
        return answer;
    }
}
