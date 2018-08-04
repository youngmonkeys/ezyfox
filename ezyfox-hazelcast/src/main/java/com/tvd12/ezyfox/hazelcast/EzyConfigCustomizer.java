package com.tvd12.ezyfox.hazelcast;

import com.hazelcast.config.Config;

public interface EzyConfigCustomizer {
	
	void customize(Config config);
	
}
