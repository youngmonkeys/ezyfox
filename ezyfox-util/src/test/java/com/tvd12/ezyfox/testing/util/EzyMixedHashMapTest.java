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
		ReentrantLock value3 = map.remove(query);
		assert value == value3;
		assert map.get(query) == null;
		assert map.remove(query) == null;
		assert map.get(new EmptyQuery()) == null;
		assert map.remove(new EmptyQuery()) == null;
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test1() {
		ComplexQuery query1 = new ComplexQuery("1", "1", "foo", "bar", "hello");
		ComplexQuery query2 = new ComplexQuery("2", "2", "foo2", "bar2", "hello2");
		EzyMixedMap<ReentrantLock> map = new EzyMixedHashMap<>();
		map.computeIfAbsent(query1, ReentrantLock::new);
		map.computeIfAbsent(query2, ReentrantLock::new);
		map.get(new ComplexQuery("1", "2", "", "", ""));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test2() {
		ComplexQuery query1 = new ComplexQuery("1", "1", "foo", "bar", "hello");
		ComplexQuery query2 = new ComplexQuery("2", "2", "foo2", "bar2", "hello2");
		EzyMixedMap<ReentrantLock> map = new EzyMixedHashMap<>();
		map.computeIfAbsent(query1, ReentrantLock::new);
		map.computeIfAbsent(query2, ReentrantLock::new);
		map.remove(new ComplexQuery("1", "2", "", "", ""));
	}
	
	@Test
	public void test3() {
		ComplexQuery query1 = new ComplexQuery("1", "1", "foo", "bar", "hello");
		EzyMixedMap<ReentrantLock> map = new EzyMixedHashMap<>();
		map.computeIfAbsent(query1, ReentrantLock::new);
		assert map.get(new ComplexQuery2("1", "1", "foo", "bar", "hello", "world")) != null;
	}
	
	@Test
	public void test5() {
		ComplexQuery query1 = new ComplexQuery("1", "1", "foo", "bar", "hello");
		EzyMixedMap<ReentrantLock> map = new EzyMixedHashMap<>();
		map.computeIfAbsent(query1, ReentrantLock::new);
		assert map.remove(new ComplexQuery2("1", "1", "foo", "bar", "hello", "world")) != null;
	}
	
	public static class EmptyQuery implements EzyMixedKey {
		@Override
		public Map<Object, Object> getKeys() {
			return null;
		}
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
	
	@Getter
	@AllArgsConstructor
	public static class ComplexQuery2 implements EzyMixedKey {
	
		protected final String talkId;
		protected final String phoneNumber;
		protected final String foo;
		protected final String bar;
		protected final String hello;
		protected final String world;
		
		@SuppressWarnings("unchecked")
		@Override
		public Map<Object, Object> getKeys() {
			return EzyMapBuilder.mapBuilder()
					.put("talkId", talkId)
					.put("phoneNumber", phoneNumber)
					.put("foo", foo)
					.put("bar", bar)
					.put("hello", hello)
					.put("world", world)
					.build();
		}
		
		@Override
		public Object getType() {
			return ComplexQuery.class;
		}
		
	}
}
