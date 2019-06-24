package com.tvd12.ezyfox.concurrent;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;

public class EzyTreeMapLockProvider extends EzyAbstractMapLockProvider {

	@Override
	protected Map<Object, Lock> newLockMap() {
		return new TreeMap<>();
	}
	
}
