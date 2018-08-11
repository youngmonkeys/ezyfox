package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyWrapperIterator;

public class EzyWrapperIterator<E> extends EzyArrayIterator<E> {

	private final E[] array;
	
	public EzyWrapperIterator(E[] array) {
		this.array = array;
	}
	
	public static <E> EzyWrapperIterator<E> wrap(E[] array) {
		return new EzyWrapperIterator<>(array);
	}
	
	@Override
	protected int getLength() {
		return array.length;
	}
	
	@Override
	protected E getItem(int index) {
		return array[index];
	}

}
