package com.tvd12.ezyfox.bean.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.reflect.EzyClass;

@SuppressWarnings("rawtypes")
public class EzyByConstructorSingletonLoader
		extends EzySimpleSingletonLoader
		implements EzySingletonLoader {

	protected final Constructor<?> constructor;
	
	protected EzyByConstructorSingletonLoader(EzyClass clazz) {
		this(clazz, new ArrayList<>());
	}
	
	protected EzyByConstructorSingletonLoader(EzyClass clazz, List<Class<?>> stackCallClasses) {
		super(clazz, stackCallClasses);
		this.constructor = getConstructor(clazz);
	}
	
	@Override
	protected String[] getConstructorArgumentNames() {
		return getConstructorArgumentNames(constructor);
	}
	
	@Override
	protected Class<?>[] getConstructorParameterTypes() {
		return constructor.getParameterTypes();
	}
	
	@Override
	protected Object newSingletonByConstructor(
			EzyBeanContext context, Class[] parameterTypes) throws Exception {
		if(parameterTypes.length == 0)
			return clazz.newInstance();
		return constructor.newInstance(getArguments(parameterTypes, context));
	}
	
}
