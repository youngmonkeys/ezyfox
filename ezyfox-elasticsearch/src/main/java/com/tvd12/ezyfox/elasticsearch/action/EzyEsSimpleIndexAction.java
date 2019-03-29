package com.tvd12.ezyfox.elasticsearch.action;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.RequestOptions;

import com.tvd12.ezyfox.elasticsearch.EzyEsIndexType;
import com.tvd12.ezyfox.elasticsearch.EzyEsIndexTypes;

import lombok.Getter;

public class EzyEsSimpleIndexAction implements EzyEsIndexAction {

	@Getter
	protected List<Object> objects;
	protected RequestOptions requestOptions;
	protected EzyEsIndexTypes.Builder indexTypesBuilder;
	
	public EzyEsSimpleIndexAction() {
		this.objects = new ArrayList<>();
		this.indexTypesBuilder = EzyEsIndexTypes.builder();
	}
	
	public EzyEsSimpleIndexAction object(Object object) {
		this.objects.add(object);
		return this;
	}
	
	public EzyEsSimpleIndexAction requestOptions(RequestOptions requestOptions) {
		this.requestOptions = requestOptions;
		return this;
	}
	
	public EzyEsSimpleIndexAction addIndexType(String index, String type) {
		this.indexTypesBuilder.add(new EzyEsIndexType(index, type));
		return this;
	}
	
	public RequestOptions getRequestOptions() {
		if(requestOptions == null)
			return RequestOptions.DEFAULT;
		return requestOptions;
	}
	
	@Override
	public EzyEsIndexTypes getIndexTypes() {
		EzyEsIndexTypes indexTypes = indexTypesBuilder.build();
		return indexTypes;
	}
	
	@Override
	public String getActionType() {
		return EzyEsActionTypes.INDEX;
	}
}
