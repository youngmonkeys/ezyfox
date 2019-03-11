package com.tvd12.ezyfox.bean.supplier;

import java.util.TreeMap;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public final class EzyTreeMapSupplier implements EzyPrototypeSupplier {

	private static final EzyTreeMapSupplier INSTANCE = new EzyTreeMapSupplier();
	
	private EzyTreeMapSupplier() {}
	
	public static EzyTreeMapSupplier getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Object supply(EzyBeanContext context) {
		return new TreeMap<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return TreeMap.class;
	}
	
}
