package com.tvd12.ezyfox.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tvd12.ezyfox.concurrent.exception.EzyFutureExistedException;

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
		if(existed.get())
			throw new EzyFutureExistedException(key, future);
		return future;
	}
	
	@Override
	public EzyFuture getFuture(K key) {
		EzyFuture future = map.get(key);
		return future;
	}
	
	@Override
	public EzyFuture removeFuture(K key) {
		EzyFuture future = map.remove(key);
		return future;
	}
	
	@Override
	public int size() {
		int size = map.size();
		return size;
	}
	
	@Override
	public Map<K, EzyFuture> clear() {
		Map<K, EzyFuture> answer = new HashMap<>(map);
		map.clear();
		return answer;
	}
	
}
