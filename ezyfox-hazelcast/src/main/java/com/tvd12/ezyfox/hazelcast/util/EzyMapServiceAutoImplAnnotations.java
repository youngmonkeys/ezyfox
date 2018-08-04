package com.tvd12.ezyfox.hazelcast.util;

import org.apache.commons.lang3.StringUtils;

import com.tvd12.ezyfox.hazelcast.annotation.EzyMapServiceAutoImpl;
import com.tvd12.ezyfox.reflect.EzyClasses;

public final class EzyMapServiceAutoImplAnnotations {

	private EzyMapServiceAutoImplAnnotations() {
	}
	
	public static String getBeanName(Class<?> annotatedClass) {
		EzyMapServiceAutoImpl anno = annotatedClass.getAnnotation(EzyMapServiceAutoImpl.class);
		String beanName = anno.name().trim();
		return StringUtils.isEmpty(beanName) ? EzyClasses.getVariableName(annotatedClass) : beanName;
	}
	
}
