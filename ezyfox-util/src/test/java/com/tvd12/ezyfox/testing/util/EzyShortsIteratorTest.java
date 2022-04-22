package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyBytesIterator;

public class EzyShortsIteratorTest extends EzyArrayIteratorTest {

    @Override
    protected Object newArray() {
        return new byte[] {1, 2, 3};
    }

    @Override
    protected EzyArrayIterator<?> newIterator() {
        return EzyBytesIterator.wrap(new byte[] {1, 2, 3});
    }
}