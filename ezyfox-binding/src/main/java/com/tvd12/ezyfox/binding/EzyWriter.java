package com.tvd12.ezyfox.binding;

public interface EzyWriter<T,R> {
	
	R write(EzyMarshaller marshaller, T object);
	
}
