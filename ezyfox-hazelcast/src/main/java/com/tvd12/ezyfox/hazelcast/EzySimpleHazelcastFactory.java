package com.tvd12.ezyfox.hazelcast;

import com.tvd12.ezyfox.hazelcast.mapstore.EzyMapstoreCreator;
import com.tvd12.ezyfox.hazelcast.mapstore.EzySimpleMapstoreCreator;

public class EzySimpleHazelcastFactory extends EzyAbstractHazelcastFactory {

	@Override
	protected EzyMapstoreCreator newMapstoreCreator() {
		return new EzySimpleMapstoreCreator();
	}

	
	
}
