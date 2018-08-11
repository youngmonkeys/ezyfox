package com.tvd12.ezyfox.bean.supplier;

import java.util.LinkedList;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyLinkedListSupplier implements EzyPrototypeSupplier {

	@Override
	public Object supply(EzyBeanContext context) {
		return new LinkedList<>();
	}
	
	@Override
	public Class<?> getObjectType() {
		return LinkedList.class;
	}
	
}
