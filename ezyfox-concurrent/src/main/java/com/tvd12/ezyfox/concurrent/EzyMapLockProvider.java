package com.tvd12.ezyfox.concurrent;

import java.util.concurrent.locks.Lock;

public interface EzyMapLockProvider {
	
	Lock provideLock(Object key);
	
	void removeLock(Object key);
	
}
