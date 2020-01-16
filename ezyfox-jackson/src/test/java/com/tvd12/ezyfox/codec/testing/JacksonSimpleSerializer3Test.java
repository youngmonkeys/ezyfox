package com.tvd12.ezyfox.codec.testing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.codec.JacksonSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.jackson.JacksonObjectMapperBuilder;

public class JacksonSimpleSerializer3Test {

	@Test
	public void test() {
		ObjectMapper objectMapper = JacksonObjectMapperBuilder.newInstance().build();
		JacksonSimpleSerializer serializer = new JacksonSimpleSerializer(objectMapper);
		EzyObject object = EzyEntityFactory.newObjectBuilder()
				.append("hello", "world")
				.append("a", new BigInteger("123"))
				.append("b", new BigDecimal("45.6"))
				.append("c", UUID.randomUUID())
				.build();
		System.out.println("string: " + serializer.serialize(object, String.class));
		System.out.println("buffer: " + serializer.serialize(object, ByteBuffer.class));
		System.out.println("map: " + serializer.serialize(object, Map.class));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test2() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonSimpleSerializer serializer = new JacksonSimpleSerializer(objectMapper);
		serializer.serialize(new A(), String.class);
	}
	
	@SuppressWarnings("rawtypes")
	public static class A implements EzyObject {
		private static final long serialVersionUID = 1641863166027487146L;

		public Object clone() {
			return null;
		}
		
		@Override
		public int size() {
			throw new RuntimeException();
		}

		@Override
		public boolean containsKey(Object key) {
			throw new RuntimeException();
		}
		
		@Override
		public boolean containsKeys(Collection keys) {
			return false;
		}

		@Override
		public boolean isNotNullValue(Object key) {
			throw new RuntimeException();
		}

		@Override
		public <V> V get(Object key) {
			throw new RuntimeException();
		}

		@Override
		public <V> V get(Object key, Class<V> type) {
			throw new RuntimeException();
		}

		@Override
		public Object getValue(Object key, Class type) {
			throw new RuntimeException();
		}

		@Override
		public Set<Object> keySet() {
			throw new RuntimeException();
		}

		@Override
		public Set<Entry<Object, Object>> entrySet() {
			throw new RuntimeException();
		}

		@Override
		public Map toMap() {
			throw new RuntimeException();
		}

		@Override
		public <V> V put(Object key, Object value) {
			throw new RuntimeException();
		}

		@Override
		public void putAll(Map m) {
			throw new RuntimeException();
			
		}

		@Override
		public <V> V remove(Object key) {
			throw new RuntimeException();
		}

		@Override
		public void removeAll(Collection keys) {
			throw new RuntimeException();
			
		}

		@Override
		public <V> V compute(Object key, BiFunction func) {
			throw new RuntimeException();
		}

		@Override
		public void clear() {
			throw new RuntimeException();
			
		}

		@Override
		public EzyObject duplicate() {
			throw new RuntimeException();
		}
		
	}
}
