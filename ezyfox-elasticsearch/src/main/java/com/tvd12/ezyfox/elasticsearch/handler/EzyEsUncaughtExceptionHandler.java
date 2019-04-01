package com.tvd12.ezyfox.elasticsearch.handler;

import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;

public interface EzyEsUncaughtExceptionHandler {
	
	void uncaughtException(EzyEsAction action, Throwable e);
	
}
