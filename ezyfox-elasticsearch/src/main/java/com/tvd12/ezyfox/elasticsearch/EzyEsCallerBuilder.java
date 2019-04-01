package com.tvd12.ezyfox.elasticsearch;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsUncaughtExceptionHandler;

@SuppressWarnings("rawtypes")
public interface EzyEsCallerBuilder extends EzyBuilder<EzyEsCaller> {

	EzyEsSimpleCallerBuilder maxQueueSize(int maxQueueSize);
	
	EzyEsSimpleCallerBuilder threadPoolSize(int threadPoolSize);
	
	EzyEsSimpleCallerBuilder scanIndexedClasses(String packageToScan);
	
	EzyEsSimpleCallerBuilder clientProxy(EzyEsClientProxy clientProxy);
	
	EzyEsCallerBuilder addActionHandler(String actionType, EzyEsActionHandler handler);
	
	EzyEsSimpleCallerBuilder uncaughtExceptionHandler(EzyEsUncaughtExceptionHandler uncaughtExceptionHandler);

}
