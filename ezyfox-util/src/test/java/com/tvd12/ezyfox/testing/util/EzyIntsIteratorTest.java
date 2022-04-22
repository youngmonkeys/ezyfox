package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyIntsIterator;

public class EzyIntsIteratorTest extends EzyArrayIteratorTest {

    @Override
    protected Object newArray() {
        return new int[] {1, 2, 3};
    }

    @Override
    protected EzyArrayIterator<?> newIterator() {
        return EzyIntsIterator.wrap(new int[] {1, 2, 3});
    }
}