package com.tvd12.ezyfox.elasticsearch.action;

import com.tvd12.ezyfox.elasticsearch.callback.EzyEsActionCallback;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;

@SuppressWarnings("rawtypes")
public interface EzyEsActionWrapper {

	EzyEsAction getAction();
	
	EzyEsActionHandler getHandler();
	
	EzyEsActionCallback getCallback();
	
}
