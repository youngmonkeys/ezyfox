package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyFloatsIterator;

public class EzyFloatsIteratorTest extends EzyArrayIteratorTest {

    @Override
    protected Object newArray() {
        return new float[] {1, 2, 3};
    }

    @Override
    protected EzyArrayIterator<?> newIterator() {
        return EzyFloatsIterator.wrap(new float[] {1, 2, 3});
    }

}
