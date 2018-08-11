package com.tvd12.ezyfox.elasticsearch.rest;

import com.tvd12.ezyfox.elasticsearch.EzyEsRestHighLevelClientAware;
import com.tvd12.ezyfox.elasticsearch.operation.EzyEsOperation;

public interface EzyEsRestOperation
		extends EzyEsOperation, EzyEsRestHighLevelClientAware {

	<T> T execute();
	
}
