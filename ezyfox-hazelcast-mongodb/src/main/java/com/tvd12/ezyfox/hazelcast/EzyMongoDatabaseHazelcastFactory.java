package com.tvd12.ezyfox.hazelcast;

import com.mongodb.client.MongoDatabase;
import com.tvd12.ezyfox.hazelcast.EzyAbstractHazelcastFactory;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMapstoreCreator;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMongoDatabaseMapstoreCreator;
import com.tvd12.ezyfox.mongodb.EzyMongoDatabaseAware;

import lombok.Setter;

@Setter
public class EzyMongoDatabaseHazelcastFactory 
		extends EzyAbstractHazelcastFactory 
		implements EzyMongoDatabaseAware {
	
	protected MongoDatabase database;
	
	@Override
	protected EzyMapstoreCreator newMapstoreCreator() {
		EzyMongoDatabaseMapstoreCreator creator = newDatabaseMapstoreCreator();
		creator.setDatabase(database);
		return creator;
	}

	protected EzyMongoDatabaseMapstoreCreator newDatabaseMapstoreCreator() {
		return new EzyMongoDatabaseMapstoreCreator(); 
	}
	
}
