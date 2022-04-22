package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import com.tvd12.ezyfox.util.EzyWrapperIterator;

public class EzyWrapperIteratorTest extends EzyArrayIteratorTest {

    @Override
    protected Object newArray() {
        return new String[] {"a", "b", "c"};
    }

    @Override
    protected EzyArrayIterator<?> newIterator() {
        return EzyWrapperIterator.wrap(new String[] {"a", "b", "c"});
    }
}