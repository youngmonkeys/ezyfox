package com.tvd12.ezyfox.hazelcast.testing.mapstore;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.hazelcast.mapstore.EzySimpleMapstoresFetcher;
import com.tvd12.test.base.BaseTest;

public class EzySimpleMapstoresFetcherTest extends BaseTest {

	@Test
	public void test() {
		EzySimpleMapstoresFetcher fetcher = EzySimpleMapstoresFetcher.builder()
				.build();
		assert fetcher.getMapNames().isEmpty();
	}
	
}
