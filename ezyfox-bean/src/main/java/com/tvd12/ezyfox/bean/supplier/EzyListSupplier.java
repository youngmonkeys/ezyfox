package com.tvd12.ezyfox.bean.supplier;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public class EzyListSupplier implements EzyPrototypeSupplier {

	private static final EzyListSupplier INSTANCE = new EzyListSupplier();

	private EzyListSupplier() {
	}

	public static EzyListSupplier getInstance() {
		return INSTANCE;
	}

	@Override
	public Object supply(EzyBeanContext context) {
		return new ArrayList<>();
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}
