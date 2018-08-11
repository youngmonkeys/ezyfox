package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyIntsIterator;

public class EzyIntsIterator extends EzyArrayIterator<Integer> {

	private int[] array;
	
	public EzyIntsIterator(int[] array) {
		this.array = array;
	}
	
	public static EzyIntsIterator wrap(int[] array) {
		return new EzyIntsIterator(array);
	}
	
	@Override
	protected int getLength() {
		return array.length;
	}

	@Override
	protected Integer getItem(int index) {
		return array[index];
	}

	
	
}
