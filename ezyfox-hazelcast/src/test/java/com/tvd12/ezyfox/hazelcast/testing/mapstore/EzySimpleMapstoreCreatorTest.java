package com.tvd12.ezyfox.hazelcast.testing.mapstore;

import java.util.Properties;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.hazelcast.mapstore.EzyMapstoresFetcher;
import com.tvd12.ezyfox.hazelcast.mapstore.EzySimpleMapstoreCreator;
import com.tvd12.ezyfox.hazelcast.mapstore.EzySimpleMapstoresFetcher;
import com.tvd12.test.base.BaseTest;

public class EzySimpleMapstoreCreatorTest extends BaseTest {

	@Test
	public void test() {
		EzyMapstoresFetcher fetcher = newMapstoresFetcher();
		EzySimpleMapstoreCreator creator = new EzySimpleMapstoreCreator();
		creator.setMapstoresFetcher(fetcher);
		try {
			System.out.println(creator.create("example_users", new Properties()));
			creator.create("no", new Properties());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private EzyMapstoresFetcher newMapstoresFetcher() {
		return EzySimpleMapstoresFetcher.builder()
				.scan("com.tvd12.ezyfox.hazelcast.testing.mapstore")
				.build();
	}
	
}
