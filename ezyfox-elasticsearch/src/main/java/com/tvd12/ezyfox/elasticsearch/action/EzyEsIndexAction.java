package com.tvd12.ezyfox.elasticsearch.action;

import java.util.List;
import java.util.Set;

import org.elasticsearch.client.RequestOptions;

public interface EzyEsIndexAction extends EzyEsAction {
	
	List<Object> getObjects();
	
	Set<String> getIndexes();
	
	RequestOptions getRequestOptions();
	
}
