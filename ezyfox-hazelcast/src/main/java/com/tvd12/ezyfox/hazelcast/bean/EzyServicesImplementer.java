package com.tvd12.ezyfox.hazelcast.bean;

import java.util.Map;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.hazelcast.impl.EzySimpleServicesImplementer;

public interface EzyServicesImplementer {
	
	public static EzyServicesImplementer servicesImplementer() {
		return new EzySimpleServicesImplementer();
	}
	
	public abstract EzyServicesImplementer scan(String packageName);
	
	public abstract EzyServicesImplementer scan(String... packageNames);
	
	public abstract EzyServicesImplementer scan(Iterable<String> packageNames);
	
	public abstract EzyServicesImplementer serviceInterface(String mapName, Class<?> itf);
	
	public abstract Map<Class<?>, Object> implement(HazelcastInstance hzInstance);
	
}
