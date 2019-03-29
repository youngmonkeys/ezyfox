package com.tvd12.ezyfox.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;

public interface EzyEsClientProxy {

	BulkResponse bulk(BulkRequest bulkRequest, RequestOptions options) throws IOException;
	
	IndexResponse index(IndexRequest indexRequest, RequestOptions options) throws IOException;
	
	SearchResponse search(SearchRequest searchRequest, RequestOptions options) throws IOException;
	
	<T> T getElasticsearchClient();
	
}
