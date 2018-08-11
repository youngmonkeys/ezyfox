package com.tvd12.ezyfox.hazelcast.testing;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.mongodb.client.MongoDatabase;
import com.tvd12.ezyfox.function.EzyCreation;
import com.tvd12.ezyfox.hazelcast.EzyMongoDatabaseHazelcastFactory;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMapstoresFetcher;
import com.tvd12.ezyfox.hazelcast.mapstore.EzySimpleMapstoresFetcher;
import com.tvd12.ezyfox.hazelcast.testing.ExampleHazelcastCreator;
import com.tvd12.ezyfox.hazelcast.util.EzyHazelcastConfigs;

public class ExampleHazelcastCreator implements EzyCreation<HazelcastInstance> {

	private String filePath;
	private MongoDatabase database;
	
	public ExampleHazelcastCreator filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public ExampleHazelcastCreator database(MongoDatabase database) {
		this.database = database;
		return this;
	}
	
	@Override
	public HazelcastInstance create() {
		EzyMongoDatabaseHazelcastFactory factory = new EzyMongoDatabaseHazelcastFactory();
		factory.setDatabase(database);
		factory.setMapstoresFetcher(newMapstoresFetcher());
		return factory.newHazelcast(newConfig());
	}
	
	private Config newConfig() {
		return EzyHazelcastConfigs.newXmlConfigBuilder(filePath);
	}
	
	private EzyMapstoresFetcher newMapstoresFetcher() {
		return EzySimpleMapstoresFetcher.builder()
				.scan("com.tvd12.ezyfox.hazelcast.testing.mapstore")
				.build();
	}
	
}
