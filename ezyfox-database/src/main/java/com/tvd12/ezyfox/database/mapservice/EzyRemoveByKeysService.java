package com.tvd12.ezyfox.database.mapservice;

public interface EzyRemoveByKeysService<K> {

	void remove(Iterable<K> keys);
	
}
