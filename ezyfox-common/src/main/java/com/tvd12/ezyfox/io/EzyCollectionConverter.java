package com.tvd12.ezyfox.io;

import java.util.Collection;

@SuppressWarnings({"rawtypes"})
public interface EzyCollectionConverter {
	
	<T> T toArray(Collection coll, Class type);
	
}
