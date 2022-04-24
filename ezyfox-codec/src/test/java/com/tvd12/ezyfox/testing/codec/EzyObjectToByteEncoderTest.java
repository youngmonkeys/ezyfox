package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyObjectToByteEncoder;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class EzyObjectToByteEncoderTest {

    @Test
    public void test() {
        EzyObjectToByteEncoder sut = new A();
        Asserts.assertThat(() -> sut.toMessageContent(null))
            .willThrows(UnsupportedOperationException.class);
        Asserts.assertThat(() -> sut.encryptMessageContent(null, null))
            .willThrows(UnsupportedOperationException.class);
    }

    private static class A implements EzyObjectToByteEncoder {

        @Override
        public byte[] encode(Object msg) {
            return null;
        }
    }
}
