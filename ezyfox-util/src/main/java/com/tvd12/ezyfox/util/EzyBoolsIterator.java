package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyBoolsIterator;

public class EzyBoolsIterator extends EzyArrayIterator<Boolean> {

	private boolean[] array;
	
	public EzyBoolsIterator(boolean[] array) {
		this.array = array;
	}
	
	public static EzyBoolsIterator wrap(boolean[] array) {
		return new EzyBoolsIterator(array);
	}
	
	@Override
	protected int getLength() {
		return array.length;
	}

	@Override
	protected Boolean getItem(int index) {
		return array[index];
	}

	
	
}
