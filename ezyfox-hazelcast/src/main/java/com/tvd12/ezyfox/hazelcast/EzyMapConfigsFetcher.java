package com.tvd12.ezyfox.hazelcast;

import com.hazelcast.config.MapConfig;

public interface EzyMapConfigsFetcher {

	Iterable<MapConfig> get();
	
}
