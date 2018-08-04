package com.tvd12.ezyfox.hazelcast.testing.service;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.hazelcast.service.EzyHazelcastMapService;

@EzyAutoImpl(properties = {
		@EzyKeyValue(key = "map.name", value = "person")
})
public interface PersonMapService extends EzyHazelcastMapService<String, Person> {
}
