package com.tvd12.ezyfox.mongodb;

import com.mongodb.client.MongoDatabase;

public interface EzyMongoDatabaseAware {

	void setDatabase(MongoDatabase database);
	
}
