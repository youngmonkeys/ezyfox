package com.tvd12.ezyfox.elasticsearch.response;

public interface EzyEsSearchManyMetadata extends EzyEsSearchMetadata {

	EzyEsHitsMetadata getHits();
	
}
