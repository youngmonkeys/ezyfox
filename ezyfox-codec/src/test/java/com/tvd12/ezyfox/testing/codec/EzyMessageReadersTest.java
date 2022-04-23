package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyMessageReaders;
import com.tvd12.test.base.BaseTest;

public class EzyMessageReadersTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyMessageReaders.class;
    }

    @Test
    public void test() {
        byte[] bytes = new byte[] {0};
        assert EzyMessageReaders.bytesToMessage(bytes) == null;
        bytes = new byte[] {1, 100};
        assert EzyMessageReaders.bytesToMessage(bytes) == null;
        bytes = new byte[] {0, 0, 3, 1, 1};
        assert EzyMessageReaders.bytesToMessage(bytes) == null;
        bytes = new byte[] {0, 0, 3, 1, 1, 1};
        assert EzyMessageReaders.bytesToMessage(bytes) != null;
        bytes = new byte[] {0, 0, 3, 1, 1, 1, 1};
        assert EzyMessageReaders.bytesToMessage(bytes) != null;
    }
}
