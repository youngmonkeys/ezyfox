package com.tvd12.ezyfox.testing.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.ezyfox.util.EzyMixedHashMap;
import com.tvd12.ezyfox.util.EzyMixedMap;
import com.tvd12.ezyfox.util.EzyMixedMap.EzyMixedKey;
import com.tvd12.test.performance.Performance;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EzyMixedHashMapTest {

	@Test
	public void test() {
		List<String> a = Lists.newArrayList("1", "2", "3");
		List<String> b = Lists.newArrayList("1", "2", "3");
		assert a.equals(b);
		EzyMixedMap<ReentrantLock> map = new EzyMixedHashMap<>();
		ComplexQuery query = new ComplexQuery("1", "1", "foo", "bar", "hello");
		long time = Performance.create()
				.test(() -> {
					map.computeIfAbsent(query, ReentrantLock::new);
				})
				.getTime();
		System.out.println(time);
		ReentrantLock value = map.computeIfAbsent(query, ReentrantLock::new);
		ReentrantLock value2 = map.get(query);
		assert value != null;
		assert value == value2;
	}
	
	
	@Getter
	@AllArgsConstructor
	public static class ComplexQuery implements EzyMixedKey {
	
		protected final String talkId;
		protected final String phoneNumber;
		protected final String foo;
		protected final String bar;
		protected final String hello;
		
		@SuppressWarnings("unchecked")
		@Override
		public Map<Object, Object> getKeys() {
			return EzyMapBuilder.mapBuilder()
					.put("talkId", talkId)
					.put("phoneNumber", phoneNumber)
					.put("foo", foo)
					.put("bar", bar)
					.put("hello", hello)
					.build();
		}
		
	}
}
