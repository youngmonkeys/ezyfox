package com.tvd12.ezyfox.concurrent.callback;

public interface EzyResultCallback<T> {

	void onResponse(T response);
	
	void onException(Exception e);
	
}
