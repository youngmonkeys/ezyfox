package com.tvd12.ezyfox.io;

public interface EzyOutputTransformer {

	@SuppressWarnings("rawtypes")
	Object transform(Object value, Class type);
	
}
