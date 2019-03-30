package com.tvd12.ezyfox.elasticsearch.action;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;

public interface EzyEsSearchAction extends EzyEsAction {
	
	SearchRequest getSearchRequest();
	
	RequestOptions getRequestOptions();
	
	Class<?> getResponseItemType();
	
}
