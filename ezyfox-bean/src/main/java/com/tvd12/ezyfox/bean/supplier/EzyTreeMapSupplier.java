package com.tvd12.ezyfox.bean.supplier;

import java.util.TreeMap;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyTreeMapSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new TreeMap<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return TreeMap.class;
	}
	
}
