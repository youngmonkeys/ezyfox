package com.tvd12.ezyfox.bean.supplier;

import java.util.ArrayList;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyArrayListSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new ArrayList<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return ArrayList.class;
	}
	
}
