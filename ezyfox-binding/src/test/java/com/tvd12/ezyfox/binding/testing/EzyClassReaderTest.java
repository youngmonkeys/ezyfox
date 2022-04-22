package com.tvd12.ezyfox.binding.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.reader.EzyClassReader;

public class EzyClassReaderTest {

    @Test
    public void test() {
        assert EzyClassReader.getInstance().read(null, getClass()) == getClass();
        assert EzyClassReader.getInstance().read(null, getClass().getName()) == getClass();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyClassReader.getInstance().read(null, 123);
    }
}