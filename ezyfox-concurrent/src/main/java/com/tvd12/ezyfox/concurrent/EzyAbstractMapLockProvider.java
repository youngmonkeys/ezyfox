package com.tvd12.ezyfox.concurrent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import com.tvd12.ezyfox.function.EzyFunctions;

public abstract class EzyAbstractMapLockProvider implements EzyMapLockProvider {

	protected final Map<Object, Lock> locks;
	
	public EzyAbstractMapLockProvider() {
		this.locks = newLockMap();
	}
	
	protected abstract Map<Object, Lock> newLockMap();
	
	@Override
	public Lock provideLock(Object key) {
		Lock lock = locks.computeIfAbsent(key, EzyFunctions.NEW_REENTRANT_LOCK_FUNC);
		return lock;
	}
	
	@Override
	public Lock getLock(Object key) {
		Lock lock = locks.get(key);
		return lock;
	}
	
	@Override
	public void removeLock(Object key) {
		this.locks.remove(key);
	}
	
	@Override
	public void removeLocks(Set<?> keys) {
		for(Object key : keys)
			this.locks.remove(key);
	}
	
	@Override
	public int size() {
		int size = locks.size();
		return size;
	}
	
}
