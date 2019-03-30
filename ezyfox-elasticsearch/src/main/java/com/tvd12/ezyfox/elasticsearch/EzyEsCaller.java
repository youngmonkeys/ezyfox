package com.tvd12.ezyfox.elasticsearch;

import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;

public interface EzyEsCaller {
	
	<T> T call(EzyEsAction action);
	
	EzyEsClientProxy getClientProxy();
	
}
