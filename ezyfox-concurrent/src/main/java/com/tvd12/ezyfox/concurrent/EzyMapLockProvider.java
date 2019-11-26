package com.tvd12.ezyfox.concurrent;

import java.util.Set;
import java.util.concurrent.locks.Lock;

public interface EzyMapLockProvider {
	
	Lock provideLock(Object key);
	
	Lock getLock(Object key);
	
	void removeLock(Object key);
	
	void removeLocks(Set<?> keys);
	
	int size();
	
}
