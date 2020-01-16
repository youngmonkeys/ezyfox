package com.tvd12.ezyfox.testing.entity;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.test.base.BaseTest;

import lombok.Setter;

public class EzyObject6Test extends BaseTest {

	@Test
	public void test() {
		XObject x = new XObject();
		assert x.isEmpty();
		x.setSize(100);
		assert !x.isEmpty();
		assert !x.isNotNullValue("");
		assert x.compareTo(null) == 0;
	}
	
	@SuppressWarnings({"rawtypes"})
	public static class XObject implements EzyObject {
		private static final long serialVersionUID = 1361895415042596335L;
		
		@Setter
		private int size;
		
		@Override
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		@Override
		public XObject duplicate() {
			return null;
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean containsKey(Object key) {
			return false;
		}
		
		@Override
		public boolean containsKeys(Collection keys) {
			return false;
		}
		
		@Override
		public boolean isNotNullValue(Object key) {
			return false;
		}

		@Override
		public <V> V get(Object key) {
			return null;
		}

		@Override
		public <V> V get(Object key, Class<V> clazz) {
			return null;
		}
		
		@Override
		public Object getValue(Object key, Class type) {
			return null;
		}

		@Override
		public Set<Object> keySet() {
			return null;
		}

		@Override
		public Set<Entry<Object, Object>> entrySet() {
			return null;
		}

		@Override
		public Map toMap() {
			return null;
		}

		@Override
		public <V> V put(Object key, Object value) {
			return null;
		}

		@Override
		public void putAll(Map m) {
		}

		@Override
		public <V> V remove(Object key) {
			return null;
		}

		@Override
		public void removeAll(Collection keys) {
		}

		@Override
		public <V> V compute(Object key, BiFunction func) {
			return null;
		}

		@Override
		public void clear() {
		}
		
	}
	
}
