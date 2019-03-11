package com.tvd12.ezyfox.bean.supplier;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public final class EzyMapSupplier implements EzyPrototypeSupplier {

	private static final EzyMapSupplier INSTANCE = new EzyMapSupplier();

	private EzyMapSupplier() {
	}

	public static EzyMapSupplier getInstance() {
		return INSTANCE;
	}

	@Override
	public Object supply(EzyBeanContext context) {
		return new HashMap<>();
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}
