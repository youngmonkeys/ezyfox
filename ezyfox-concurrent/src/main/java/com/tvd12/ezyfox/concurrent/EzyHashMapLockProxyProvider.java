package com.tvd12.ezyfox.concurrent;

import java.util.HashMap;
import java.util.Map;

public class EzyHashMapLockProxyProvider extends EzyMapLockProxyProvider {

	@Override
	protected Map<Object, EzyLockProxy> newLockMap() {
		return new HashMap<>();
	}
	
}
