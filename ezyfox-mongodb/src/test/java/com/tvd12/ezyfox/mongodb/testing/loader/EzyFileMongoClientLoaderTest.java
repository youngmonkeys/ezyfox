package com.tvd12.ezyfox.mongodb.testing.loader;

import com.tvd12.ezyfox.mongodb.loader.EzyFileMongoClientLoader;
import com.tvd12.ezyfox.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezyfox.util.EzyMapBuilder;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;

public class EzyFileMongoClientLoaderTest extends BaseTest {

	@Test
	public void test() {
		EzyFileMongoClientLoader.load("src/test/resources/mongodb_config.properties");
		new EzyFileMongoClientLoader()
			.filePath("src/test/resources/mongodb_config.properties")
			.property(EzyMongoClientLoader.HOST, "127.0.0.1")
			.property(EzyMongoClientLoader.PORT, "27017")
			.properties(EzyMapBuilder.mapBuilder()
					.put(EzyMongoClientLoader.USERNAME, "root")
					.put(EzyMongoClientLoader.PASSWORD, "123456")
					.put(EzyMongoClientLoader.DATABASE, "test")
					.build())
			.load();
		try {
			EzyFileMongoClientLoader.load("src/test/resources/");
		}
		catch (Exception e) {
			assert e instanceof IllegalArgumentException;
		}
	}
	
}
