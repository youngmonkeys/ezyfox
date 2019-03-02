package com.tvd12.ezyfox.io;

import java.io.Serializable;
import java.util.Collection;

@SuppressWarnings({"rawtypes"})
public interface EzyCollectionConverter extends Serializable {
	
	<T> T toArray(Collection coll, Class type);
	
}
