package com.tvd12.ezyfox.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;

public interface EzyEsRestHighLevelClientAware {

	void setHighLevelClient(RestHighLevelClient highLevelClient);
	
}
