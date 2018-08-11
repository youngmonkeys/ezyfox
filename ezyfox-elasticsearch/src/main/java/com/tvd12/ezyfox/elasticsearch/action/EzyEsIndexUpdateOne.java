package com.tvd12.ezyfox.elasticsearch.action;

import org.apache.http.Header;
import org.elasticsearch.action.bulk.BulkResponse;

public interface EzyEsIndexUpdateOne {

	BulkResponse indexUpdateOne(Object object, Header... headers);
	
}
