package com.tvd12.ezyfox.elasticsearch.action;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;

import lombok.Getter;

public class EzyEsSimpleSearchAction implements EzyEsSearchAction {

	@Getter
	protected Class<?> responseItemType;
	@Getter
	protected SearchRequest searchRequest;
	protected RequestOptions requestOptions;
	
	public EzyEsSimpleSearchAction responseItemType(Class<?> responseItemType) {
		this.responseItemType = responseItemType;
		return this;
	}
	
	public EzyEsSimpleSearchAction searchRequest(SearchRequest searchRequest) {
		this.searchRequest = searchRequest;
		return this;
	}

	@Override
	public RequestOptions getRequestOptions() {
		if(requestOptions == null)
			return RequestOptions.DEFAULT;
		return requestOptions;
	}
	
	@Override
	public String getActionType() {
		return EzyEsActionTypes.SEARCH;
	}

}
