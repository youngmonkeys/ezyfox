package com.tvd12.ezyfox.elasticsearch.util;

import static com.tvd12.ezyfox.util.EzyNameStyles.toLowerHyphen;

import com.tvd12.ezyfox.elasticsearch.EzyEsIndexType;
import com.tvd12.ezyfox.elasticsearch.EzyEsIndexTypes;
import com.tvd12.ezyfox.elasticsearch.annotation.EzyDataIndex;

public final class EzyDataIndexAnnotations {

	private EzyDataIndexAnnotations() {
	}
	
	public static String[] getTypes(Class<?> clazz, EzyDataIndex anno) {
		String[] types = anno.types();
		if(types.length > 0)
			return anno.types();
		return new String[] {toLowerHyphen(clazz.getSimpleName())};
	}
	
	public static EzyEsIndexTypes getIndexTypes(Class<?> clazz, EzyDataIndex anno) {
		String index = anno.index();
		String[] types = getTypes(clazz, anno);
		EzyEsIndexTypes.Builder builder = EzyEsIndexTypes.builder();
		for(String type : types) {
			builder.add(new EzyEsIndexType(index, type));
		}
		EzyEsIndexTypes indexTypes = builder.build();
		return indexTypes;
	}
	
}
