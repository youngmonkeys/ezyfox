package com.tvd12.ezyfox.bean.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyMethod;

@SuppressWarnings("rawtypes")
public class EzyByMethodSingletonLoader
		extends EzySimpleSingletonLoader
		implements EzySingletonLoader {

	protected final EzyMethod method;

	public EzyByMethodSingletonLoader(
			EzyMethod method, 
			Object configurator, 
			Map<Class<?>, EzyMethod> methodsByType) {
		this(method, configurator, methodsByType, new ArrayList<>());
	}
	
	public EzyByMethodSingletonLoader(
			EzyMethod method, 
			Object configurator, 
			Map<Class<?>, EzyMethod> methodsByType, List<Class<?>> stackCallClasses) {
		super(new EzyClass(method.getReturnType()), configurator, methodsByType, stackCallClasses);
		this.method = method;
	}
	
	@Override
	protected Map getAnnotationProperties() {
		return EzyKeyValueParser.getSingletonProperties(
				method.getAnnotation(EzySingleton.class));
	}
	
	@Override
	protected String getSingletonName() {
		return EzyBeanNameParser.getSingletonName(
				method.getAnnotation(EzySingleton.class), method.getFieldName());
	}
	
	@Override
	protected Class<?>[] getConstructorParameterTypes() {
		return method.getParameterTypes();
	}
	
	@Override
	protected Object newSingletonByConstructor(
			EzyBeanContext context, Class[] parameterTypes) throws Exception {
		if(parameterTypes.length == 0)
			return method.invoke(configurator);
		return method.invoke(configurator, getArguments(parameterTypes, context));
	}
	
	@Override
	protected Class[] getConstructorParameterTypes(Class clazz) {
		EzyMethod method = methodsByType.get(clazz);
		return method != null ? method.getParameterTypes() : new Class[0]; 
	}
	
}
