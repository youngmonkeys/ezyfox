package com.tvd12.ezyfox.hazelcast.testing;

import static org.mockito.Mockito.*;

import org.testng.annotations.Test;

import com.hazelcast.config.Config;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.hazelcast.EzyMongoDatastoreHazelcastFactory;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMapstoresFetcher;
import com.tvd12.test.base.BaseTest;

import dev.morphia.Datastore;

public class EzyMongoDatastoreHazelcastFactoryTest extends BaseTest {

	@Test
	public void test() {
		Datastore datastore = mock(Datastore.class);
		EzyMapstoresFetcher mapstoresFetcher = mock(EzyMapstoresFetcher.class);
		when(mapstoresFetcher.getMapNames()).thenReturn(Sets.newHashSet());
		EzyMongoDatastoreHazelcastFactory factory = new EzyMongoDatastoreHazelcastFactory();
		factory.setDatastore(datastore);
		factory.setMapstoresFetcher(mapstoresFetcher);
		factory.newHazelcast(new Config());
	}
	
}
