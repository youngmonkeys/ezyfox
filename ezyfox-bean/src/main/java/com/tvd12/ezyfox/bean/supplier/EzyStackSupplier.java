package com.tvd12.ezyfox.bean.supplier;

import java.util.Stack;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyStackSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new Stack<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return Stack.class;
	}
	
}
