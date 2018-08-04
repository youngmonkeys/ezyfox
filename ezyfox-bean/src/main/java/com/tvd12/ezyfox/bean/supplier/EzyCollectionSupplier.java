package com.tvd12.ezyfox.bean.supplier;

import java.util.ArrayList;
import java.util.Collection;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyCollectionSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new ArrayList<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return Collection.class;
	}
	
}
