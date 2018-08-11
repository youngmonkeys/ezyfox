package com.tvd12.ezyfox.bean.supplier;

import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyConcurrentHashMapSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new ConcurrentHashMap<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return ConcurrentHashMap.class;
	}
	
}
