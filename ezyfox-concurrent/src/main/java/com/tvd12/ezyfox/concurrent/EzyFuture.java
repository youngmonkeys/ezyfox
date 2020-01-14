package com.tvd12.ezyfox.concurrent;

public interface EzyFuture {

	void setResult(Object result);
	
	void setException(Exception exception);
	
	void cancel(String message);
	
	boolean isDone();

	<V> V get() throws Exception;

	<V> V get(long timeout) throws Exception;

}
