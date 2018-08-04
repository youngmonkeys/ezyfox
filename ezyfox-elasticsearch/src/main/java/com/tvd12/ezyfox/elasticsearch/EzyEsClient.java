package com.tvd12.ezyfox.elasticsearch;

import com.tvd12.ezyfox.elasticsearch.action.EzyEsIndexActions;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsSearchActions;

public interface EzyEsClient extends 
		EzyEsIndexActions,
		EzyEsSearchActions {
}
