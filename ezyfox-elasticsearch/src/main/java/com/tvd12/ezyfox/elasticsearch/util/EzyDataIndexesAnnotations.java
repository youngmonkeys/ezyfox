package com.tvd12.ezyfox.elasticsearch.util;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.elasticsearch.annotation.EzyDataIndex;
import com.tvd12.ezyfox.elasticsearch.annotation.EzyDataIndexes;
import com.tvd12.ezyfox.util.EzyNameStyles;

public final class EzyDataIndexesAnnotations {

	private static final Logger LOGGER 
			= LoggerFactory.getLogger(EzyDataIndexesAnnotations.class);
	
	private EzyDataIndexesAnnotations() {
	}
	
	public static Set<String> getIndexes(Class<?> clazz) {
		EzyDataIndexes indexesAnno = clazz.getAnnotation(EzyDataIndexes.class);
		if(indexesAnno != null)
			return EzyDataIndexesAnnotations.getIndexTypes(clazz, indexesAnno);
		
		EzyDataIndex indexAnno = clazz.getAnnotation(EzyDataIndex.class);
		if(indexAnno != null)
			return EzyDataIndexAnnotations.getIndexes(clazz, indexAnno);
		
		warningAnnotationNotFound(clazz);
		
		String className = clazz.getSimpleName();
		String hyphenClassName = EzyNameStyles.toLowerHyphen(className);
		return Sets.newHashSet(hyphenClassName);
	}
	
	private static Set<String> getIndexTypes(Class<?> clazz, EzyDataIndexes anno) {
		Set<String> set = Sets.newHashSet(anno.value());
		return set;
	}
	
	private static void warningAnnotationNotFound(Class<?> clazz) {
		LOGGER.warn("{} was not annotated by @EzyDataIndexes or @EzyDataIndex, use class name by default");
	}
	
}
