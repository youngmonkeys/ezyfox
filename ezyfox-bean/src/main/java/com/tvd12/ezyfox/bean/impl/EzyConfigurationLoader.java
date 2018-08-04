package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanContext;

public interface EzyConfigurationLoader {

	EzyConfigurationLoader clazz(Class<?> clazz);
	
	EzyConfigurationLoader context(EzyBeanContext context);
	
	void load();
}
