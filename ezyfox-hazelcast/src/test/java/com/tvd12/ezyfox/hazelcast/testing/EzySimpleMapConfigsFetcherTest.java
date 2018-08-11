package com.tvd12.ezyfox.hazelcast.testing;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;
import com.tvd12.ezyfox.hazelcast.EzySimpleMapConfigsFetcher;
import com.tvd12.test.base.BaseTest;

public class EzySimpleMapConfigsFetcherTest extends BaseTest {

	@Test
	public void test() {
		EzySimpleMapConfigsFetcher fetcher = EzySimpleMapConfigsFetcher.builder()
				.mapNames(Sets.newHashSet("a", "b", "c"))
				.build();
		fetcher.get();
	}
	
	
	
}
