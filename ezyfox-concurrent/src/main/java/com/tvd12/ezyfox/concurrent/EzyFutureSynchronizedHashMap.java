package com.tvd12.ezyfox.concurrent;

import java.util.HashMap;
import java.util.Map;

public class EzyFutureSynchronizedHashMap<K> extends EzyFutureAbstractMap<K> {

	@Override
	protected Map<K, EzyFuture> newFutureMap() {
		return new HashMap<>();
	}
	
	@Override
	public EzyFuture addFuture(K key, EzyFuture future) {
		synchronized (map) {
			return super.addFuture(key, future);
		}
	}
	
	@Override
	public EzyFuture putFuture(K key) throws IllegalArgumentException {
		synchronized (map) {
			return super.putFuture(key);
		}
	}
	
	@Override
	public EzyFuture getFuture(K key) {
		synchronized (map) {
			return super.getFuture(key);
		}
	}
	
	@Override
	public EzyFuture removeFuture(K key) {
		synchronized (map) {
			return super.removeFuture(key);
		}
	}
	
	@Override
	public int size() {
		synchronized (map) {
			return super.size();
		}
	}
	
}
