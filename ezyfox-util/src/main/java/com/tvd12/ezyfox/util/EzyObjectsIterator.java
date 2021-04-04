package com.tvd12.ezyfox.util;

public class EzyObjectsIterator<T> extends EzyArrayIterator<T> {

	private T[] array;
	
	public EzyObjectsIterator(T[] array) {
		this.array = array;
	}
	
	public static <T> EzyObjectsIterator<T> wrap(T[] array) {
		return new EzyObjectsIterator<>(array);
	}
	
	@Override
	protected int getLength() {
		return array.length;
	}

	@Override
	protected T getItem(int index) {
		return array[index];
	}
	
}
