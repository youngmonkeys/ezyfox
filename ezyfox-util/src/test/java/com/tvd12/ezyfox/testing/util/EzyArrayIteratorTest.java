package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyArrayIterator;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;

public abstract class EzyArrayIteratorTest {

    @Test
    public void test() {
        Object array = newArray();
        EzyArrayIterator<?> it = newIterator();
        AtomicInteger i = new AtomicInteger(0);
        while (it.hasNext()) {
            assertEquals(it.next(), Array.get(array, i.getAndIncrement()));
        }
    }

    protected abstract Object newArray();

    protected abstract EzyArrayIterator<?> newIterator();
}
