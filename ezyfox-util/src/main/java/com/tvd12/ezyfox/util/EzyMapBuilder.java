package com.tvd12.ezyfox.util;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.util.EzyMapBuilder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EzyMapBuilder implements EzyBuilder<Map> {

	protected Map map = new HashMap<>();
	
	public static EzyMapBuilder mapBuilder() {
		return new EzyMapBuilder();
	}
	
	public EzyMapBuilder map(Map map) {
		this.map = map;
		return this;
	}
	
	public EzyMapBuilder putAll(Map map) {
		this.map.putAll(map);
		return this;
	}
	
	public EzyMapBuilder put(Object key, Object value) {
		this.map.put(key, value);
		return this;
	}
	
	@Override
	public Map build() {
		return map;
	}
	
	public <K, V> Map<K, V> toMap() {
	    return (Map<K, V>)map;
	}
}
