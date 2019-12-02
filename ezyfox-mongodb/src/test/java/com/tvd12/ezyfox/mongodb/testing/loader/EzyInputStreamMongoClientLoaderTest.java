package com.tvd12.ezyfox.mongodb.testing.loader;

import java.io.InputStream;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.mongodb.loader.EzyInputStreamMongoClientLoader;
import com.tvd12.ezyfox.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.base.BaseTest;

public class EzyInputStreamMongoClientLoaderTest extends BaseTest {

	@Test
	public void test() {
		InputStream inputStream = EzyAnywayInputStreamLoader.builder()
				.context(getClass())
				.build()
				.load("mongodb_config.properties");
		EzyInputStreamMongoClientLoader loader = new EzyInputStreamMongoClientLoader()
				.inputStream(inputStream)
				.property(EzyMongoClientLoader.HOST, "127.0.0.1")
				.property(EzyMongoClientLoader.PORT, "27017")
				.properties(EzyMapBuilder.mapBuilder()
						.put(EzyMongoClientLoader.USERNAME, "root")
						.put(EzyMongoClientLoader.PASSWORD, "123456")
						.put(EzyMongoClientLoader.DATABASE, "test")
						.build());
		loader.load();
		
	}
	
}
