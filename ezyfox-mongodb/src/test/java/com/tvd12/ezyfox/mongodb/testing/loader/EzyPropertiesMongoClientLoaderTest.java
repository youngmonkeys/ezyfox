package com.tvd12.ezyfox.mongodb.testing.loader;

import java.util.Properties;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezyfox.mongodb.loader.EzyPropertiesMongoClientLoader;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.base.BaseTest;

public class EzyPropertiesMongoClientLoaderTest extends BaseTest {

	@Test
	public void test() {
		EzyPropertiesMongoClientLoader loader = new EzyPropertiesMongoClientLoader()
				.property(EzyMongoClientLoader.HOST, "127.0.0.1")
				.property(EzyMongoClientLoader.PORT, "27017")
				.properties(EzyMapBuilder.mapBuilder()
						.put(EzyMongoClientLoader.USERNAME, "root")
						.put(EzyMongoClientLoader.PASSWORD, "123456")
						.put(EzyMongoClientLoader.DATABASE, "test")
						.build());
		loader.load();
		
		loader = new EzyPropertiesMongoClientLoader()
				.property(EzyMongoClientLoader.URI, "mongodb://root:123456@127.0.0.1:27017/test");
		loader.load();
		
		Properties properties = new Properties();
		properties.setProperty(EzyMongoClientLoader.URI, "mongodb://root:123456@127.0.0.1:27017/test");
		EzyPropertiesMongoClientLoader.load(properties);
	}
	
}
