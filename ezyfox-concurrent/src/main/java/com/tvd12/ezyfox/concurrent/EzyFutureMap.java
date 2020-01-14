package com.tvd12.ezyfox.concurrent;

import java.util.Map;

public interface EzyFutureMap<K> {

	EzyFuture addFuture(K key);
	
	EzyFuture addFuture(K key, EzyFuture future);
	
	EzyFuture putFuture(K key) throws IllegalArgumentException;
	
	EzyFuture getFuture(K key);
	
	EzyFuture removeFuture(K key);

	int size();
	
	Map<K, EzyFuture> clear();
	
}
