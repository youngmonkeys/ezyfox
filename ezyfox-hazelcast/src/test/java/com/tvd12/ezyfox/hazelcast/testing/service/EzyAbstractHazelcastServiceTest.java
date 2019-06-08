package com.tvd12.ezyfox.hazelcast.testing.service;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.hazelcast.service.EzyAbstractHazelcastService;
import com.tvd12.ezyfox.hazelcast.testing.HazelcastBaseTest;

public class EzyAbstractHazelcastServiceTest extends HazelcastBaseTest {

	@Test
	public void test() throws Exception {
		MyService service = new MyService() {};
		service.setHazelcastInstance(HZ_INSTANCE);
		service.init();
	}
	
	public static class MyService extends EzyAbstractHazelcastService {
	}
	
}
