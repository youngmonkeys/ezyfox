package com.tvd12.ezyfox.elasticsearch.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;

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
			Set<String> indexes = action.getIndexes();
			Set<String> defaultIndexes = indexedDataClasses.getIndexes(objectType);
			indexes.addAll(defaultIndexes);
			
			Object objectId = idFetcher.getId(object);
			EzyObject wrapper = marshaller.marshal(object);
			Map<String, Object> source = wrapper.toMap();
			
			for(String index : indexes) {
				IndexRequest indexRequest = new IndexRequest(index)
						.id(objectId.toString())
						.source(source);
				bulkRequest.add(indexRequest);
			}
		}
		RequestOptions requestOptions = action.getRequestOptions();
		BulkResponse response = clientProxy.bulk(bulkRequest, requestOptions);
		return response;
	}

}
