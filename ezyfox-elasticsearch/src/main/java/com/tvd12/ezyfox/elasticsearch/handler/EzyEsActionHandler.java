package com.tvd12.ezyfox.elasticsearch.handler;

import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;

public interface EzyEsActionHandler<A extends EzyEsAction, R> {
	
	R handle(A action) throws Exception;
	
}
