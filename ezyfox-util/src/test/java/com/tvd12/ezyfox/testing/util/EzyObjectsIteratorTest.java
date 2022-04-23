package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyObjectsIterator;
import org.testng.annotations.Test;

public class EzyObjectsIteratorTest {

    @Test
    public void test() {
        EzyObjectsIterator<String> iterator = EzyObjectsIterator.wrap(new String[]{"1", "2", "3"});
        assert iterator.hasNext();
        assert iterator.next().equals("1");
    }
}
