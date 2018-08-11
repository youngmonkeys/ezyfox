package com.tvd12.ezyfox.hazelcast.testing.service;

import com.tvd12.ezyfox.hazelcast.annotation.EzyMapServiceAutoImpl;
import com.tvd12.ezyfox.hazelcast.service.EzyHazelcastMapService;

@EzyMapServiceAutoImpl("monkey")
public interface MonkeyMapService extends EzyHazelcastMapService<String, Monkey> {
}
