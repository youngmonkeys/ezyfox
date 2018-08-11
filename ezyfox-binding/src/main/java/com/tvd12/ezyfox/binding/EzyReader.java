package com.tvd12.ezyfox.binding;

public interface EzyReader<T,R> {
	
	R read(EzyUnmarshaller unmarshaller, T value);
	
}
