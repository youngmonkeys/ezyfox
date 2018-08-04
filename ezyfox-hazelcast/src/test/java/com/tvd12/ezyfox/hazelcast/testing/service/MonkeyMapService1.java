package com.tvd12.ezyfox.hazelcast.testing.service;

import com.tvd12.ezyfox.hazelcast.annotation.EzyMapServiceAutoImpl;
import com.tvd12.ezyfox.hazelcast.service.EzyHazelcastMapService;

@EzyMapServiceAutoImpl("monkey")
interface MonkeyMapService1 extends EzyHazelcastMapService<String, Monkey> {
}
