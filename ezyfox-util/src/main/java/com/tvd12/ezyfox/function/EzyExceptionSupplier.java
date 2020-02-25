package com.tvd12.ezyfox.function;

public interface EzyExceptionSupplier<T> {
	
	T get() throws Exception;
	
}
