package com.tvd12.ezyfox.mongodb;

import com.mongodb.MongoClient;

public interface EzyMongoClientAware {

	void setMongoClient(MongoClient mongoClient);
	
}
