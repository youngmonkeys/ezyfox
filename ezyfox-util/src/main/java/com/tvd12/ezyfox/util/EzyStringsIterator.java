package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.util.EzyStringsIterator;
import com.tvd12.ezyfox.util.EzyWrapperIterator;

public class EzyStringsIterator extends EzyWrapperIterator<String> {

	public EzyStringsIterator(String[] array) {
		super(array);
	}
	
	public static EzyStringsIterator wrap(String[] array) {
		return new EzyStringsIterator(array);
	}
	
}
