package com.tvd12.ezyfox.concurrent;

import java.util.concurrent.locks.Lock;

import com.tvd12.ezyfox.util.EzyMixedHashMap;
import com.tvd12.ezyfox.util.EzyMixedMap;
import com.tvd12.ezyfox.util.EzyMixedMap.EzyMixedKey;

public class EzyMixedMapLockProxyProvider implements EzyMapLockProvider {

	protected final EzyMixedMap<EzyLockProxy> locks;
	
	public EzyMixedMapLockProxyProvider() {
		this.locks = new EzyMixedHashMap<>();
	}
	
	@Override
	public Lock provideLock(Object key) {
		EzyMixedKey mkey = (EzyMixedKey)key;
		synchronized (locks) {
			EzyLockProxy lock = locks.computeIfAbsent(mkey, EzyLockProxy.SUPPLIER);
			lock.retain();
			return lock;
		}
	}
	
	@Override
	public void removeLock(Object key) {
		EzyMixedKey mkey = (EzyMixedKey)key;
		synchronized (locks) {
			EzyLockProxy lock = locks.get(mkey);
			if(lock != null) {
				lock.release();
				if(lock.isReleasable())
					locks.remove(mkey);
			}
		}
	}
	
}