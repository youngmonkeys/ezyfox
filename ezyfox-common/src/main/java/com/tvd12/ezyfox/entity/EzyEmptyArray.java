package com.tvd12.ezyfox.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzyEmptyArray implements EzyArray {
	private static final long serialVersionUID = 7419371637164947028L;

	private final List<Object> list;
	private static final EzyEmptyArray INSTANCE = new EzyEmptyArray();
	
	private EzyEmptyArray() {
		this.list = Collections.EMPTY_LIST;
	}
	
	public static EzyEmptyArray getInstance() {
		return INSTANCE;
	}
	
	@Override
	public <T> T get(int index) {
		throw new UnsupportedOperationException("you're using empty array");
	}

	@Override
	public <T> T get(int index, Class<T> type) {
		throw new UnsupportedOperationException("you're using empty array");
	}

	@Override
	public Object getValue(int index, Class type) {
		throw new UnsupportedOperationException("you're using empty array");
	}

	@Override
	public boolean isNotNullValue(int index) {
		return false;
	}
	
	@Override
	public boolean contains(Object value) {
		return false;
	}
	
	@Override
	public boolean containsAll(Collection values) {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public <T> List<T> toList() {
		return (List<T>)list;
	}

	@Override
	public <T> List<T> toList(Class<T> type) {
		throw new UnsupportedOperationException("you're using empty array");
	}

	@Override
	public <T, A> A toArray(Class<T> type) {
		throw new UnsupportedOperationException("you're using empty array");
	}

	@Override
	public void add(Object item) {
	}

	@Override
	public void add(Object... items) {
	}

	@Override
	public void add(Collection items) {
	}

	@Override
	public <T> T set(int index, Object item) {
		return (T)item;
	}

	@Override
	public <T> T remove(int index) {
		throw new UnsupportedOperationException("you're using empty array");
	}

	@Override
	public EzyArray sub(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException("you're using empty array");
	}

	@Override
	public void forEach(Consumer<Object> action) {
	}

	@Override
	public Iterator<Object> iterator() {
		return list.iterator();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("you're using empty array");
	}

	@Override
	public EzyArray duplicate() {
		throw new UnsupportedOperationException("you're using empty array");
	}

}
