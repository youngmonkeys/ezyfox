package com.tvd12.ezyfox.elasticsearch.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elasticsearch.client.RequestOptions;

import lombok.Getter;

@Getter
public class EzyEsSimpleIndexAction implements EzyEsIndexAction {

	protected List<Object> objects;
	protected RequestOptions requestOptions;
	protected Set<String> indexes;
	
	public EzyEsSimpleIndexAction() {
		this.objects = new ArrayList<>();
		this.indexes = new HashSet<>();
	}
	
	public EzyEsSimpleIndexAction object(Object object) {
		this.objects.add(object);
		return this;
	}
	
	public EzyEsSimpleIndexAction requestOptions(RequestOptions requestOptions) {
		this.requestOptions = requestOptions;
		return this;
	}
	
	public EzyEsSimpleIndexAction addIndex(String index) {
		this.indexes.add(index);
		return this;
	}
	
	public EzyEsSimpleIndexAction addIndexes(String... indexes) {
		return addIndexes(Arrays.asList(indexes));
	}
	
	public EzyEsSimpleIndexAction addIndexes(Collection<String> indexes) {
		this.indexes.addAll(indexes);
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
		return EzyEsActionTypes.INDEX;
	}
}
