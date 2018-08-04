package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyDoublesIterator;

public class EzyDoublesIteratorTest extends EzyArrayIteratorTest {

	@Override
	protected Object newArray() {
		return new double[] {1, 2, 3};
	}

	@Override
	protected EzyArrayIterator<?> newIterator() {
		return EzyDoublesIterator.wrap(new double[] {1, 2, 3});
	}

}
