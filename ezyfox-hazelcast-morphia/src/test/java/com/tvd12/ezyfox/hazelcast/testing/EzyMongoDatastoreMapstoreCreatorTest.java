package com.tvd12.ezyfox.hazelcast.testing;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMapstoresFetcher;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMongoDatastoreMapstoreCreator;
import com.tvd12.ezyfox.hazelcast.testing.mapstore.ExampleUserMapstore;
import com.tvd12.ezyfox.hazelcast.testing.mapstore.ExampleUserMapstore2;
import com.tvd12.test.base.BaseTest;

public class EzyMongoDatastoreMapstoreCreatorTest extends BaseTest {

	@Test
	public void test() {
		EzyMapstoresFetcher mapstoresFetcher = new ExEzyMapstoresFetcher();
		EzyMongoDatastoreMapstoreCreator creator = new EzyMongoDatastoreMapstoreCreator();
		creator.setMapstoresFetcher(mapstoresFetcher);
		assert creator.create("example_users", new Properties()) != null;
		assert creator.create("example_users_2", new Properties()) != null;
	}

	public static class ExEzyMapstoresFetcher implements EzyMapstoresFetcher {
		
		@Override
		public Set<String> getMapNames() {
			return Sets.newHashSet("example_users", "example_users_2");
		}

		@Override
		public Object getMapstore(String mapName) {
			Map<String, Object> map = new HashMap<>();
			map.put("example_users", new ExampleUserMapstore());
			map.put("example_users_2", new ExampleUserMapstore2());
			return map.get(mapName);
		}

		@Override
		public Map<String, Object> getMapstores() {
			Map<String, Object> map = new HashMap<>();
			map.put("example_users", new ExampleUserMapstore());
			map.put("example_users_2", new ExampleUserMapstore2());
			return map;
		}

		@Override
		public boolean containsMapstore(String mapName) {
			return true;
		}
		
	}
}
