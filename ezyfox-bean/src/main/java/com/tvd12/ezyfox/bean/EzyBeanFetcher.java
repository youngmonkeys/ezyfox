package com.tvd12.ezyfox.bean;

public interface EzyBeanFetcher {

	Object getBean(Class<?> type);
	
	Object getBean(String name, Class<?> type);
	
}
