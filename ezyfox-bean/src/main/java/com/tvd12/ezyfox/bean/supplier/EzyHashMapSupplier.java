package com.tvd12.ezyfox.bean.supplier;

import java.util.HashMap;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyHashMapSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new HashMap<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return HashMap.class;
	}
	
}
