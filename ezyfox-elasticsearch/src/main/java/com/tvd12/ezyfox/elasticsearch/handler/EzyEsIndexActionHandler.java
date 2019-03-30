package com.tvd12.ezyfox.elasticsearch.handler;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;

import com.tvd12.ezyfox.elasticsearch.EzyEsIndexTypes;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsIndexAction;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.identifier.EzyIdFetcher;

public class EzyEsIndexActionHandler extends EzyEsAbstractActionHandler<EzyEsIndexAction, BulkResponse> {

	@SuppressWarnings("unchecked")
	@Override
	public BulkResponse handle(EzyEsIndexAction action) throws Exception {
		List<Object> objects = action.getObjects();
		BulkRequest bulkRequest = new BulkRequest();
		for(Object object : objects) {
			Class<?> objectType = object.getClass();
			EzyIdFetcher idFetcher = idFetchers.getIdFetcher(objectType);
			EzyEsIndexTypes indexTypes = action.getIndexTypes();
			EzyEsIndexTypes defaultIndexTypes = indexedDataClasses.getIndexTypes(objectType);
			indexTypes.merge(defaultIndexTypes);
			
			Object objectId = idFetcher.getId(object);
			EzyObject wrapper = marshaller.marshal(object);
			Map<String, Object> source = wrapper.toMap();
			
			for(String index : indexTypes.getIndexes()) {
				for(String type : indexTypes.getTypes(index)) {
					IndexRequest indexRequest = new IndexRequest(index)
							.id(objectId.toString())
							.type(type)
							.source(source);
					bulkRequest.add(indexRequest);
				}
			}
		}
		RequestOptions requestOptions = action.getRequestOptions();
		BulkResponse response = clientProxy.bulk(bulkRequest, requestOptions);
		return response;
	}

}
