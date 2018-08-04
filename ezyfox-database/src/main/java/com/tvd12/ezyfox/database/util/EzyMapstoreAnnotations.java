package com.tvd12.ezyfox.database.util;

import static com.tvd12.ezyfox.reflect.EzyClasses.getVariableName;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.tvd12.ezyfox.database.annotation.EzyMapstore;

public final class EzyMapstoreAnnotations {

	private EzyMapstoreAnnotations() {
	}
	
	public static String getMapName(Class<?> clazz) {
		EzyMapstore anno = clazz.getAnnotation(EzyMapstore.class);
		String name = anno.value();
		return isEmpty(name) ? getVariableName(clazz) : name;
	}
	
}
