package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EzyHashMap extends EzyTransformable implements EzyObject {
	private static final long serialVersionUID = 2273868568933801751L;
	
	protected final HashMap<Object, Object> map = new HashMap<>();
	
	public EzyHashMap(
			EzyInputTransformer inputTransformer,
			EzyOutputTransformer outputTransformer) {
		super(inputTransformer, outputTransformer);
	}
	
	public EzyHashMap(Map map,
			EzyInputTransformer inputTransformer,
			EzyOutputTransformer outputTransformer) {
		this(inputTransformer, outputTransformer);
		this.map.putAll(map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyObject#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> V put(Object key, Object value) {
		V answer = (V) map.put(key, transformInput(value));
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyObject#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map m) {
		for(Object key : m.keySet())
			put(key, m.get(key));
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#get(java.lang.Object, java.lang.Class)
	 */
	@Override
	public <V> V get(Object key, Class<V> type) {
		V value = (V) getValue(key, type);
		return value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#getValue(java.lang.Object, java.lang.Class)
	 */
	@Override
	public Object getValue(Object key, Class type) {
		Object answer = transformOutput(map.get(key), type);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyObject#remove(java.lang.Object)
	 */
	@Override
	public <V> V remove(Object key) {
		V answer = (V) map.remove(key);
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyObject#removeAll(java.util.Collection)
	 */
	@Override
	public void removeAll(Collection keys) {
		for(Object key : keys)
			map.remove(key);
	}
	
	/*
	 * 
	 */
	@Override
	public <V> V compute(Object key, BiFunction func) {
		V answer = (V) map.compute(key, func);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#size()
	 */
	@Override
	public int size() {
		int size = map.size();
		return size;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		boolean answer = map.isEmpty();
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		boolean answer = map.containsKey(key);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#isNotNullKey(java.lang.Object)
	 */
	@Override
	public boolean isNotNullValue(Object key) {
		boolean answer = map.get(key) != null;
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#get(java.lang.Object)
	 */
	@Override
	public <V> V get(Object key) {
		V value = (V) map.get(key);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#keySet()
	 */
	@Override
	public Set<Object> keySet() {
		Set<Object> set = map.keySet();
		return set;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#entrySet()
	 */
	@Override
	public Set<Entry<Object, Object>> entrySet() {
		Set<Entry<Object, Object>> entries = map.entrySet();
		return entries;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyObject#clear()
	 */
	@Override
	public void clear() {
		map.clear();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoObject#toMap()
	 */
	@Override
	public Map toMap() {
		return new HashMap<>(map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		EzyHashMap clone = new EzyHashMap(
				map, 
				inputTransformer, outputTransformer);
		return clone;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyObject#duplicate()
	 */
	@Override
	public EzyObject duplicate() {
		try {
			return (EzyObject) clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private Object transformInput(Object input) {
		Object answer = inputTransformer.transform(input);
		return answer;
	}
	
	private Object transformOutput(Object output, Class type) {
		Object answer = outputTransformer.transform(output, type);
		return answer;
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}
