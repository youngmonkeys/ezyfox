package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzyEmptyObject implements EzyObject {
	private static final long serialVersionUID = 7760695245885076646L;

	private Map<Object, Object> map;
	private static final EzyEmptyObject INSTANCE = new EzyEmptyObject();
	
	private EzyEmptyObject() {
		this.map = Collections.EMPTY_MAP;
	}
	
	public static EzyEmptyObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	public int size() {
		return 0;
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
		throw new UnsupportedOperationException("you're using empty object");
	}

	@Override
	public <V> V get(Object key, Class<V> type) {
		throw new UnsupportedOperationException("you're using empty object");
	}

	@Override
	public Object getValue(Object key, Class type) {
		throw new UnsupportedOperationException("you're using empty object");
	}

	@Override
	public Set<Object> keySet() {
		return map.keySet();
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Map toMap() {
		return map;
	}

	@Override
	public <V> V put(Object key, Object value) {
		return (V)value;
	}

	@Override
	public void putAll(Map m) {
	}

	@Override
	public <V> V remove(Object key) {
		return (V)null;
	}

	@Override
	public void removeAll(Collection keys) {
	}

	@Override
	public <V> V compute(Object key, BiFunction func) {
		throw new UnsupportedOperationException("you're using empty object");
	}

	@Override
	public void clear() {
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("you're using empty object");
	}

	@Override
	public EzyObject duplicate() {
		throw new UnsupportedOperationException("you're using empty object");
	}

}
