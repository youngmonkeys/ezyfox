package com.tvd12.ezyfox.elasticsearch.response;

public interface EzyEsResponse<M,B,O> {

	B getBody();
	
	M getMetadata();
	
	O getOriginal();
}
