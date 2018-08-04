package com.tvd12.ezyfox.database.mapservice;

public interface EzyFetchByKeyService<K,V> {

	V get(K key);
	
}
