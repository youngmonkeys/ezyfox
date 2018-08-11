package com.tvd12.ezyfox.bean.supplier;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyMapSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new HashMap<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}
	
}
