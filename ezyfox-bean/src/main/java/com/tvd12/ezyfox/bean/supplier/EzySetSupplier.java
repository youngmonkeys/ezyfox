package com.tvd12.ezyfox.bean.supplier;

import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public final class EzySetSupplier implements EzyPrototypeSupplier {

	private static final EzySetSupplier INSTANCE = new EzySetSupplier();

	private EzySetSupplier() {}

	public static EzySetSupplier getInstance() {
		return INSTANCE;
	}

	@Override
	public Object supply(EzyBeanContext context) {
		return new HashSet<>();
	}

	@Override
	public Class<?> getObjectType() {
		return Set.class;
	}

}
