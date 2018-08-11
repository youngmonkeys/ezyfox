package com.tvd12.ezyfox.bean.supplier;

import java.util.concurrent.CopyOnWriteArraySet;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyCopyOnWriteArraySetSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new CopyOnWriteArraySet<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return CopyOnWriteArraySet.class;
	}
	
}
