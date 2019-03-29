package com.tvd12.ezyfox.elasticsearch.action;

import java.util.List;

import org.elasticsearch.client.RequestOptions;

import com.tvd12.ezyfox.elasticsearch.EzyEsIndexTypes;

public interface EzyEsIndexAction extends EzyEsAction {
	
	List<Object> getObjects();
	
	EzyEsIndexTypes getIndexTypes();
	
	RequestOptions getRequestOptions();
	
}
