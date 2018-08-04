package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyBytesIterator;

public class EzyBytesIterator extends EzyArrayIterator<Byte> {

	private byte[] array;
	
	public EzyBytesIterator(byte[] array) {
		this.array = array;
	}
	
	public static EzyBytesIterator wrap(byte[] array) {
		return new EzyBytesIterator(array);
	}
	
	@Override
	protected int getLength() {
		return array.length;
	}

	@Override
	protected Byte getItem(int index) {
		return array[index];
	}

	
	
}
