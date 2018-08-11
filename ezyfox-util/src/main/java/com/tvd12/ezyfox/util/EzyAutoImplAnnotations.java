package com.tvd12.ezyfox.util;

import org.apache.commons.lang3.StringUtils;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.util.EzyKeyValueAnnotations;

public final class EzyAutoImplAnnotations {

	private EzyAutoImplAnnotations() {
	}
	
	public static String getBeanName(Class<?> annotatedClass) {
		EzyAutoImpl anno = annotatedClass.getAnnotation(EzyAutoImpl.class);
		String beanName = anno.value().trim();
		if(!StringUtils.isEmpty(beanName)) 
			return beanName;
		beanName = EzyKeyValueAnnotations.getProperty("name", anno.properties());
		if(!StringUtils.isEmpty(beanName))
			return beanName;
		return EzyClasses.getVariableName(annotatedClass);
	}
	
}
